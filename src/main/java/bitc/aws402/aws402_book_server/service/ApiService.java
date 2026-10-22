package bitc.aws402.aws402_book_server.service;

import bitc.aws402.aws402_book_server.dto.BookDTO;
import bitc.aws402.aws402_book_server.dto.UserDTO;
import bitc.aws402.aws402_book_server.mapper.ApiMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class ApiService {
  private final ApiMapper apiMapper;

  public void insertBookInfo(BookDTO bookDTO) {
    apiMapper.insertBookInfo(bookDTO);
  }

  public BookDTO selectBookInfo(String isbn) {
    return apiMapper.selectBookInfo(isbn);
  }
  public List<BookDTO> selectBookList(int idx) {
    return apiMapper.selectBookList(idx);
  }
  public int updateBookList(Map<String, Object> mapData){
    return apiMapper.updateBookList(mapData);
  }

  public int selectUserByEmail(String email){
    return apiMapper.selectUserByEmail(email);
  }
  public UserDTO selectUserByEmailAndPassword(Map<String, Object> loginData) {
    return apiMapper.selectUserByEmailAndPassword(loginData);
  }

  public void insertUser(UserDTO userDTO) {
    apiMapper.insertUser(userDTO);
  }
  public int updateUser(UserDTO userDTO) {
    return apiMapper.updateUser(userDTO);
  }
  public int updateUserToken(Map<String, Object> tokenData) {
    return apiMapper.updateUserToken(tokenData);
  }
  public int updateUserByEmail(Map<String, Object> userData) {
    return apiMapper.updateUserByEmail(userData);
  }

  public List<Map<String, Object>> selectBookListAdmin(){
    return apiMapper.selectBookListAdmin();
  }

  public Map<String, Object> selectAllCountAdmin(){
    return apiMapper.selectAllCountAdmin();
  }
  public List<Map<String, Object>> selectAllUsersAdmin(){
    return apiMapper.selectAllUsersAdmin();
  }

  public int updateUserGrade(int idx, int grade){
    return apiMapper.updateUserGrade(idx, grade);
  }

  public int deleteUserAdmin(int idx){
    return apiMapper.deleteUserAdmin(idx);
  }

}
