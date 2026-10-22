package bitc.aws402.aws402_book_server.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
  int idx;
  String name;
  String email;
  String password;
  String target_book_count;
  int grade;
}
