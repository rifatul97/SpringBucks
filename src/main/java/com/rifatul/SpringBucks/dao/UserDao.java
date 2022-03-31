package com.rifatul.SpringBucks.dao;

import com.rifatul.SpringBucks.domain.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserDao {
    List<User> getAll();
    void insert(String firstname, String lastname, String email, String password);
    Optional<User> selectById(int id);
    Optional<User> selectByEmail(String email);
    Optional<User> selectByEmailAndPassword(String email, String password);
}

