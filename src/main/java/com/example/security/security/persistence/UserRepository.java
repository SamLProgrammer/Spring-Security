package com.example.security.security.persistence;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.example.security.security.models.User;


@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    @Query("SELECT u.id FROM User u WHERE u.username = :username")
    Integer getIdByUsername(String username);

    User findByUsername(String username);

}
