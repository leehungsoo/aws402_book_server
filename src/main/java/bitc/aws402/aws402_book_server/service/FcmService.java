package bitc.aws402.aws402_book_server.service;

import bitc.aws402.aws402_book_server.data.FcmRequest;
import bitc.aws402.aws402_book_server.data.FcmRequestMulti;
import bitc.aws402.aws402_book_server.data.FcmResponse;
import bitc.aws402.aws402_book_server.data.FcmResponseMulti;
import bitc.aws402.aws402_book_server.variable.ReturnText;
import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class FcmService {
  public FcmResponse sendMessage(FcmRequest fcmRequest) {
    FcmResponse fcmResponse = new FcmResponse();
    try{
      Notification.Builder notification = Notification.builder();
      if(fcmRequest.getImage() != null)notification.setImage(fcmRequest.getImage());
      if(fcmRequest.getTitle() != null)notification.setTitle(fcmRequest.getTitle());
      if(fcmRequest.getBody() != null)notification.setBody(fcmRequest.getBody());
      Message message = Message.builder()
          .setToken(fcmRequest.getToken())
          .setNotification( notification.build())
          .build();
      String response = FirebaseMessaging.getInstance().send(message);
      fcmResponse.setSuccess(true);
      fcmResponse.setMessage(response);
    }catch (IllegalArgumentException | FirebaseMessagingException e){
      log.error(e.getMessage());
      fcmResponse.setSuccess(false);
      fcmResponse.setMessage(e.getMessage());
    }
    return fcmResponse;
  }

  public FcmResponseMulti sendMessageMulti(FcmRequestMulti fcmRequestMulti) {
    FcmResponseMulti fcmResponseMulti = new FcmResponseMulti();
    try{
      Notification.Builder notification = Notification.builder();
      if(fcmRequestMulti.getImage() != null)notification.setImage(fcmRequestMulti.getImage());
      if(fcmRequestMulti.getTitle() != null)notification.setTitle(fcmRequestMulti.getTitle());
      if(fcmRequestMulti.getBody() != null)notification.setBody(fcmRequestMulti.getBody());
      MulticastMessage message = MulticastMessage.builder()
          .addAllTokens(fcmRequestMulti.getTokens())
          .setNotification( notification.build())
          .build();
      BatchResponse response = FirebaseMessaging.getInstance().sendEachForMulticast(message);
      fcmResponseMulti.setSuccessCount(response.getSuccessCount());
      fcmResponseMulti.setFailureCount(response.getFailureCount());
      if (response.getFailureCount() > 0) {
        List<SendResponse> responses = response.getResponses();
        List<String> failedTokens = new ArrayList<>();
        for (int i = 0; i < responses.size(); i++) {
          if (!responses.get(i).isSuccessful()) {
            failedTokens.add(fcmRequestMulti.getTokens().get(i));
          }
        }
        fcmResponseMulti.setFailTokens(failedTokens);
        System.out.println("List of tokens that caused failures: " + failedTokens);
      }
    }catch (IllegalArgumentException | FirebaseMessagingException e){
      log.error(e.getMessage());
    }
    return fcmResponseMulti;
  }
}
