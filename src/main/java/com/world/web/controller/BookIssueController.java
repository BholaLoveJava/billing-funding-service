package com.world.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/issue")
public class BookIssueController {


    @PreAuthorize("hasAuthority('STAFF_MEMBER')")
    @GetMapping("/new/{bookId}")
    public ResponseEntity<String> issueBook(@PathVariable("bookId") String bookId, Authentication authentication) {
        String userName = authentication.getName();
        return ResponseEntity.ok("Book issued successfully with id :: "+bookId+" for user name :: "+userName);
    }

    @PreAuthorize("hasAuthority('STAFF_MEMBER')")
    @GetMapping("/update/{bookId}")
    public ResponseEntity<String> updateBookDueDate(@PathVariable("bookId") String bookId) {
        return ResponseEntity.ok("Book due date updated successfully!! with id :: "+bookId);
    }

    @PreAuthorize("hasAuthority('STAFF_MEMBER')")
    @DeleteMapping("/delete/{bookId}")
    public ResponseEntity<String> deleteIssueBook(@PathVariable("bookId") String bookId) {
        return ResponseEntity.ok("Book deleted successfully!! with id :: "+bookId);
    }

}
