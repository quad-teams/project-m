package project.m;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.zip.ZipInputStream;

/**
 * Unzip zip files.
 *
 * @author Uanderson Soares
 */
public class Zip {

  public static final String PREFIX = "flowable";
  public static final String EXTENSION = ".tmp";

  /**
   * Unzip a zip file from the filesystem.
   *
   * @param zip to be unzipped
   * @return a flow of files
   */
  public static Flowable<Path> unzip(Path zip) {
    Objects.requireNonNull(zip);

    return Flowable.create(emitter -> {
      try (
        var inputStream = Files.newInputStream(zip);
        var zipInputStream = new ZipInputStream(inputStream);
      ) {
        var entry = zipInputStream.getNextEntry();

        while (entry != null) {
          if (!entry.isDirectory()) {
            var entryPath = Files.createTempFile(PREFIX, EXTENSION);
            Files.copy(zipInputStream, entryPath, StandardCopyOption.REPLACE_EXISTING);
            emitter.onNext(entryPath);
          }

          entry = zipInputStream.getNextEntry();
        }

        zipInputStream.closeEntry();
        emitter.onComplete();
      } catch (IOException ex) {
        emitter.onError(ex);
      }
    }, BackpressureStrategy.BUFFER);
  }
}
