package bitc.aws402.aws402_book_server.data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class FcmRequest {
  String title;
  String body;
  String image;
  String token;
}
