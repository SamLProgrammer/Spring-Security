package com.example.security.security.persistence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.security.security.models.RoleAssignment;
import com.example.security.security.models.RoleAssignmentId;

@Repository
public interface RoleAssignmentRepository extends CrudRepository<RoleAssignment, RoleAssignmentId> {

    @Query(value = "SELECT ra.role_id FROM role_assignment ra WHERE ra.user_id = :id", nativeQuery = true)
    ArrayList<Integer> getRolesIdsByUserId(int id);

    @Query(value = "SELECT ra.role_id FROM role_assignment ra WHERE ra.user_id IN (:idsSet)", nativeQuery = true)
    ArrayList<Integer> getRolesIdsByUsersIds(HashSet<Integer> idsSet);
}
