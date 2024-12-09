package com.example.security.security.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.security.security.dto.CreateUserPayload;
import com.example.security.security.dto.CreateUserResponse;
import com.example.security.security.dto.UserWithRoles;
import com.example.security.security.models.Role;
import com.example.security.security.models.RoleAssignment;
import com.example.security.security.models.RoleAssignmentId;
import com.example.security.security.models.User;
import com.example.security.security.persistence.RoleAssignmentRepository;
import com.example.security.security.persistence.RoleRepository;
import com.example.security.security.persistence.UserRepository;

@Service
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private RoleAssignmentRepository roleAssignmentRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
            RoleRepository roleRepository,
            RoleAssignmentRepository roleAssignmentRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.roleAssignmentRepository = roleAssignmentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public CreateUserResponse createUser(ArrayList<String> authorities, CreateUserPayload createUserPayload) {
        CreateUserResponse createUserResponse = new CreateUserResponse();
        ArrayList<String> rolesList = (createUserPayload.getRoles() == null || createUserPayload.getRoles().isEmpty())
                ? new ArrayList<>(Arrays.asList("ROLE_USER"))
                : createUserPayload.getRoles();
        if (!authorities.contains("ROLE_ADMIN") && rolesList.contains("ROLE_ADMIN")) {
            createUserResponse.setError("Insufficient Permissions");
        } else {
            try {
                User payloadUser = createUserPayload.getUser();
                payloadUser.setPassword(passwordEncoder.encode(payloadUser.getPassword()));
                User createdUser = userRepository.save(payloadUser);

                ArrayList<Integer> rolesIdsList = roleRepository.findRolesIdsByName(rolesList);
                ArrayList<RoleAssignment> roleAssignments = new ArrayList<>();

                for (int roleId : rolesIdsList) {
                    roleAssignments.add(new RoleAssignment(new RoleAssignmentId(roleId, createdUser.getId())));
                }
                roleAssignmentRepository.saveAll(roleAssignments);
                createUserResponse.setUser(createdUser);
            } catch (DataIntegrityViolationException e) {
                createUserResponse.setError(e.getMessage());
            }
        }

        return createUserResponse;
    }

    @Transactional(readOnly = true)
    public ArrayList<Role> queryRolesByUsername(String username) {
        int userId = userRepository.getIdByUsername(username);
        ArrayList<Integer> userRolesIds = roleAssignmentRepository.getRolesIdsByUserId(userId);
        return roleRepository.findRolesByIds(userRolesIds);
    }

    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional(readOnly = true)
    public ArrayList<UserWithRoles> getUsers() {
        ArrayList<User> allUsersList = userRepository.getAllUsers();
        HashMap<Integer, User> usersMap = new HashMap<>();
        for (User u : allUsersList) {
            usersMap.put(u.getId(), u);
        }

        ArrayList<UserWithRoles> usersWithRolesList = new ArrayList<UserWithRoles>();
        List<Map<String, Object>> x = userRepository.findRolesGroupedByUserId(usersMap.keySet());
        for (Map<String, Object> xmap : x) {
            usersWithRolesList.add(new UserWithRoles(usersMap.get(xmap.get("user_id")),
                    String.valueOf(xmap.get("role_names")).split(",")));
        }

        return usersWithRolesList;
    }
}
