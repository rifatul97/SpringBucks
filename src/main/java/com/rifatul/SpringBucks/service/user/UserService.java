package com.rifatul.SpringBucks.service.user;

import com.rifatul.SpringBucks.dao.RoleDao;
import com.rifatul.SpringBucks.dao.UserDao;
import com.rifatul.SpringBucks.domain.dto.UserDTO;
import com.rifatul.SpringBucks.domain.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.rifatul.SpringBucks.service.user.UserValidatorService.*;

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

    public User register(UserDTO.Request.Register req) {
        throwExceptionIfUserEmailAlreadyExist(req.email(), userDao);

        userDao.insert(req.firstname(), req.lastname(), req.email(), req.password());

        return userDao.selectByEmail(req.email()).get();
    }

    public void addUserRole(int userId, int roleId)  {
        throwExceptionIfUserIdDoesNotExist(userId, userDao);
        throwExceptionIfRoleIdDoesNotExist(roleId, roleDao);

        roleDao.updateUserRole(userId, roleId);
    }

    public void removeUserRole(int userId) {
        throwExceptionIfUserIdDoesNotExist(userId, userDao);

        roleDao.updateUserRole(userId, 1);
    }
}