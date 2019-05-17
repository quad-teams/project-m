package project.m;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;

import java.io.IOException;
import java.io.InputStream;
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
   * Unzip a zip file from the file system.
   *
   * @param path to be unzipped
   * @return a flow of files
   */
  public static Flowable<Path> unzip(Path path) {
    Objects.requireNonNull(path);

    try (var inputStream = Files.newInputStream(path)) {
      return unzip(inputStream);
    } catch (IOException ex) {
      return Flowable.error(ex);
    }
  }

  /**
   * Unzip a zip file from the input stream.
   *
   * @param inputStream to be unzipped
   * @return a flow of files
   */
  public static Flowable<Path> unzip(InputStream inputStream) {
    Objects.requireNonNull(inputStream);

    return Flowable.create(emitter -> {
      var zipInputStream = new ZipInputStream(inputStream);
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
    }, BackpressureStrategy.BUFFER);
  }
}
