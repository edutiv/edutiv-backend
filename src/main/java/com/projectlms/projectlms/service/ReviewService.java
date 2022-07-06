package com.projectlms.projectlms.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projectlms.projectlms.constant.AppConstant;
import com.projectlms.projectlms.domain.dao.Course;
import com.projectlms.projectlms.domain.dao.Review;
import com.projectlms.projectlms.domain.dao.User;
import com.projectlms.projectlms.domain.dto.ReviewDto;
import com.projectlms.projectlms.repository.CourseRepository;
import com.projectlms.projectlms.repository.ReviewRepository;
import com.projectlms.projectlms.repository.UserRepository;
import com.projectlms.projectlms.util.ResponseUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, CourseRepository courseRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<Object> addReview(ReviewDto request) {
        try {
            log.info("Save new review: {}", request);

            log.info("Find user by user id");
            Optional<User> user = userRepository.findById(request.getUserId());
            if(user.isEmpty()) return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.NOT_FOUND);

            log.info("Find course by course id");
            Optional<Course> course = courseRepository.searchCourseById(request.getCourseId());
            if(user.isEmpty()) return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.NOT_FOUND);

            Review review = Review.builder()
                .user(user.get())
                .rating(request.getRating())
                .review(request.getReview())
                .course(course.get())
                .build();
                review = reviewRepository.save(review);

            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, review, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Get an error by executing create new review, Error : {}",e.getMessage());
            return ResponseUtil.build(AppConstant.ResponseCode.UNKNOWN_ERROR,null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> getAllReview(Long courseId) {
        try {
            log.info("Find course detail by course id: {}", courseId);
            Optional<Course> courseDetail = courseRepository.searchCourseById(courseId);
            if (courseDetail.isEmpty()) {
                log.info("course not found");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.NOT_FOUND);
            }
            log.info("Get all review");
            List<Review> reviews = reviewRepository.searchAllReviews(courseId);
            if (reviews.isEmpty()) {
                log.info("reviews is empty");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.NOT_FOUND);
            }
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, reviews, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Get an error by get all reviews, Error : {}",e.getMessage());
            return ResponseUtil.build(AppConstant.ResponseCode.UNKNOWN_ERROR,null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> getReviewDetail(Long courseId, Long id) {
        try {
            log.info("Find course detail by course id: {}", courseId);
            Optional<Course> courseDetail = courseRepository.searchCourseById(courseId);
            if (courseDetail.isEmpty()) {
                log.info("course not found");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.NOT_FOUND);
            }

            log.info("Find review detail by review id: {}", id);
            Optional<Review> reviewDetail = reviewRepository.searchReviewById(id, courseId);
            if (reviewDetail.isEmpty()) {
                log.info("review not found");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.NOT_FOUND);
            }
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, reviewDetail.get(), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Get an error by executing get review by id, Error : {}",e.getMessage());
            return ResponseUtil.build(AppConstant.ResponseCode.UNKNOWN_ERROR,null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> deleteReview(Long courseId, Long id) {
        try {
            log.info("Find course detail by course id: {}", courseId);
            Optional<Course> courseDetail = courseRepository.searchCourseById(courseId);
            if (courseDetail.isEmpty()) {
                log.info("course not found");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.NOT_FOUND);
            }
            log.info("Find review");
            Optional<Review> review = reviewRepository.searchReviewById(id, courseId);
            if (review.isEmpty()) {
                log.info("review not found");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.NOT_FOUND);
            }

            log.info("Executing delete review by id: {}", id);
            reviewRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            log.error("Data not found. Error: {}", e.getMessage());
            return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.NOT_FOUND);
        }
        return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, null, HttpStatus.OK);
    }

    public ResponseEntity<Object> updateReview(Long id, ReviewDto request) {
        try {
            log.info("Find course by course id");
            Optional<Course> course = courseRepository.searchCourseById(request.getCourseId());
            if(course.isEmpty()) {
                log.info("course not found");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.NOT_FOUND);
            }
            log.info("Find review");
            Optional<Review> review = reviewRepository.searchReviewById(id, request.getCourseId());
            if (review.isEmpty()) {
                log.info("review not found");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.NOT_FOUND);
            }
            log.info("Update review: {}", request);
            review.get().setRating(request.getRating());
            review.get().setReview(request.getReview());
            review.get().setCourse(course.get());
            reviewRepository.save(review.get());
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, review.get(), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Get an error by update review, Error : {}",e.getMessage());
            return ResponseUtil.build(AppConstant.ResponseCode.UNKNOWN_ERROR,null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
 