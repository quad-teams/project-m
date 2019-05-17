package project.m;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.multipart.CompletedFileUpload;

import javax.inject.Inject;

/**
 * Controls the upload of files.
 *
 * @author Uanderson Soares
 */
@Controller
public class FileController {

  @Inject
  private FileService fileService;

  /**
   * Handles a file in zip format.
   *
   * @param file to be handled
   */
  @Post("/files.upload")
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  public void upload(CompletedFileUpload file) {
    fileService.importFile(file);
  }
}
