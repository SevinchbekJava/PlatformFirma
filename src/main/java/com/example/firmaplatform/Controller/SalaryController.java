package com.example.firmaplatform.Controller;

import com.example.firmaplatform.DTO.ApiResponse;
import com.example.firmaplatform.DTO.GetStaffDTO;
import com.example.firmaplatform.DTO.SalaryDTO;
import com.example.firmaplatform.Service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/salary")
public class SalaryController {
    @Autowired
    SalaryService salaryService;
    @PostMapping("/pay")
    public HttpEntity<?> salaryPay(@RequestBody SalaryDTO salaryDTO){
        ApiResponse apiResponse=salaryService.salaryTo(salaryDTO);
        return ResponseEntity.status(apiResponse.getType()?200:408).body(apiResponse.getMessage());
    }
    @PostMapping("/readPay")
    public HttpEntity<?> readPay(@RequestBody GetStaffDTO getStaffDTO){
        return ResponseEntity.ok(salaryService.salaryList(getStaffDTO));
    }
}
