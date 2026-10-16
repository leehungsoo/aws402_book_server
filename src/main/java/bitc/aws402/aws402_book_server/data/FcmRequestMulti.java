package bitc.aws402.aws402_book_server.data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class FcmRequestMulti {
  String title;
  String body;
  String image;
  List<String> tokens;
}
