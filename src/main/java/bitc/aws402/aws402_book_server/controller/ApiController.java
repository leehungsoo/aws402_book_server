package bitc.aws402.aws402_book_server.controller;

import bitc.aws402.aws402_book_server.dto.UserDTO;
import bitc.aws402.aws402_book_server.variable.ReturnText;
import bitc.aws402.aws402_book_server.dto.BookDTO;
import bitc.aws402.aws402_book_server.service.ApiService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class ApiController {

  private final ApiService apiService;

  @GetMapping("/agent")
  public String getAgent(HttpServletRequest request){
    String userAgent = request.getHeader("User-Agent");
    log.info("User-Agent: {}", userAgent);
    return "agent";
  }


//  LHS : 도서 정보 가져오기(단일) isbn 을 토대로 정보를 출력한다
  @GetMapping("/book/info")
  public ResponseEntity<?> bookInfo(@RequestParam String isbn) {
    BookDTO bookDTO = apiService.selectBookInfo(isbn);
    Map<String, Object> map = new HashMap<>();
    if (bookDTO == null) {
      map.put(ReturnText.RESULT_TEXT, ReturnText.RESULT_FAILURE);
    }else{
      map.put(ReturnText.RESULT_TEXT, ReturnText.RESULT_SUCCESS);
      map.put(ReturnText.RESULT_DATA, bookDTO);
    }
    return ResponseEntity.ok(map);
  }

//  LHS : 도서 정보 DB에 입력
  @PostMapping("/book/info")
  public ResponseEntity<?> bookInfo(@RequestBody BookDTO bookDTO) {
    BookDTO existBook = apiService.selectBookInfo(bookDTO.getIsbn());
    if (existBook == null) {
      apiService.insertBookInfo(bookDTO);
    }
    Map<String, Object> map = new HashMap<>();
    map.put(ReturnText.RESULT_TEXT, ReturnText.RESULT_SUCCESS);
    return ResponseEntity.ok(map);
  }


  //  LHS : 사용자 idx 를 기반으로 등록된 도서 리스트를 출력
  @GetMapping("/book/list")
  public ResponseEntity<?> bookList(@RequestParam int idx) {
    List<BookDTO> bookDTOList = apiService.selectBookList(idx);
    Map<String, Object> map = new HashMap<>();
    map.put(ReturnText.RESULT_TEXT, ReturnText.RESULT_SUCCESS);
    map.put(ReturnText.RESULT_DATA, bookDTOList);
    return ResponseEntity.ok(map);
  }

//  LHS : 도서리스트 수정 (대여, 종료)
  @PutMapping("/book/list")
  public ResponseEntity<?> bookList(@RequestBody Map<String, Object> mapData) {
    int updateResult = apiService.updateBookList(mapData);
    Map<String, Object> map = new HashMap<>();
    if(updateResult  > 0){
      map.put(ReturnText.RESULT_TEXT, ReturnText.RESULT_SUCCESS);
    }else{
      map.put(ReturnText.RESULT_TEXT, ReturnText.RESULT_FAILURE);
    }
    return ResponseEntity.ok(map);
  }

//  LHS : 사용자 정보중에 이메일에 해당하는 사용자가 있는지 확인
  @GetMapping("/user/exists")
  public ResponseEntity<?> userExists(@RequestParam String email) {
    int userCount = apiService.selectUserByEmail(email);
    Map<String, Object> map = new HashMap<>();
    if(userCount == 1){
      map.put(ReturnText.RESULT_TEXT, ReturnText.RESULT_SUCCESS);
    }else{
      map.put(ReturnText.RESULT_TEXT, ReturnText.RESULT_FAILURE);
    }
    return ResponseEntity.ok(map);
  }

//  LHS : 사용자 이메일과, 비밀번호를 입력받아 해당하는 사용자를 출력
  @PostMapping("/user/exists")
  public ResponseEntity<?> userExists(@RequestBody Map<String, Object> loginData) {
    UserDTO userDTO = apiService.selectUserByEmailAndPassword(loginData);
    Map<String, Object> map = new HashMap<>();
    if(userDTO != null){
      map.put(ReturnText.RESULT_TEXT, ReturnText.RESULT_SUCCESS);
      map.put(ReturnText.RESULT_DATA, userDTO);
    }else{
      map.put(ReturnText.RESULT_TEXT, ReturnText.RESULT_FAILURE);
    }
    return ResponseEntity.ok(map);
  }

//  LHS : 회원가입 호출
  @PostMapping("/user/signup")
  public ResponseEntity<?> userSignup(@RequestBody UserDTO userDTO) {
    apiService.insertUser(userDTO);
    int resultIdx = userDTO.getIdx();
    Map<String, Object> map = new HashMap<>();
    if(resultIdx > 0){
      map.put(ReturnText.RESULT_TEXT, ReturnText.RESULT_SUCCESS);
    }else{
      map.put(ReturnText.RESULT_TEXT, ReturnText.RESULT_FAILURE);
    }
    return ResponseEntity.ok(map);
  }

//  LHS : 회원정보 수정
  @PutMapping("/user/edit")
  public ResponseEntity<?> userEdit(@RequestBody UserDTO userDTO) {
    int updateResult =  apiService.updateUser(userDTO);
    Map<String, Object> map = new HashMap<>();
    if(updateResult > 0){
      map.put(ReturnText.RESULT_TEXT, ReturnText.RESULT_SUCCESS);
    }else{
      map.put(ReturnText.RESULT_TEXT, ReturnText.RESULT_FAILURE);
    }
    return ResponseEntity.ok(map);
  }

//  LHS : 회원 fcm 토큰값 수정
  @PutMapping("/user/token")
  public ResponseEntity<?> userToken(@RequestBody Map<String, Object> tokenData) {
    int updateResult =  apiService.updateUserToken(tokenData);
    Map<String, Object> map = new HashMap<>();
    if(updateResult > 0){
      map.put(ReturnText.RESULT_TEXT, ReturnText.RESULT_SUCCESS);
    }else{
      map.put(ReturnText.RESULT_TEXT, ReturnText.RESULT_FAILURE);
    }
    return ResponseEntity.ok(map);
  }
}
