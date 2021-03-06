package com.projectlms.projectlms.controller;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.projectlms.projectlms.constant.AppConstant;
import com.projectlms.projectlms.domain.dao.User;
import com.projectlms.projectlms.domain.dto.UserDto;
import com.projectlms.projectlms.service.UserService;
import com.projectlms.projectlms.util.ResponseUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/user")
public class UserController {
    private final UserService userService;

    // @PostMapping(value = "")
    // @PreAuthorize("hasRole('ADMIN')")
    // public ResponseEntity<Object> addUser(@RequestBody UserDto request) {
    //     return userService.addUser(request);
    // }

    @GetMapping(value = "/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> getAllUser() {
        return userService.getAllUser();
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> getUserDetail(@PathVariable(value = "id") Long id) {
        return userService.getUserDetail(id);
    }

    @GetMapping(value = "")
    public ResponseEntity<Object> getUserDetail(Principal principal) {
        return userService.getUserDetail(principal.getName());
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> deleteUser(@PathVariable(value = "id") Long id) {
        return userService.deleteUser(id);
    }

    @PutMapping(value = "/edit-user")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> updateUserbyUser(Principal principal, @RequestBody UserDto request) {
        request.setEmail(principal.getName());
        return userService.updateUserbyUser(request);
    }

    @PutMapping(value = "/edit-admin/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> updateUserbyAdmin(@PathVariable(value = "id") Long id, @RequestBody UserDto request) {
        return userService.updateUserbyAdmin(id, request);
    }

    @PutMapping(value = "/change-password")
    public ResponseEntity<?> changePassword(Principal principal, @RequestBody UserDto request) {
        try{
            request.setEmail(principal.getName());
            User user = userService.changePassword(request);
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, user, HttpStatus.OK);
        } catch(Exception e) {
            return ResponseUtil.build(AppConstant.ResponseCode.UNKNOWN_ERROR,null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }   
}
