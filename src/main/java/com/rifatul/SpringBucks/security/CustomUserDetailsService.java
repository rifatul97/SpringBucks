package com.rifatul.SpringBucks.security;

import com.rifatul.SpringBucks.dao.RoleDao;
import com.rifatul.SpringBucks.dao.UserDao;
import com.rifatul.SpringBucks.domain.model.Role;
import com.rifatul.SpringBucks.domain.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
@CrossOrigin(origins = "http://localhost:3000", maxAge = 36000)
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired private final UserDao userDao;
    @Autowired private final RoleDao roleDao;
    @Autowired private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("loading user by username");
        User user = userDao.selectByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("it is not found."));
        List<Role> userRoles = roleDao.selectByUserId(user.id());
        log.info("user's roles are {}", userRoles.toString());
        return new org.springframework.security.core.userdetails.User(
                user.email(), passwordEncoder.encode(user.password()), getUserAuthorities(userRoles));
    }

    private List<GrantedAuthority> getUserAuthorities(List<Role> userRoles) {
        Set<GrantedAuthority> roles = new HashSet<>();
        userRoles.forEach((role) -> {
            roles.add(new SimpleGrantedAuthority(role.name()));
        });
        return new ArrayList<GrantedAuthority>(roles);
    }

}