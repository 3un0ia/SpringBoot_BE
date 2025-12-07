package com.aivle._th_miniProject.Book.Repository;

import com.aivle._th_miniProject.Book.Entity.Book;
import com.aivle._th_miniProject.Book.Entity.Category;
import com.aivle._th_miniProject.User.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByUser(User user);

    List<Book> findByCategory(Category category);

    List<Book> findByTitleContaining(String keyword);
}
