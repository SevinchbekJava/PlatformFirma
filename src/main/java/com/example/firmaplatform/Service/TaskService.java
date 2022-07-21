package com.example.firmaplatform.Service;

import com.example.firmaplatform.DTO.ApiResponse;
import com.example.firmaplatform.DTO.TaskDTO;
import com.example.firmaplatform.Model.Roles;
import com.example.firmaplatform.Model.Staff;
import com.example.firmaplatform.Model.Tasks;
import com.example.firmaplatform.Repository.RoleRepository;
import com.example.firmaplatform.Repository.StaffRepository;
import com.example.firmaplatform.Repository.TaskRepository;
import com.example.firmaplatform.Roles.RoleName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    StaffRepository staffRepository;
    @Autowired
    RoleRepository roleRepository;

    public ApiResponse createTask(TaskDTO taskDTO){
        Optional<Staff> optionalStaff = staffRepository.findById(taskDTO.getStaffId());
        Optional<Roles> optionalRoles=roleRepository.findById(taskDTO.getMaker());
        if (optionalStaff.isPresent()){
            if (optionalRoles.get().getRoleName().equals(RoleName.DIRECTOR) || optionalRoles.get().getRoleName().equals(RoleName.MANAGER)){
                Tasks tasks=new Tasks();
                tasks.setTaskName(taskDTO.getTaskName());
                tasks.setTaskInfo(taskDTO.getTaskInfo());
                tasks.setTaskExpDate(taskDTO.getTaskExpDate());
                tasks.setStaff(optionalStaff.get());
                if (optionalRoles.get().getRoleName().equals(RoleName.DIRECTOR) && !optionalStaff.get().getRoles().equals(RoleName.DIRECTOR) || optionalRoles.get().getRoleName().equals(RoleName.MANAGER) && (!optionalStaff.get().getRoles().equals(RoleName.DIRECTOR) && !optionalStaff.get().getRoles().equals(RoleName.MANAGER))){
                    taskRepository.save(tasks);
                    emailVerification(optionalRoles.get().getRoleName().toString(), optionalStaff.get().getEmail(), taskDTO.getTaskName(), taskDTO.getTaskInfo());
                    return new ApiResponse("Topshiriq muvaffaqiyatli yuborildi!", true);
                }
                return new ApiResponse(optionalRoles.get().getRoleName()+" Sizga vazifa qoʻshishga ruxsat yoʻq!",false);
            }
            return new ApiResponse("Qo'shilmadi", true);
        }
        return new ApiResponse("Xodim id-si topilmadi",false);
    }
    public ApiResponse readTask(Integer id){
        Optional<Staff> optionalStaff = staffRepository.findById(id);
        if (optionalStaff.isPresent()){
            Optional<Tasks> optionalTasks=taskRepository.findByStaff(optionalStaff.get());
            return optionalTasks.map(tasks -> new ApiResponse(tasks.getTaskName() + "\n" + tasks.getTaskInfo() + "\n" + tasks.getTaskExpDate(), true)).orElseGet(() -> new ApiResponse("this staff has no tasks", false));
        }
        return new ApiResponse("Xodim topilmadi", false);
    }
    public boolean emailVerification(String fromEmail, String userEmail,String taskName, String taskInfo){
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(fromEmail);
            mailMessage.setTo(userEmail);
            mailMessage.setSubject("Topshiriq: "+taskName);
            mailMessage.setText(taskInfo);
            javaMailSender.send(mailMessage);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
