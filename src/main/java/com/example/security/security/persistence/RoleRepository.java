package com.example.security.security.persistence;

import java.util.ArrayList;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.example.security.security.models.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer>{
    @Query("SELECT r.id FROM Role r WHERE r.name IN :rolesList")
    ArrayList<Integer> findRolesIdsByName(ArrayList<String> rolesList);

    @Query("SELECT r FROM Role r WHERE r.id IN :rolesIdsList")
    ArrayList<Role> findRolesByIds(ArrayList<Integer> rolesIdsList);
}
