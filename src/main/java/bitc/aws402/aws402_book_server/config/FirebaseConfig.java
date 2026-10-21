package bitc.aws402.aws402_book_server.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Configuration
public class FirebaseConfig {
  @Value("${firebase.sdk.path}")
  private String sdkPath;

  @Bean
  public FirebaseApp initializeFCM(){
    try{
      log.info("initialize FirebaseApp");
      FirebaseOptions options = FirebaseOptions.builder()
          .setCredentials(GoogleCredentials.fromStream(getFirebaseInfo()))
          .build();
      if(FirebaseApp.getApps().isEmpty()) {
        log.info("FirebaseApp has been initialized");
        return FirebaseApp.initializeApp(options);
      }else{
        log.info("FirebaseApp already initialized");
        return FirebaseApp.getInstance();
      }
    }catch (Exception e){
      log.error(e.getMessage());
    }
    return null;
  }

  private InputStream getFirebaseInfo() throws IOException {
    Resource resource = new ClassPathResource(sdkPath);
    if(resource.exists()) {
      return resource.getInputStream();
    }
    throw new RuntimeException("Firebase SDK not found");
  }
}
