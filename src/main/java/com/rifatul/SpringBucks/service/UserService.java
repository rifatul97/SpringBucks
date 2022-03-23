package com.rifatul.SpringBucks.service;

import com.rifatul.SpringBucks.dao.RoleDao;
import com.rifatul.SpringBucks.dao.UserDao;
import com.rifatul.SpringBucks.domain.dto.UpdateUserRoleRequest;
import com.rifatul.SpringBucks.domain.dto.UserRegisterRequest;
import com.rifatul.SpringBucks.domain.model.User;
import com.rifatul.SpringBucks.exception.types.UserEmailAlreadyExistException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.rifatul.SpringBucks.service.UserValidatorService.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService {

    @Autowired private final UserDao userDao;
    @Autowired private final RoleDao roleDao;

    public Optional<User> findUserById(int id) {
        return userDao.selectById(id);
    }

    public Iterable<User> findAllUsers() {
        return userDao.getAll();
    }

    public Optional<User> findUserByEmail(String email) {
        return userDao.selectByEmail(email);
    }

    public User register(UserRegisterRequest newUser) throws UserEmailAlreadyExistException {
        throwExceptionIfUserEmailAlreadyExist(newUser.email(), userDao);
        userDao.insert(newUser.firstname(), newUser.lastname(), newUser.email(), newUser.password());
        return userDao.selectByEmail(newUser.email()).get();
    }

    public void updateUserRole(UpdateUserRoleRequest request) {
        throwExceptionIfUserIdDoesNotExist(request, userDao);
        throwExceptionIfRoleIdDoesNotExist(request, roleDao);
        roleDao.update(request);
    }

}