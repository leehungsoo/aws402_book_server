package bitc.aws402.aws402_book_server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.nio.file.Paths;

@Configuration
// 날짜: 2026-10-20
// 수정자: 구정민
// 내용: 업로드한 책 표지 이미지를 정적 리소스로 제공하도록 경로를 연결한다.
public class UploadConfig implements WebMvcConfigurer {

  // 내용: addResourceHandlers 메서드에서 해당 화면 또는 데이터의 처리 로직을 구현한다.
  @Value("${barobook.upload-dir:./uploads/covers}") private String uploadDir;
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/uploads/covers/**")
      .addResourceLocations(Paths.get(uploadDir).toAbsolutePath().normalize().toUri().toString());
  }
}
