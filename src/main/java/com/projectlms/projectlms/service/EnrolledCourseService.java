package com.projectlms.projectlms.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.projectlms.projectlms.constant.AppConstant;
import com.projectlms.projectlms.domain.dao.Course;
import com.projectlms.projectlms.domain.dao.EnrolledCourse;
import com.projectlms.projectlms.domain.dao.User;
import com.projectlms.projectlms.domain.dto.EnrolledCourseDto;
import com.projectlms.projectlms.repository.CourseRepository;
import com.projectlms.projectlms.repository.EnrolledCourseRepository;
import com.projectlms.projectlms.repository.UserRepository;
import com.projectlms.projectlms.util.ResponseUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EnrolledCourseService {
    
    private final EnrolledCourseRepository enrolledCourseRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    @Autowired
    public EnrolledCourseService(EnrolledCourseRepository enrolledCourseRepository, CourseRepository courseRepository, UserRepository userRepository) {
        this.enrolledCourseRepository = enrolledCourseRepository;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<Object> addEnrolledCourse(EnrolledCourseDto request) {
        try {
            log.info("Save new enrolled course: {}", request);

            log.info("Find user by user id");
            Optional<User> user = userRepository.findOne(request.getUserId());
            if(user.isEmpty()) return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.NOT_FOUND);

            log.info("Find course by course id");
            Optional<Course> course = courseRepository.findOne(request.getCourseId());
            if(user.isEmpty()) return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.NOT_FOUND);

            EnrolledCourse enrolledCourse = EnrolledCourse.builder()
                .user(user.get())
                .course(course.get())
                .build();
                enrolledCourse = enrolledCourseRepository.save(enrolledCourse);

            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, enrolledCourse, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Get an error by executing create new enrolled course, Error : {}",e.getMessage());
            return ResponseUtil.build(AppConstant.ResponseCode.UNKNOWN_ERROR,null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> getAllEnrolledCourse() {
        try {
            log.info("Get all enrolled course");
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, enrolledCourseRepository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Get an error by get all enrolled course, Error : {}",e.getMessage());
            return ResponseUtil.build(AppConstant.ResponseCode.UNKNOWN_ERROR,null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> getEnrolledCourseDetail(Long id) {
        try {
            log.info("Find request detail by enrolled course id: {}", id);
            Optional<EnrolledCourse> enrolledCourseDetail = enrolledCourseRepository.findOne(id);
            if (enrolledCourseDetail.isEmpty()) {
                log.info("enrolled course not found");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.NOT_FOUND);
            }
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, enrolledCourseDetail.get(), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Get an error by executing get enrolled course by id, Error : {}",e.getMessage());
            return ResponseUtil.build(AppConstant.ResponseCode.UNKNOWN_ERROR,null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> deleteEnrolledCourse(Long id) {
        try {
            log.info("Executing delete enrolled course by id: {}", id);
            enrolledCourseRepository.delete(id);
        } catch (EmptyResultDataAccessException e) {
            log.error("Data not found. Error: {}", e.getMessage());
            return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.NOT_FOUND);
        }
        return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, null, HttpStatus.OK);
    }

    // public ResponseEntity<Object> updateRequest(EnrolledCourseDto request, Long id) {
    //     try {
    //         log.info("Update request: {}", request);
    //         Optional<EnrolledCourse> reqCourse = requestRepository.findOne(id);
    //         if (reqCourse.isEmpty()) {
    //             log.info("request not found");
    //             return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.NOT_FOUND);
    //         }
    //         log.info("Find course by course id");
    //         Optional<Course> course = courseRepository.findOne(request.getCourseId());
    //         if(course.isEmpty()) {
    //             log.info("course not found");
    //             return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.NOT_FOUND);
    //         }
    //         reqCourse.get().setCourse(course.get());
    //         requestRepository.save(reqCourse.get());
    //         return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, reqCourse.get(), HttpStatus.OK);
    //     } catch (Exception e) {
    //         log.error("Get an error by update request, Error : {}",e.getMessage());
    //         return ResponseUtil.build(AppConstant.ResponseCode.UNKNOWN_ERROR,null,HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    // }

}