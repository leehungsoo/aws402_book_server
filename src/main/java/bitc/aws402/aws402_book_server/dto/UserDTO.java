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
  int targetBookCount;
  int grade;
  int status; // tb_book_list의 완독수를 가져오기 위함
}
