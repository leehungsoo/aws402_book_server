package bitc.aws402.aws402_book_server.dto;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserDTO {
  int idx;
  String name;
  String email;
  String password;
  String target_book_count;
  int grade;
  int status;
}
