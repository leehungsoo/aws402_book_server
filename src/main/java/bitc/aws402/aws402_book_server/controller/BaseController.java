package bitc.aws402.aws402_book_server.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class BaseController {
  @GetMapping({"", "/"})
  public String index() {
    return "index";
  }
  @GetMapping({"/db"})
  public String db() {
    return "db";
  }
}
