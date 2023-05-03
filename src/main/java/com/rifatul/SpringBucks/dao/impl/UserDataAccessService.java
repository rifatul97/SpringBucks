package com.rifatul.SpringBucks.dao.impl;


import com.rifatul.SpringBucks.dao.UserDao;
import com.rifatul.SpringBucks.dao.mapper.UserRowMapper;
import com.rifatul.SpringBucks.domain.model.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.rifatul.SpringBucks.utils.GetSQLQuery.getSQLQuery;

@Repository @AllArgsConstructor @Slf4j
public class UserDataAccessService implements UserDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<User> getAll() {
        var sql = getSQLQuery("select_all_user");
        return jdbcTemplate.query(sql, new UserRowMapper());
    }

    @Override
    public void insert(String firstname, String lastname, String email, String password) {
        log.info("step 3.7");
        log.info("firstname = {} lastname = {} email = {} password = {}", firstname, lastname, email, password);
        var insertUser = getSQLQuery("insert_user");
        var insertUserRole = getSQLQuery("insert_user_roles");
        jdbcTemplate.update(
                insertUser, firstname, lastname, email, password
        );
        log.info("step 3.8");
        User newUser = this.selectByEmail(email).get();

        jdbcTemplate.update(
                insertUserRole,
                newUser.id()
        );

    }

    @Override
    public Optional<User> selectById(int id) {
        var sql = getSQLQuery("select_user_by_id");
        return Optional.empty();
    }

    @Override
    public Optional<User> selectByEmail(String email) {
        var sql = getSQLQuery("select_user_by_email");
        System.out.println("the email is " + email);
        return jdbcTemplate.query(sql, new UserRowMapper(), email)
                .stream()
                .findFirst();
    }

    @Override
    public Optional<User> selectByEmailAndPassword(String email, String password) {
        var sql = getSQLQuery("select_user_by_email_and_password");
        return jdbcTemplate.query(sql, new UserRowMapper(), email, password)
                .stream()
                .findFirst();
    }

    @Override
    public void deleteAll() {
        var sql = getSQLQuery("delete_all_users");
        jdbcTemplate.execute(sql);
    }


}
