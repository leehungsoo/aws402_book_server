package bitc.aws402.aws402_book_server.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/book")
// 날짜: 2026-10-20
// 수정자: 구정민
// 내용: 책 표지 이미지 파일 업로드를 처리하고 접근 가능한 경로를 반환한다.
public class BookCoverController {
  private static final Set<String> ALLOWED = Set.of("image/jpeg", "image/png", "image/webp");
  @Value("${barobook.upload-dir:./uploads/covers}")
  private String uploadDir;

  @PostMapping("/cover")
  public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) throws IOException {
    if (file.isEmpty() || file.getSize() > 5 * 1024 * 1024 || !ALLOWED.contains(file.getContentType())) {
      return ResponseEntity.badRequest().body(Map.of("result", "fail", "data", "JPG, PNG, WEBP 이미지(최대 5MB)만 가능합니다."));
    }
    String extension = switch (file.getContentType()) {
      case "image/png" -> ".png";
      case "image/webp" -> ".webp";
      default -> ".jpg";
    };
    Path directory = Paths.get(uploadDir).toAbsolutePath().normalize();
    Files.createDirectories(directory);
    String name = UUID.randomUUID() + extension;
    Path target = directory.resolve(name);
    Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
    String url = ServletUriComponentsBuilder.fromCurrentContextPath().path("/uploads/covers/").path(name).toUriString();
    return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("result", "success", "data", Map.of("url", url)));
  }
}
