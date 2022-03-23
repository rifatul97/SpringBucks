package com.rifatul.SpringBucks;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.web.util.WebUtils;

public class CommandLineAppStartupRunner implements CommandLineRunner {

    private static final String COOKIE_BEARER = "COOKIE-BEARER";
    @Autowired private HttpServletRequest request;

    @Override
    public void run(String... args) throws Exception {
        checkIfCookieExist();
    }

    private void checkIfCookieExist() {
        Cookie cookie = WebUtils.getCookie(request, COOKIE_BEARER);
        if (cookie != null) {
            System.out.println(cookie.getValue());
        } else {
            System.out.println(cookie == null);
        }

    }
}
