package com.example.security.security.models;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class RoleAssignmentId implements Serializable {

    @Column(name = "role_id")
    private int roleId;
    @Column(name = "user_id")
    private int userId;

    public RoleAssignmentId() {
    }

    public RoleAssignmentId(int roleId, int userId) {
        this.roleId = roleId;
        this.userId = userId;
    }

    public int getRoleId() {
        return this.roleId;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof RoleAssignmentId)) {
            return false;
        }
        RoleAssignmentId roleAssignmentId = (RoleAssignmentId) o;
        return roleId == roleAssignmentId.roleId && userId == roleAssignmentId.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId, userId);
    }

    @Override
    public String toString() {
        return "{" +
                " roleId='" + getRoleId() + "'" +
                ", userId='" + getUserId() + "'" +
                "}";
    }

}
