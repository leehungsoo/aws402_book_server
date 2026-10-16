package bitc.aws402.aws402_book_server.data;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class FcmResponseMulti {
  int successCount = 0;
  int failureCount = 0;
  List<String> failTokens = new ArrayList<>();

  public Map<String, Object> objToMap(){
    Map<String, Object> map = new HashMap<>();
    map.put("successCount", successCount);
    map.put("failureCount", failureCount);
    map.put("failTokens", failTokens);
    return map;
  }
}
