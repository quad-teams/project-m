package project.m;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;

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

  public static final String PREFIX = "ZIP";
  public static final String EXTENSION = ".tmp";

  /**
   * Unzip a zip file from the file system.
   *
   * @param path to be unzipped
   * @return a flow of files
   */
  public static Flowable<UnzippedFile> unzip(Path path) {
    Objects.requireNonNull(path);
    return Flowable.using(
      () -> Files.newInputStream(path),
      Zip::unzip,
      InputStream::close
    );
  }

  /**
   * Unzip a zip file from the input stream.
   *
   * @param inputStream to be unzipped
   * @return a flow of files
   */
  public static Flowable<UnzippedFile> unzip(InputStream inputStream) {
    Objects.requireNonNull(inputStream);

    return Flowable.create(emitter -> {
      var zipInputStream = new ZipInputStream(inputStream);
      var zipEntry = zipInputStream.getNextEntry();

      while (zipEntry != null) {
        if (zipEntry.isDirectory()) {
          zipEntry = zipInputStream.getNextEntry();
          continue;
        }

        var entryPath = Files.createTempFile(PREFIX, EXTENSION);
        Files.copy(zipInputStream, entryPath, StandardCopyOption.REPLACE_EXISTING);
        emitter.onNext(new UnzippedFile(zipEntry.getName(), entryPath));

        zipEntry = zipInputStream.getNextEntry();
      }

      zipInputStream.closeEntry();
      emitter.onComplete();
    }, BackpressureStrategy.BUFFER);
  }
}
