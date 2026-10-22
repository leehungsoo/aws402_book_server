package bitc.aws402.aws402_book_server.controller;
import bitc.aws402.aws402_book_server.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

// 플러터로부터 '로그인 및 회원가입' 백엔드 데이터 전송

@RestController
@RequestMapping("/api")
public class UserController {

    // 로그인
    @GetMapping("/login")
    public ResponseEntity<Map<String, Object>> login(
            @RequestParam("email") String email,
            @RequestParam(value = "token", required = false) String firebaseToken,
            @RequestParam(value = "fcmToken", required = false) String fcmToken
    ) {
        System.out.println("=== 로그인 API 요청 수신 ===");
        System.out.println("이메일: " + email);
        System.out.println("파이어베이스 토큰: " + firebaseToken);
        System.out.println("FCM 토큰: " + fcmToken);

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("status", "success");
        responseData.put("message", "로그인 연동 성공!");
        responseData.put("email", email);

        return ResponseEntity.ok(responseData);
    }

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<Map<String, Object>> signup(@RequestBody UserDTO requestDto) {
        System.out.println("=== 회원가입 API 요청 수신 ===");
        System.out.println("전달받은 데이터: " + requestDto.toString());

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("status", "success");
        responseData.put("message", "회원가입 정보 DB 저장 성공!");

        return ResponseEntity.ok(responseData);
    }




}
