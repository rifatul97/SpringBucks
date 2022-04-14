package com.rifatul.SpringBucks.dao;

import com.rifatul.SpringBucks.domain.model.Role;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleDao {
    List<Role> selectByUserId(int id);
    void updateUserRole(int userId, int roleId);
}

