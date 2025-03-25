package com.world.web.serviceImpl;

import com.world.web.repository.LibraryManagementRepository;
import com.world.web.entity.LibraryBookEntity;
import com.world.web.model.LibraryBookRequest;
import com.world.web.model.LibraryBookResponse;
import com.world.web.service.LibraryManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LibraryManagementServiceImpl implements LibraryManagementService {

    private static final Logger logger = LoggerFactory.getLogger(LibraryManagementServiceImpl.class);

    @Autowired
    private LibraryManagementRepository libraryManagementRepository;

    /**
     * @param libraryBookRequest as input request
     * @return LibraryBookResponse object
     */
    @Override
    public LibraryBookResponse saveBookData(LibraryBookRequest libraryBookRequest) {
        logger.info("IN LibraryManagementServiceImpl :: saveBookData method called with data : {}", libraryBookRequest);
        //Prepare LibraryBookEntity Object and Save it, using Repository
        LibraryBookEntity libraryBookEntityData = mapLibraryBookRequestModelToEntity(libraryBookRequest);
        LibraryBookEntity libraryBookEntityResult = libraryManagementRepository.save(libraryBookEntityData);
        //Prepare and return the LibraryBookResponse Object Data
        return mapLibraryBookEntityToModel(libraryBookEntityResult);
    }

    /**
     *
     * @param libraryBookRequest as input request
     * @return LibraryBookEntity
     */

    public LibraryBookEntity mapLibraryBookRequestModelToEntity(LibraryBookRequest libraryBookRequest){
        LibraryBookEntity libraryBookEntity = new LibraryBookEntity();
        libraryBookEntity.setBookId(libraryBookRequest.getBookId());
        libraryBookEntity.setBookName(libraryBookRequest.getBookName());
        libraryBookEntity.setBookAuthor(libraryBookRequest.getBookAuthor());
        libraryBookEntity.setRackSection(libraryBookRequest.getRackSection());
        libraryBookEntity.setRackRowNumber(libraryBookRequest.getRackRowNumber());
        logger.info("IN LibraryManagementServiceImpl :: mapLibraryBookRequestModelToEntity method response data : {}",libraryBookEntity );
        return libraryBookEntity;
    }

    /**
     *
     * @param libraryBookEntityResult as input parameter
     * @return LibraryBookResponse
     */
    public LibraryBookResponse mapLibraryBookEntityToModel(LibraryBookEntity libraryBookEntityResult) {
        LibraryBookResponse libraryBookResponse = new LibraryBookResponse();
        libraryBookResponse.setLibraryId(libraryBookEntityResult.getLibraryBookId());
        libraryBookResponse.setBookName(libraryBookEntityResult.getBookName());
        libraryBookResponse.setBookAuthor(libraryBookEntityResult.getBookAuthor());
        libraryBookResponse.setRackSection(libraryBookEntityResult.getRackSection());
        libraryBookResponse.setRackRowNumber(libraryBookEntityResult.getRackRowNumber());
        logger.info("IN LibraryManagementServiceImpl :: mapLibraryBookEntityToModel method response data : {}", libraryBookEntityResult);
        return libraryBookResponse;
    }

    /**
     * @return List<LibraryBookResponse>
     */
    @Override
    public List<LibraryBookResponse> fetchAllBookData() {
        logger.info("IN LibraryManagementServiceImpl :: fetchAllBookData method called");
        List<LibraryBookEntity> libraryBookEntities = libraryManagementRepository.findAll();
        return libraryBookEntities.stream()
                .map(this::mapLibraryBookEntityToModel)
                .collect(Collectors.toList());
    }

    /**
     * @param pageable as input parameter
     * @return List<LibraryBookResponse>
     */
    @Override
    public Map<String,Object> fetchAllBooksUsingPagination(Pageable pageable) {
        logger.info("IN LibraryManagementServiceImpl :: fetchAllBooksUsingPagination method called with pageable data : {}:{}",
                pageable.getPageNumber(),  pageable.getPageSize());
        //Fetch Pageable response from the database using repository call
        Page<LibraryBookEntity> libraryBookEntityPage = libraryManagementRepository.findAll(pageable);
        //Map the Pageable response to LibraryBookResponse object
        List<LibraryBookResponse> libraryBookResponses = libraryBookEntityPage.stream()
                .map(this::mapLibraryBookEntityToModel)
                .toList();
        //Preparing Map Response Object Data
        Map<String,Object> mapPageResponse = new HashMap<>();
        mapPageResponse.put("libraryBookResponse",libraryBookResponses);
        mapPageResponse.put("totalItems", libraryBookEntityPage.getTotalElements());
        mapPageResponse.put("totalPages", libraryBookEntityPage.getTotalPages());
        return mapPageResponse;
    }


    /**
     * getAuthorNameByBookId function fetches the author name based on bookId parameter
     * @param bookId as input parameter
     * @return String
     */
    @Override
    public String getAuthorNameByBookId(String bookId) {
        return libraryManagementRepository.getAuthorNameByBookId(bookId);
    }

}
