package bitc.aws402.aws402_book_server.mapper;

import bitc.aws402.aws402_book_server.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {
    UserDTO selectUserMyPage(@Param("email") String email);
    int updateTargetBookCount(UserDTO userDTO);
    int updateUserProfile(UserDTO userDTO);
    List<Map<String, Object>> selectCompletedBooksByEmail(@Param("email") String email);
}