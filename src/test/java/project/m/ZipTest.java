package project.m;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class ZipTest {

  private Path temporaryZip;

  @BeforeEach
  void setUp() throws Exception {
    temporaryZip = Files.createTempFile("tests", ".zip");
    var inputStream = getClass().getResourceAsStream("/zip.zip");
    Files.copy(inputStream, temporaryZip, StandardCopyOption.REPLACE_EXISTING);
  }

  @Test
  @DisplayName("Ensure the correct number of entries")
  void unzip_and_count() {
    Zip.unzip(temporaryZip).count()
      .doOnSuccess(count -> assertThat(count).isEqualTo(6))
      .doOnError(error -> fail(error.getMessage()))
      .subscribe();
  }
}
