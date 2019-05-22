package project.m;

import java.io.File;
import java.nio.file.Path;

import static java.util.Objects.requireNonNull;

/**
 * Represents a unzipped entry.
 *
 * @author Uanderson Soares
 */
public class UnzippedFile {

  private String fileName;
  private Path path;

  public UnzippedFile(String fileName, Path path) {
    this.fileName = requireNonNull(fileName);
    this.path = requireNonNull(path);
  }

  /**
   * Gets the file name.
   *
   * @return file name
   */
  public String getFileName() {
    return fileName;
  }

  /**
   * Gets the path.
   *
   * @return path
   */
  public Path getPath() {
    return path;
  }


  /**
   * Gets a {@link File} from the path.
   *
   * @return a {@link File}
   */
  public File getFile() {
    return path.toFile();
  }
}
