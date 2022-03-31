package com.rifatul.SpringBucks.service;

import com.rifatul.SpringBucks.dao.RoleDao;
import com.rifatul.SpringBucks.dao.UserDao;
import com.rifatul.SpringBucks.domain.dto.UserDTO;
import com.rifatul.SpringBucks.domain.model.User;
import com.rifatul.SpringBucks.exception.UserEmailAlreadyExistException;
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
        log.info("finding all the users");
        return userDao.getAll();
    }

    public Optional<User> findUserByEmail(String email) {
        return userDao.selectByEmail(email);
    }

    public User register(UserDTO.Request.Register request) throws UserEmailAlreadyExistException {
        throwExceptionIfUserEmailAlreadyExist(request.getEmail(), userDao);
        userDao.insert(request.getFirstname(), request.getLastname(), request.getEmail(), request.getPassword());
        return userDao.selectByEmail(request.getEmail()).get();
    }

    public void updateUserRole(UserDTO.Request.UpdateRole request) {
        throwExceptionIfUserIdDoesNotExist(request.getUserId(), userDao);
        throwExceptionIfRoleIdDoesNotExist(request.getRoleId(), roleDao);

        roleDao.update(request.getUserId(), request.getRoleId());
    }

}