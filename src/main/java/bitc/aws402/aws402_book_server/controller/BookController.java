package bitc.aws402.aws402_book_server.controller;

import bitc.aws402.aws402_book_server.mapper.BookMapper;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

// 날짜: 2026-10-20
// 수정자: 구정민
// 내용: 도서 목록·중복 확인·등록을 처리하는 REST API를 제공한다.
@RestController
@RequestMapping("/api/book")
public class BookController {
    private final BookMapper mapper;

    public BookController(BookMapper mapper) {
        this.mapper = mapper;
    }

    // 내용: ok 메서드에서 해당 화면 또는 데이터의 처리 로직을 구현한다.
    private Map<String, Object> ok(Object data) {
        return Map.of("result", "success", "data", data);
    }

    // 내용: fail 메서드에서 해당 화면 또는 데이터의 처리 로직을 구현한다.
    private Map<String, Object> fail(String message) {
        return Map.of("result", "fail", "data", message);
    }

    // 내용: tokens 메서드에서 해당 화면 또는 데이터의 처리 로직을 구현한다.
    private List<String> tokens(String isbn) {
        if (isbn == null) return List.of();
        return Arrays.stream(isbn.trim().split("\\s+"))
                .map(s -> s.replaceAll("[^0-9Xx]", "").toUpperCase(Locale.ROOT))
                .filter(s -> !s.isBlank()).distinct().toList();
    }

    private Optional<Map<String, Object>> existing(String isbn) {
        List<String> codes = tokens(isbn);
        if (codes.isEmpty()) return Optional.empty();
        return mapper.findAllWithIsbn().stream().filter(row ->
                tokens(Objects.toString(row.get("isbn"), "")).stream().anyMatch(codes::contains)).findFirst();
    }

    // 내용: info 메서드에서 해당 화면 또는 데이터의 처리 로직을 구현한다.
    @GetMapping("/info")
    public Map<String, Object> info(@RequestParam String isbn) {
        return existing(isbn).map(this::ok).orElseGet(() -> fail("등록되지 않은 책입니다."));
    }

    // 내용: 요청한 ISBN의 등록 여부를 확인하는 API를 처리한다.
    @GetMapping("/exists")
    public Map<String, Object> exists(@RequestParam String isbn) {
        return ok(Map.of("exists", existing(isbn).isPresent()));
    }

    // 내용: Flutter에서 전달한 도서 정보를 검증하고 MyBatis를 통해 DB에 등록한 후 처리 결과를 반환한다.
    @PostMapping("/info")
    public ResponseEntity<Map<String, Object>> insert(@RequestBody Map<String, Object> body) {

        // 내용: Flutter에서 전달한 JSON 데이터에서 도서 제목과 ISBN을 문자열로 추출한다.
        String title = str(body, "title"), isbn = str(body, "isbn");

        // 내용: 도서 제목이나 ISBN이 비어 있으면 등록을 중단하고 HTTP 400 오류를 반환한다.
        if (title.isBlank() || isbn.isBlank()) {
            return ResponseEntity.badRequest()
                    .body(fail("제목과 ISBN은 필수입니다."));
        }

        // 내용: DB에 동일한 ISBN이 이미 등록되어 있는지 확인하고 중복이면 HTTP 409 오류를 반환한다.
        if (existing(isbn).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(fail("이미 등록된 책입니다."));
        }

        try {
            // 내용: 도서 제목, 설명, ISBN, 출판일, 저자, 출판사 등의 정보를 MyBatis Mapper에 전달하여 DB에 저장한다.
            mapper.insertBook(
                    title,
                    str(body, "contents"),
                    str(body, "url"),
                    isbn,
                    str(body, "datetime"),
                    str(body, "authors"),
                    str(body, "publisher"),
                    str(body, "translators"),
                    number(body, "price"),
                    number(body, "sale_price"),
                    str(body, "thumbnail")
            );

            // 내용: 도서 등록이 완료되면 HTTP 201 상태 코드와 등록 성공 메시지를 Flutter에 반환한다.
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ok(Map.of("message", "도서 등록이 완료되었습니다.")));

        } catch (DuplicateKeyException e) {

            // 내용: DB 저장 중 ISBN 등의 고유 키 중복 오류가 발생하면 HTTP 409와 중복 등록 안내 메시지를 반환한다.
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(fail("이미 등록된 책입니다."));
        }
    }


    // 내용: str 메서드에서 해당 화면 또는 데이터의 처리 로직을 구현한다.
    private String str(Map<String, Object> body, String key) {
        return Objects.toString(body.get(key), "").trim();
    }


    // 내용: number 메서드에서 해당 화면 또는 데이터의 처리 로직을 구현한다.
    private int number(Map<String, Object> body, String key) {
        try {
            return Integer.parseInt(str(body, key));
        } catch (Exception e) {
            return 0;
        }
    }
}
