package com.projectlms.projectlms.controller;

import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projectlms.projectlms.domain.dto.ReviewDto;
import com.projectlms.projectlms.service.ReviewService;

@RestController
@RequestMapping(value = "/course/{cid}/review")
//@CrossOrigin(origins = "https://edutiv-springboot.herokuapp.com")

public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping(value = "")
    //@PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> addReview(@PathVariable(value = "cid") Long courseId, @RequestBody ReviewDto request) {
        request.setCourseId(courseId);
        return reviewService.addReview(request);
    }

    @GetMapping(value = "")
    public ResponseEntity<Object> getAllReview(@PathVariable(value = "cid") Long courseId) {
        return reviewService.getAllReview(courseId);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getReviewDetail(@PathVariable(value = "cid") Long courseId, @PathVariable(value = "id") Long id) {
        return reviewService.getReviewDetail(courseId, id);
    }

    @DeleteMapping(value = "/{id}")
    //@PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> deleteReview(@PathVariable(value = "cid") Long courseId, @PathVariable(value = "id") Long id) {
        return reviewService.deleteReview(courseId, id);
    }

    @PutMapping(value = "/{id}")
    //@PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> updateReview(@PathVariable(value = "cid") Long courseId, @PathVariable(value = "id") Long id, @RequestBody ReviewDto request) {
        request.setCourseId(courseId);
        return reviewService.updateReview(id,request);
    }
    
}
