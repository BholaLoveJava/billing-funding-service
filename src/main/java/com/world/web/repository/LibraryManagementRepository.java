package com.world.web.repository;

import com.world.web.entity.LibraryBookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryManagementRepository extends JpaRepository<LibraryBookEntity, Long> {


    /**
     * Calling Stored Function findLibraryBookById based on bookId input parameter
     * @param bookId as input parameter
     * @return String author name
     */
    @Query(value = "SELECT * from findLibraryBookById(:bookId)", nativeQuery = true)
    public String getAuthorNameByBookId(String bookId);
}
