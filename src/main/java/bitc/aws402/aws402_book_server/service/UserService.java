package bitc.aws402.aws402_book_server.service;

import bitc.aws402.aws402_book_server.dto.UserDTO;
import bitc.aws402.aws402_book_server.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserMapper userMapper;

    // 마이페이지 회원 정보 조회
    public UserDTO getUserMyPage(String email) {
        return userMapper.selectUserMyPage(email);
    }

    // 목표 권수 수정
    public boolean updateTargetBookCount(UserDTO userDTO) {
        return userMapper.updateTargetBookCount(userDTO) > 0;
    }

    // 내 정보 수정
    public boolean updateUserProfile(UserDTO userDTO) {
        return userMapper.updateUserProfile(userDTO) > 0;
    }

    // 읽은 책 통계 리스트 조회
    public List<Map<String, Object>> getCompletedBooksByEmail(String email) {
        return userMapper.selectCompletedBooksByEmail(email);
    }
}