package com.aivle._th_miniProject.Book.Service;

import com.aivle._th_miniProject.Book.DTO.*;
import com.aivle._th_miniProject.Book.Entity.Book;
import com.aivle._th_miniProject.Book.Repository.BookRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Transactional
    public BookCreateResponse createBook(BookCreateRequest request, Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Book book = Book.builder()
                .user(user)
                .title(request.getTitle())
                .description(request.getDescription())
                .coverImage(request.getCoverImage())
                .category(request.getCategory())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Book saved = bookRepository.save(book);

        return BookCreateResponse.builder()
                .bookId(saved.getBookId())
                .title(saved.getTitle())
                .author(saved.getUser().getName())
                .description(saved.getDescription())
                .coverImage(saved.getCoverImage())
                .category(saved.getCategory())
                .createdAt(saved.getCreatedAt())
                .updatedAt(saved.getUpdatedAt())
                .build();
    }

    public BookDetailResponse getBookDetail(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() ->
                        new IllegalArgumentException("도서 정보를 가져올 수 없습니다.")
                );

        return BookDetailResponse.builder()
                .bookId(book.getBookId())
                .title(book.getTitle())
                .author(book.getUser().getName())
                .description(book.getDescription())
                .coverImage(book.getCoverImage())
                .category(book.getCategory())
                .createdAt(book.getCreatedAt())
                .updatedAt(book.getUpdatedAt())
                .build();
    }

    @Transactional
    public BookDetailResponse updateBook(Long bookId, BookUpdateRequest request, Long userId, String role) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() ->
                        new IllegalArgumentException("요청한 도서를 찾을 수 없습니다.")
                );

        // 권한 체크
        boolean isOwner = book.getUser().getUserId().equals(userId);
        boolean isAdmin = role.equals("ADMIN");

        if (!isOwner && !isAdmin) {
            throw new IllegalArgumentException("본인의 도서만 수정할 수 있습니다.");
        }

        book.setTitle(request.getTitle());
        book.setDescription(request.getDescription());
        book.setCoverImage(request.getCoverImage());
        book.setCategory(request.getCategory());
        book.setUpdatedAt(LocalDateTime.now());

        return BookDetailResponse.builder()
                .bookId(book.getBookId())
                .title(book.getTitle())
                .author(book.getUser().getName())
                .description(book.getDescription())
                .coverImage(book.getCoverImage())
                .category(book.getCategory())
                .createdAt(book.getCreatedAt())
                .updatedAt(book.getUpdatedAt())
                .build();
    }

    @Transactional
    public void deleteBook(Long bookId, Long userId, String role) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() ->
                        new IllegalArgumentException("도서 정보를 가져올 수 없습니다.")
                );

        boolean isOwner = book.getUser().getUserId().equals(userId);
        boolean isAdmin = role.equals("ADMIN");

        if (!isOwner && !isAdmin) {
            throw new IllegalArgumentException("본인의 도서만 삭제할 수 있습니다.");
        }

        bookRepository.delete(book);
    }

    @Transactional
    public CoverUpdateResponse updateCover(Long bookId, Long userId, String role, String newCover) {

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() ->
                        new IllegalArgumentException("도서 정보를 가져올 수 없습니다.")
                );

        boolean isOwner = book.getUser().getUserId().equals(userId);
        boolean isAdmin = role.equals("ADMIN");

        if (!isOwner && !isAdmin) {
            throw new IllegalArgumentException("본인의 표지만 수정할 수 있습니다.");
        }

        book.setCoverImage(newCover);
        book.setUpdatedAt(LocalDateTime.now());

        return new CoverUpdateResponse(
                book.getBookId(),
                book.getCoverImage(),
                book.getUpdatedAt()
        );
    }

}
