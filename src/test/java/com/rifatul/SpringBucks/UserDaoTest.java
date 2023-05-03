package com.rifatul.SpringBucks;

import com.rifatul.SpringBucks.dao.UserDao;
import com.rifatul.SpringBucks.testutils.DemoPostgreSQLContainer;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Testcontainers // automatically starts and stops all defined containers
@ContextConfiguration(initializers = {UserDaoTest.Initializer.class}) // set datasource properties from container
public class UserDaoTest {

    @Autowired
    UserDao userDao;

    @Container
    private static final DemoPostgreSQLContainer postgreSQLContainer = DemoPostgreSQLContainer.getInstance();

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                    "spring.datasource.password=" + postgreSQLContainer.getPassword()
            ).applyTo(applicationContext.getEnvironment());
        }
    }


//    @Autowired
//    private DSLContext context;

    @BeforeEach
    void setup(){

    }

    @Test
    @Transactional
    void testuser2() {
        userDao.insert("ooo", "oooo", "oo", "pass");
        assertThat(userDao.selectByEmail("oo").isPresent()).isTrue();
    }

    @Test
    @Transactional
    void testuser3() {
        assertThat(userDao.selectByEmail("oo").isPresent()).isFalse();
    }

    @Test
    @Transactional
    void testuser4() {
//        userDao.insert("ooo", "oooo", "oo", "pass");
        assertThat(userDao.selectByEmail("rifat97@gmail.com").isPresent()).isTrue();
    }

}
