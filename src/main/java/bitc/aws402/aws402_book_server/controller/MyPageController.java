package bitc.aws402.aws402_book_server.controller;

import bitc.aws402.aws402_book_server.dto.UserDTO;
import bitc.aws402.aws402_book_server.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MyPageController {

    private final UserService userService;

    // 마이페이지 정보 조회
    @GetMapping("/api/mypage/{email}")
    public ResponseEntity<?> getUserMyPage(@PathVariable("email") String email) {
        log.info("--- 마이페이지 정보 조회 요청 (이메일: {}) ---", email);
        UserDTO userDTO = userService.getUserMyPage(email);

        if (userDTO != null) {
            return ResponseEntity.ok(userDTO);
        } else {
            Map<String, Object> map = new HashMap<>();
            map.put("status", "fail");
            map.put("message", "사용자를 찾을 수 없습니다.");
            return ResponseEntity.status(404).body(map);
        }
    }

    // 독서 목표 권수 수정
    @PutMapping("/api/targetbook")
    public ResponseEntity<?> updateTargetBook(@RequestBody UserDTO userDTO) {
        log.info("--- 독서 목표 수정 요청 (이메일: {}) ---", userDTO.getEmail());
        boolean isUpdated = userService.updateTargetBookCount(userDTO);

        if (isUpdated) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().body("목표 수정 실패");
        }
    }

    // 내 정보 수정
    @PutMapping("/api/editprofile")
    public ResponseEntity<?> editProfile(@RequestBody UserDTO userDTO) {
        log.info("--- 내 정보 수정 요청 (이메일: {}) ---", userDTO.getEmail());
        boolean isUpdated = userService.updateUserProfile(userDTO);

        if (isUpdated) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().body("프로필 수정 실패");
        }
    }

    // 읽은 책 통계 리스트 조회
    @GetMapping("/api/bookstatistics")
    public ResponseEntity<List<Map<String, Object>>> getBookStatistics(@RequestParam("email") String email) {
        log.info("--- 읽은 책 통계 조회 요청 (이메일: {}) ---", email);
        List<Map<String, Object>> completedBooks = userService.getCompletedBooksByEmail(email);
        return ResponseEntity.ok(completedBooks);
    }
}