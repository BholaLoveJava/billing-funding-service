package com.world.web.controller;

import com.world.web.service.LibraryManagementService;
import com.world.web.model.LibraryBookRequest;
import com.world.web.model.LibraryBookResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/library")
public class LibraryManagementController {

    @Autowired
    private LibraryManagementService libraryManagementService;

    /**
     * @param libraryBookRequest as input request
     * @return LibraryBookResponse
     */
    @PostMapping("/save/book")
    public ResponseEntity<LibraryBookResponse> saveBookData(@RequestBody @Valid LibraryBookRequest libraryBookRequest) {
        try {
            LibraryBookResponse libraryBookResponse = libraryManagementService.saveBookData(libraryBookRequest);
            return new ResponseEntity<>(libraryBookResponse, HttpStatus.OK);
        } catch (Exception exp) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @return List<LibraryBookResponse>
     */
    @GetMapping("/fetchBooks")
    public ResponseEntity<List<LibraryBookResponse>> fetchAllBookData() {
        try {
            List<LibraryBookResponse> libraryBookResponse = libraryManagementService.fetchAllBookData();
            return new ResponseEntity<>(libraryBookResponse, HttpStatus.OK);
        } catch (Exception exp) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @return List<LibraryBookResponse>
     * <a href="https://www.bezkoder.com/spring-boot-pagination-filter-jpa-pageable/">
     */
    @GetMapping("/fetchBooks/pagination")
    public ResponseEntity<Map<String, Object>> fetchAllBooksUsingPagination(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "4") int size) {
        try {
            Pageable paging = PageRequest.of(page, size);
            Map<String, Object> mapPaginationResponse = libraryManagementService.fetchAllBooksUsingPagination(paging);
            return new ResponseEntity<>(mapPaginationResponse, HttpStatus.OK);
        } catch (Exception exp) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API Endpoint to fetch the Author name based on bookId
     * @param bookId as input parameter
     * @return String
     */
    @GetMapping("/fetchBooks/{bookId}")
    public ResponseEntity<String> getAuthorNameByBookId(@PathVariable String bookId){
        try {
            String bookAuthorName  = libraryManagementService.getAuthorNameByBookId(bookId);
            return new ResponseEntity<>(bookAuthorName, HttpStatus.OK);
        }catch(Exception exp){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
