package com.example.firmaplatform.Controller;

import com.example.firmaplatform.DTO.*;
import com.example.firmaplatform.Service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class StaffController {
    @Autowired
    StaffService staffService;

    @PostMapping("/createDirector")
    public HttpEntity<?> createDirector(@RequestBody DirectorDTO directorDTO){
        ApiResponse apiResponse =staffService.createDirector(directorDTO);
        return ResponseEntity.status(apiResponse.getType()?200:409).body(apiResponse.getMessage());
    }
    @PostMapping("/createManager")
    public HttpEntity<?> createManager(@RequestBody StaffDTO staffDTO){
        ApiResponse apiResponse =staffService.createManager(staffDTO);
        return ResponseEntity.status(apiResponse.getType()?200:409).body(apiResponse.getMessage());
    }
    @GetMapping("/emailConfirm")
    public HttpEntity<?> email(@RequestParam String userEmail, @RequestParam String userCode){
        ApiResponse apiResponse = staffService.emailConfirmation(userEmail, userCode);
        return ResponseEntity.status(apiResponse.getType()?201:409).body(apiResponse.getMessage());
    }
    @PostMapping("/login")
    public HttpEntity<?> login(@RequestBody LoginDTO loginDTO){
        ApiResponse apiResponse = staffService.loginStaff(loginDTO);
        return ResponseEntity.status(apiResponse.getType()?200:401).body(apiResponse);
    }
    @PostMapping("/logout")
    public HttpEntity<?> loginOut(@RequestBody LoginDTO loginDTO){
        ApiResponse apiResponse = staffService.logOut(loginDTO);
        return ResponseEntity.status(apiResponse.getType()?200:401).body(apiResponse);
    }
    @PostMapping("/updatePassword")
    public HttpEntity<?> updatePassword(@RequestBody LoginDTO loginDTO){
        ApiResponse apiResponse=staffService.updatePassword(loginDTO);
        return ResponseEntity.status(apiResponse.getType()?200:409).body(apiResponse.getMessage());
    }
    @PostMapping("/getStaff")
    public HttpEntity<?> readStaff(@RequestBody GetStaffDTO getStaffDTO){
        return ResponseEntity.ok(staffService.readStaff(getStaffDTO));
    }
}
