package com.example.firmaplatform.Service;

import com.example.firmaplatform.DTO.ApiResponse;
import com.example.firmaplatform.DTO.GetStaffDTO;
import com.example.firmaplatform.DTO.SalaryDTO;
import com.example.firmaplatform.DTO.StaffDTO;
import com.example.firmaplatform.Model.Roles;
import com.example.firmaplatform.Model.Salary;
import com.example.firmaplatform.Model.Staff;
import com.example.firmaplatform.Repository.RoleRepository;
import com.example.firmaplatform.Repository.SalaryRepository;
import com.example.firmaplatform.Repository.StaffRepository;
import com.example.firmaplatform.Roles.RoleName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SalaryService {
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    SalaryRepository salaryRepository;

    @Autowired
    StaffRepository staffRepository;

    public ApiResponse salaryTo(SalaryDTO salaryDTO){
        Optional<Staff> optionalStaff1=staffRepository.findByEmail(salaryDTO.getWho());
        if (optionalStaff1.get().getRoles().getRoleName().equals(RoleName.DIRECTOR) || optionalStaff1.get().getRoles().getRoleName().equals(RoleName.MANAGER)) {
            Optional<Staff> optionalStaff = staffRepository.findByEmail(salaryDTO.getToWho());
            if (optionalStaff.isPresent()){
                Salary salary=new Salary();
                salary.setSum(salaryDTO.getSum());
                salary.setToWho(optionalStaff.get());
                salaryRepository.save(salary);
                return new ApiResponse("Toʻlov muvaffaqiyatli amalga oshirildi!", true);
            }
            return new ApiResponse("Bunday xodim topilmadi!", false);
        }
        return new ApiResponse("Tolov amalga oshirilmadi!", false);
    }
    public List<Salary> salaryList(GetStaffDTO getStaffDTO){
        Optional<Roles> optionalRoles=roleRepository.findById(getStaffDTO.getId());
        if (optionalRoles.get().getRoleName().equals(RoleName.DIRECTOR) || optionalRoles.get().getRoleName().equals(RoleName.MANAGER)){
            return salaryRepository.findAll();
        }
        return null;
    }
}
