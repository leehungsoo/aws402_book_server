package bitc.aws402.aws402_book_server.controller;

import bitc.aws402.aws402_book_server.data.FcmRequest;
import bitc.aws402.aws402_book_server.data.FcmRequestMulti;
import bitc.aws402.aws402_book_server.data.FcmResponse;
import bitc.aws402.aws402_book_server.data.FcmResponseMulti;
import bitc.aws402.aws402_book_server.service.FcmService;
import bitc.aws402.aws402_book_server.variable.ReturnText;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@Slf4j
@RequestMapping("/fcm")
@RequiredArgsConstructor
@RestController
public class FcmController {

  private final FcmService fcmService;

  @PostMapping("/send")
  public ResponseEntity<?> sendMessage(@RequestBody FcmRequest fcmRequest) {
    Map<String, Object> map = new HashMap<>();
    FcmResponse fcmResponse = fcmService.sendMessage(fcmRequest);
    if(fcmResponse.isSuccess()) {
      map.put(ReturnText.RESULT_TEXT, ReturnText.RESULT_SUCCESS);
    }else{
      map.put(ReturnText.RESULT_TEXT, ReturnText.RESULT_FAILURE);
    }
    map.put("message", fcmResponse.getMessage());
    return ResponseEntity.ok(map);
  }

  @PostMapping("/sendMulti")
  public ResponseEntity<?> sendMessageMulti(@RequestBody FcmRequestMulti fcmRequestMulti) {
//    System.out.println("\n ----- FCM 멀티 메세지 Request ----- \n");
//    System.out.println("title : " + fcmRequestMulti.getTitle());
//    System.out.println("body : " + fcmRequestMulti.getBody());
//      System.out.println("token : ");
//    for(String token : fcmRequestMulti.getTokens()) {
//      System.out.println(token);
//    }
    Map<String, Object> map = new HashMap<>();
    FcmResponseMulti fcmResponseMulti = new FcmResponseMulti();
    List<String> tokens = fcmRequestMulti.getTokens();
    int maxSize = 500;
    for (int i = 0; i < tokens.size(); i += 500) {
      List<String> subTokens = tokens.subList(i, Math.min(i + maxSize, tokens.size()));
      fcmRequestMulti.setTokens(subTokens);
      FcmResponseMulti multiResult = fcmService.sendMessageMulti(fcmRequestMulti);
      fcmResponseMulti.setSuccessCount(fcmResponseMulti.getSuccessCount() + multiResult.getSuccessCount());
      fcmResponseMulti.setFailureCount(fcmResponseMulti.getFailureCount() + multiResult.getFailureCount());
      List<String> failTokens = fcmResponseMulti.getFailTokens();
      failTokens.addAll(multiResult.getFailTokens());
      fcmResponseMulti.setFailTokens(failTokens);
    }
    map.put(ReturnText.RESULT_TEXT, ReturnText.RESULT_SUCCESS);
    map.put("data", fcmResponseMulti.objToMap());
    return ResponseEntity.ok(map);
  }
}
