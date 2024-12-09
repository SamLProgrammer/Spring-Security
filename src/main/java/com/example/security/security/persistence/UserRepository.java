package com.example.security.security.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.security.security.models.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    @Query("SELECT u.id FROM User u WHERE u.username = :username")
    Integer getIdByUsername(String username);

    User findByUsername(String username);

    @Query("SELECT u FROM User u")
    ArrayList<User> getAllUsers();

    // @Query(value = """
    // SELECT ra.user_id,
    // (SELECT GROUP_CONCAT(r.name)
    // FROM role r
    // WHERE r.id IN
    // (SELECT ra.role_id
    // FROM role_assignment ra
    // WHERE ra.user_id = ra.user_id)) AS role_names
    // FROM role_assignment ra
    // WHERE ra.user_id IN (:userIds)
    // GROUP BY ra.user_id
    // """, nativeQuery = true)
    // List<Map<String, Object>> findRolesGroupedByUserId(ArrayList<Integer>
    // userIds);

    @Query(value = """
    SELECT ra.user_id,
    (SELECT GROUP_CONCAT(r.name)
    FROM role r
    WHERE r.id IN
    (SELECT ra.role_id
    FROM role_assignment ra
    WHERE ra.user_id = ra.user_id)) AS role_names
    FROM role_assignment ra
    WHERE ra.user_id IN (:userIds)
    GROUP BY ra.user_id
    """, nativeQuery = true)
    List<Map<String, Object>> findRolesGroupedByUserId(Set<Integer> userIds);

}
