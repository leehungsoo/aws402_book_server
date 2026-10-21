package bitc.aws402.aws402_book_server.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

@Mapper
public interface BookMapper {
  List<Map<String,Object>> findAllWithIsbn();
  // 날짜: 2026-10-20
  // 수정자: 구정민
  // 내용: insertBook 메서드에서 해당 화면 또는 데이터의 처리 로직을 구현한다.
  int insertBook(@Param("title") String title,
      @Param("contents") String contents, @Param("url") String url,
      @Param("isbn") String isbn, @Param("datetime") String datetime,
      @Param("authors") String authors, @Param("publisher") String publisher,
      @Param("translators") String translators, @Param("price") int price,
      @Param("salePrice") int salePrice, @Param("thumbnail") String thumbnail);
}
