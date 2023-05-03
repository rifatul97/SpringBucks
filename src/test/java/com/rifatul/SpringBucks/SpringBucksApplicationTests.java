//package com.rifatul.SpringBucks;
//
//import com.rifatul.SpringBucks.dao.UserDao;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//
//@SpringBootTest
//@Testcontainers
//@ActiveProfiles("test-containers")
//class SpringBucksApplicationTests extends AbstractPostgresSQLTestContainer {
//
//
//	@Autowired
//	UserDao userDao;
//
//	@Test
//	void contextLoads() {
////		userDao.insert("first", "last", "testemail2", "pass");
//		assertThat(userDao.selectByEmail("testemail3").isPresent()).isTrue();
//	}
//
//}
