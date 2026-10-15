package bitc.aws402.aws402_book_server.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@NoArgsConstructor
@Service
@Getter
public class BookDTO {
  int idx;
  String title;
  String contents;
  String url;
  String isbn;
  String datetime;
  String authors;
  String publisher;
  String translators;
  int price;
  int sale_price;
  String thumbnail;
}
