package com.rifatul.SpringBucks.service.user;

import com.rifatul.SpringBucks.dao.RoleDao;
import com.rifatul.SpringBucks.dao.UserDao;
import com.rifatul.SpringBucks.exception.UserEmailAlreadyExistException;

public class UserValidatorService {

    public static void throwExceptionIfUserEmailAlreadyExist(String email, UserDao userDao) throws UserEmailAlreadyExistException {
        if (userDao.selectByEmail(email).isPresent()) {
            throw new UserEmailAlreadyExistException(email);
        }
    }

    public static void throwExceptionIfRoleIdDoesNotExist(int roleId, RoleDao roleDao) {
    }

    public static void throwExceptionIfUserIdDoesNotExist(int userId, UserDao userDao) {
    }

}