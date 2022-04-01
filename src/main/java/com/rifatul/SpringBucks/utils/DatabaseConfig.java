package com.rifatul.SpringBucks.utils;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DatabaseConfig {

    @Bean
    @Primary
    public HikariDataSource hikariDataSource() {
        return DataSourceBuilder
                .create()
                .type(HikariDataSource.class)
                .driverClassName("org.postgresql.Driver")
                .url("jdbc:postgresql://localhost:5432/somedb")
                .username("postgres")
                .password("pass")
                .build();
    }

//    @Bean
//    public static FlywayMigrationStrategy cleanMigrateStrategy() {
//        return flyway -> {
//            flyway.repair();
//            flyway.clean();//flyway.migrate();
//        };
//    }

    @Bean
    public JdbcTemplate jdbcTemplate(HikariDataSource hikariDataSource) {
        return new JdbcTemplate(hikariDataSource);
    }

}
