package com.world.web.service;

import com.world.web.model.LibraryBookRequest;
import com.world.web.model.LibraryBookResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface LibraryManagementService {

    public LibraryBookResponse saveBookData(LibraryBookRequest libraryBookRequest);
    public List<LibraryBookResponse> fetchAllBookData();
    public Map<String,Object> fetchAllBooksUsingPagination(Pageable pageable);

    public String getAuthorNameByBookId(String bookId);
}
