package com.example.security.security.models;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class RoleAssignment {

    @EmbeddedId
    private RoleAssignmentId id;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "role_id", insertable = false, updatable = false)
    private Role role;

    public RoleAssignment() {

    }

    public RoleAssignment(RoleAssignmentId id) {
        this.id = id;
    }

    public RoleAssignment(RoleAssignmentId id, User user, Role role) {
        this.id = id;
        this.user = user;
        this.role = role;
    }

    public RoleAssignmentId getId() {
        return id;
    }

    public Role getRole() {
        return role;
    }

    public User getUser() {
        return user;
    }

    public void setId(RoleAssignmentId id) {
        this.id = id;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
