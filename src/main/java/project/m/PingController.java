package project.m;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

import javax.inject.Inject;
import java.util.UUID;

@Controller
public class PingController {

  @Inject
  private PingService pingService;

  @Get("/ping")
  public UUID get() {
    return pingService.get();
  }
}
