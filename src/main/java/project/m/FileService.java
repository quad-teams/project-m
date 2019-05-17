package project.m;

import io.micronaut.context.annotation.Value;
import io.micronaut.http.multipart.CompletedFileUpload;
import io.reactivex.Flowable;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * A class to unzip and process the content
 * of the zip files.
 *
 * @author Uanderson Soares
 */
@Singleton
public class FileService {

  @Value("${importer.batch-size}")
  private int batchSize;

  @Inject
  private Importer importer;

  /**
   * Unzips and processes the content
   * of the zip file.
   *
   * @param file to be processed
   */
  public void importFile(CompletedFileUpload file) {
    try (var inputStream = file.getInputStream()) {
      Zip.unzip(inputStream)
        .flatMap(this::processContent)
        .subscribe();
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }
  }

  /**
   * Processes the content of the file extracting
   * the lines and importing it in batches.
   *
   * @param file to have the lines extracted
   * @return a flow with the batch
   */
  private Flowable<List<String>> processContent(Path file) {
    return Flowable.using(
      () -> new BufferedReader(new FileReader(file.toFile())),
      reader -> Flowable.fromIterable(() -> reader.lines().iterator()),
      BufferedReader::close
    )
      .skip(1)
      .buffer(batchSize)
      .doOnNext(a -> importer.importLines(a));
  }
}
