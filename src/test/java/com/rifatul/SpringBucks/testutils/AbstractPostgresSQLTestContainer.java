package com.rifatul.SpringBucks.testutils;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.MapPropertySource;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import java.util.Map;

import static java.lang.System.getProperties;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractPostgresSQLTestContainer {

    // will be started before and stopped after each test method
    @Container
    static PostgreSQLContainer postgressqlcontainer = (PostgreSQLContainer) new PostgreSQLContainer("postgres:latest")
            .withDatabaseName("somedb")
            .withReuse(true);

    @DynamicPropertySource
    public static void overrideProps(DynamicPropertyRegistry registry) {
        System.out.println(String.format("spring.datasource.url = %s", postgressqlcontainer.getJdbcUrl()));
        registry.add("spring.datasource.url", postgressqlcontainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgressqlcontainer::getUsername);
        registry.add("spring.datasource.password", postgressqlcontainer::getPassword);
    }


    @BeforeAll
    public static void setup() {
        postgressqlcontainer.start();
    }

    @AfterAll
    public static void stop() {
        postgressqlcontainer.stop();
        System.out.println("stopped!");
    }

    @Test
    void test() {
        assertTrue(postgressqlcontainer.isRunning());
        System.out.println(postgressqlcontainer.getDatabaseName());
        System.out.println(postgressqlcontainer.getJdbcUrl());
        System.out.println(postgressqlcontainer.getDriverClassName());
        System.out.println(postgressqlcontainer.getTestQueryString());
    }
}