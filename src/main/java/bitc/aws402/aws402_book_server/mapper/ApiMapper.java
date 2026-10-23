package bitc.aws402.aws402_book_server.mapper;

import bitc.aws402.aws402_book_server.dto.BookDTO;
import bitc.aws402.aws402_book_server.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ApiMapper {
  public void insertBookInfo(BookDTO bookDTO);
  public BookDTO selectBookInfo(@Param("isbn") String isbn);
  public List<Map<String, Object>> selectBookList(@Param("idx") int idx);
  public List<Map<String, Object>> selectBookListEmail(@Param("email") String email);
  public int updateBookList(Map<String, Object> mapData);

  public UserDTO selectUserByEmail(@Param("email") String email);
  public UserDTO selectUserByEmailAndPassword(Map<String, Object> loginData);
  public void insertUser(UserDTO userDTO);
  public int updateUser(UserDTO userDTO);
  public int updateUserToken(Map<String, Object> tokenData);
  public int updateUserByEmail(Map<String, Object> userData);
  public int updateUserTargetBook(Map<String, Object> userData);

  public List<Map<String, Object>> selectBookListAdmin();
  public Map<String, Object> selectAllCountAdmin();
  public List<Map<String, Object>> selectAllUsersAdmin();
  public int updateUserGrade(@Param("idx") int idx, @Param("grade") int grade);
  public int deleteUserAdmin(@Param("idx") int idx);
}
