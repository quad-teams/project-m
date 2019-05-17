package project.m;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

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
      .doOnSuccess(count -> Assertions.assertThat(count).isEqualTo(6))
      .doOnError(error -> Assertions.fail(error.getMessage()))
      .subscribe();
  }
}
