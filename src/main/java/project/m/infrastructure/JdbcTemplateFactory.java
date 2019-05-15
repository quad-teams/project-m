package project.m.infrastructure;

import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.sql.DataSource;

@Factory
public class JdbcTemplateFactory {

  @Inject
  private DataSource dataSource;

  @Bean
  @Singleton
  JdbcTemplate jdbcTemplate() {
    return new JdbcTemplate(dataSource);
  }
}
