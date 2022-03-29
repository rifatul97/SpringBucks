package com.rifatul.SpringBucks.dao.impl;

import com.rifatul.SpringBucks.dao.RoleDao;
import com.rifatul.SpringBucks.dao.mapper.RoleRowMapper;
import com.rifatul.SpringBucks.domain.dto.UpdateUserRoleRequest;
import com.rifatul.SpringBucks.domain.model.Role;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.rifatul.SpringBucks.utils.GetSQLQuery.getSQLQuery;

@Repository
@AllArgsConstructor
@Slf4j
public class RoleDataAccessService implements RoleDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Role> getRoles(int userId) {
        var sql = getSQLQuery("select_user_roles");
        return jdbcTemplate.query(sql, new RoleRowMapper(), userId);
    }

    @Override
    public void update(UpdateUserRoleRequest request) {
        var updateUserRole = getSQLQuery("update_user_role");
        jdbcTemplate.update(updateUserRole, request.roleId(), request.userId());
    }
}