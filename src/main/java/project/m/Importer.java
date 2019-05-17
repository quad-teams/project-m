package project.m;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;
import static org.apache.commons.lang3.math.NumberUtils.toDouble;

/**
 * Imports the lines into the database.
 *
 * @author Uanderson Soares
 */
@Singleton
public class Importer {

  @Inject
  private JdbcTemplate jdbcTemplate;

  /**
   * Insert the batch into the database.
   *
   * @param lines to be inserted
   */
  @Transactional
  public void importLines(List<String> lines) {
    var sql = "INSERT INTO rows VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

      @Override
      public void setValues(PreparedStatement statement, int index) throws SQLException {
        var line = lines.get(index);
        var values = line.split("\\t");

        statement.setString(1, UUID.randomUUID().toString());
        statement.setString(2, OffsetDateTime.now().format(ISO_DATE_TIME));
        statement.setString(3, values[0]);
        statement.setString(4, values[1]);
        statement.setString(5, values[2]);
        statement.setString(6, values[3]);
        statement.setString(7, values[4]);
        statement.setString(8, values[5]);
        statement.setDouble(9, toDouble(values[6]));
        statement.setDouble(10, toDouble(values[7]));
        statement.setDouble(11, toDouble(values[8]));
        statement.setDouble(12, toDouble(values[9]));
        statement.setString(13, values[10]);
        statement.setDouble(14, toDouble(values[11]));
        statement.setString(15, values[12]);
      }

      @Override
      public int getBatchSize() {
        return lines.size();
      }
    });
  }
}
