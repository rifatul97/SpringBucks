package com.rifatul.SpringBucks.dao;

import com.rifatul.SpringBucks.domain.dto.UpdateUserRoleRequest;
import com.rifatul.SpringBucks.domain.model.Role;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleDao {
    List<Role> getRoles(int id);
    void update(UpdateUserRoleRequest request);
}

