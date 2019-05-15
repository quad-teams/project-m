package project.m;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.UUID;

@Singleton
public class PingService {

  @Inject
  private JdbcTemplate jdbcTemplate;

  @Transactional
  public UUID get() {
    var query = "SELECT id FROM pings";
    return jdbcTemplate.queryForObject(query, UUID.class);
  }
}
