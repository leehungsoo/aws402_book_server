package bitc.aws402.aws402_book_server.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {
  @Value("${firebase.sdk.path}")
  private String sdkPath;

  @Bean
  public FirebaseApp initializeFCM() throws IOException {
    try{
      System.out.println("initializeFCM");
      FirebaseOptions options = FirebaseOptions.builder()
          .setCredentials(GoogleCredentials.fromStream(getFirebaseInfo()))
          .build();
      if(FirebaseApp.getApps().isEmpty()) {
        return FirebaseApp.initializeApp(options);
      }else{
        System.out.println("Firebase already initialized");
        return FirebaseApp.getInstance();
      }
    }catch (Exception e){
      e.printStackTrace();
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
