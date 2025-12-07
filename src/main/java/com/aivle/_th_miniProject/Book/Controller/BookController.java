package com.aivle._th_miniProject.Book.Controller;

import com.aivle._th_miniProject.Book.DTO.*;
import com.aivle._th_miniProject.Book.Service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<?> createBook(
            @Validated @RequestBody BookCreateRequest request,
            @AuthenticationPrincipal AuthUser authUser
    ){
        Long userId = authUser.getUserId();

        try {
            BookCreateResponse response = bookService.createBook(request, userId);
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            //메시지로 404 / 400 구분
            if (e.getMessage().contains("찾을 수 없습니다")) {
                return ResponseEntity.status(404)
                        .body(Map.of("errorMessage", e.getMessage()));
            }

            return ResponseEntity.status(400)
                    .body(Map.of("errorMessage", e.getMessage()));
        }
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<?> getBookDetail(
            @PathVariable Long bookId,
            @AuthenticationPrincipal AuthUser authUser
    ) {
        try {
            BookDetailResponse response = bookService.getBookDetail(bookId);
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("도서 정보를 가져올 수 없습니다")) {
                return ResponseEntity.status(404)
                        .body(Map.of("errorMessage", e.getMessage()));
            }
            return ResponseEntity.status(400)
                    .body(Map.of("errorMessage", e.getMessage()));
        }
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<?> updateBook(
            @PathVariable Long bookId,
            @Validated @RequestBody BookUpdateRequest request,
            @AuthenticationPrincipal AuthUser authUser
    ) {
        Long userId = authUser.getUserId();
        String role = authUser.getRole();

        try {
            BookDetailResponse response = bookService.updateBook(bookId, request, userId, role);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("요청한 도서를 찾을 수 없습니다")) {
                return ResponseEntity.status(404)
                        .body(Map.of("errorMessage", e.getMessage()));
            }
                return ResponseEntity.status(403)
                        .body(Map.of("errorMessage", e.getMessage()));
        }
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<?> deleteBook(
            @PathVariable Long bookId,
            @AuthenticationPrincipal AuthUser authUser
    ) {
        Long userId = authUser.getUserId();
        String role = authUser.getRole();

        try {
            bookService.deleteBook(bookId, userId, role);
            return ResponseEntity.noContent().build(); //204
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("도서 정보를 가져올 수 없습니다")) {
                return ResponseEntity.status(404)
                        .body(Map.of("errorMessage", e.getMessage()));
            }
                return ResponseEntity.status(403)
                        .body(Map.of("errorMessage", e.getMessage()));
        }
    }

    @PatchMapping("/{bookId}")
    public ResponseEntity<?> updateCover(
            @PathVariable Long bookId,
            @Validated @RequestBody CoverUpdateRequest request,
            @AuthenticationPrincipal AuthUser authUser
    ) {
        Long userId = authUser.getUserId();
        String role = authUser.getRole();

        try {
            CoverUpdateResponse response =
                    bookService.updateCover(bookId, userId, role, request.getCoverImage());

            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {

            if (e.getMessage().contains("도서 정보를 가져올 수 없습니다")) {
                return ResponseEntity.status(404)
                        .body(Map.of("errorMessage", e.getMessage()));
            }

            if (e.getMessage().contains("본인의 표지만")) {
                return ResponseEntity.status(403)
                        .body(Map.of("errorMessage", e.getMessage()));
            }
            return ResponseEntity.status(400)
                    .body(Map.of("errorMessage", e.getMessage()));
        }
    }
}
