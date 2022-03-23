package com.rifatul.SpringBucks.service;

import com.rifatul.SpringBucks.dao.RoleDao;
import com.rifatul.SpringBucks.dao.UserDao;
import com.rifatul.SpringBucks.domain.dto.UpdateUserRoleRequest;
import com.rifatul.SpringBucks.exception.types.UserEmailAlreadyExistException;

public class UserValidatorService {

    public static void throwExceptionIfUserEmailAlreadyExist(String email, UserDao userDao) throws UserEmailAlreadyExistException {
        if (userDao.selectByEmail(email).isPresent()) {
            System.out.println("email " + email + " is being thrown!!");
            throw new UserEmailAlreadyExistException(email);
        }
    }

    public static void throwExceptionIfRoleIdDoesNotExist(UpdateUserRoleRequest request, RoleDao roleDao) {
    }

    public static void throwExceptionIfUserIdDoesNotExist(UpdateUserRoleRequest request, UserDao userDao) {
    }

}