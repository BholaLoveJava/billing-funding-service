package com.world.web.service;

import com.world.web.model.LibraryBookRequest;
import com.world.web.model.LibraryBookResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface LibraryManagementService {

    LibraryBookResponse saveBookData(LibraryBookRequest libraryBookRequest);
    List<LibraryBookResponse> fetchAllBookData();
    Map<String,Object> fetchAllBooksUsingPagination(Pageable pageable);
    String getAuthorNameByBookId(String bookId);
}
