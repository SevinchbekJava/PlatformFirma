package com.example.firmaplatform.Service;

import com.example.firmaplatform.DTO.*;
import com.example.firmaplatform.Model.Roles;
import com.example.firmaplatform.Model.Staff;
import com.example.firmaplatform.Model.Tasks;
import com.example.firmaplatform.Repository.StaffRepository;
import com.example.firmaplatform.Repository.RoleRepository;
import com.example.firmaplatform.Repository.TaskRepository;
import com.example.firmaplatform.Roles.RoleName;
import com.example.firmaplatform.WebToken.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StaffService implements UserDetailsService {
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    Token token;
    @Autowired
    StaffRepository staffRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    AuthenticationManager authenticationManager;

    public ApiResponse createDirector(DirectorDTO directorDTO){
        try {
//            boolean b = roleRepository.existsByRoleName(RoleName.DIRECTOR);
//            if (b) return new ApiResponse("Director already registered", false);
            Staff staff=new Staff();
            staff.setFirstName(directorDTO.getFirstName());
            staff.setLastName(directorDTO.getLastName());
            staff.setPhoneNumber(directorDTO.getPhoneNumber());
            staff.setEmail(directorDTO.getEmail());
            staff.setPassword(passwordEncoder.encode(directorDTO.getPassword()));
            staff.setRoles(roleRepository.findByRoleName(RoleName.DIRECTOR));
            staff.setType(false);
            staff.setEnabled(true);
            staffRepository.save(staff);
            return new ApiResponse("Direktor roʻyxatdan oʻtdi!", true);
        }catch (Exception e){
            e.printStackTrace();
            return new ApiResponse("Allaqachon ro'yxatdan o'tgan", true);
        }
    }

    public ApiResponse createManager(StaffDTO staffDTO){
        Optional<Staff> optionalStaff=staffRepository.findById(staffDTO.getStaffId());
        Optional<Roles> optionalRoles=roleRepository.findById(staffDTO.getTypeRole());
        if (optionalStaff.isPresent() && optionalStaff.get().getEnabled()){
            if (optionalStaff.get().getRoles().getRoleName().equals(RoleName.DIRECTOR) || optionalStaff.get().getRoles().getRoleName().equals(RoleName.MANAGER)){
                Staff staff=new Staff();
                staff.setFirstName(staffDTO.getFirstName());
                staff.setLastName(staffDTO.getLastName());
                staff.setPhoneNumber(staffDTO.getPhoneNumber());
                staff.setEmail(staffDTO.getEmail());
                staff.setPassword(passwordEncoder.encode(staffDTO.getPassword()));
                staff.setRoles(roleRepository.findByRoleName(RoleName.DIRECTOR));
                staff.setEmailCode(UUID.randomUUID().toString());
                if (optionalRoles.get().getRoleName().equals(RoleName.DIRECTOR) || optionalRoles.get().getRoleName().equals(RoleName.MANAGER)  && (optionalStaff.get().getRoles().getRoleName().equals(RoleName.MANAGER) || optionalStaff.get().getRoles().getRoleName().equals(RoleName.USER))){
                    return new ApiResponse(optionalRoles.get().getRoleName()+" You are not allowed to add "+optionalStaff.get().getRoles().getRoleName().toString(),false);
                }
                staff.setRoles(roleRepository.findByRoleName(optionalRoles.get().getRoleName()));
                staff.setType(false);
                boolean verification = emailVerification(staff.getEmail(), staff.getEmailCode());
                if (verification){
                    staffRepository.save(staff);
                    return new ApiResponse("Xodim roʻyxatdan oʻtdi, elektron pochtaga tasdiqlash kodi yuborildi, iltimos kodni tasdiqlang!!!", true);
                }
                else return new ApiResponse("Mavjud emas", false);
            }
            return new ApiResponse("Xodim qo'shilmadi", false);
        }
        return new ApiResponse("Login mavjud emas!", false);
    }

    public List<Staff> readStaff(GetStaffDTO getStaffDTO){
        Optional<Roles> optionalRoles=roleRepository.findById(getStaffDTO.getId());
        if (optionalRoles.get().getRoleName().equals(RoleName.DIRECTOR) || optionalRoles.get().getRoleName().equals(RoleName.MANAGER)){
            return staffRepository.findAll();
        }
        return null;
    }
    public ApiResponse updatePassword(LoginDTO loginDTO){
        Optional<Staff> byEmail = staffRepository.findByEmail(loginDTO.getLogin());
        if (byEmail.isPresent() && byEmail.get().getEnabled()){
            Staff staff=byEmail.get();
            staff.setPassword(loginDTO.getPassword());
            staffRepository.save(staff);
            return new ApiResponse("Parol muvaffaqiyatli yangilandi", true);
        }
        return new ApiResponse("Bunday foydalanuvchi nomi topilmadi!",false);
    }
    public boolean emailVerification(String userEmail, String userCode){
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("turmatovsevinchbek2000@gmail.com");
            mailMessage.setTo(userEmail);
            mailMessage.setSubject("Elektron pochtani tekshirish");
            mailMessage.setText("<a href='http://localhost:8080/auth/emailConfirm?userCode="+userCode+"&userEmail="+userEmail+"'>Confirm email</a>");
            javaMailSender.send(mailMessage);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    public ApiResponse emailConfirmation(String userEmail, String userCode) {
        Optional<Staff> optionalStaff=staffRepository.findByEmailAndEmailCode(userEmail, userCode);
        if (optionalStaff.isPresent()){
            Staff staff=optionalStaff.get();
            staff.setEnabled(true);
            staff.setEmailCode(null);
            staffRepository.save(staff);
            String token1 = token.getToken("turmatovsevinchbek2000@gmail.com", staff.getRoles());
            System.out.println(token1);
            return new ApiResponse("Muvaffaqiyatli tasdiqlandi", true);
        }
        return new ApiResponse("Allaqachon faollashtirilgan", false);
    }
    public ApiResponse loginStaff(LoginDTO loginDTO){
        try {
            Optional<Staff> optionalStaff=staffRepository.findByEmail(loginDTO.getLogin());
            if (optionalStaff.isPresent()){
                Optional<Tasks> tasksOptional=taskRepository.findByStaff(optionalStaff.get());
                String taskInfo="";
                String taskName="Topshiriq topilmadi";
                String taskExpDate="";
                if (tasksOptional.isPresent()){
                    taskInfo=tasksOptional.get().getTaskInfo();
                    taskName=tasksOptional.get().getTaskName();
                    taskExpDate=tasksOptional.get().getTaskExpDate();
                }
                Staff staff=optionalStaff.get();
                staff.setType(true);
                staffRepository.save(staff);
                Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getLogin(), loginDTO.getPassword()));
                Staff staff1 = (Staff) authenticate.getPrincipal();
                String token1 = token.getToken(loginDTO.getLogin(), staff1.getRoles());
                return new ApiResponse("Profilingizga xush kelibsiz!!!\n"+taskName+"\n"+taskInfo+"\n"+taskExpDate,true, token1);
            }
            else {
                return new ApiResponse("Login xato",false);
            }
        }catch (BadCredentialsException e){
            e.getStackTrace();
            return new ApiResponse("Login yoki password xato", false);
        }
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return staffRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username+" not found username"));
    }


    public ApiResponse logOut(LoginDTO loginDTO) {
        try {
            Optional<Staff> optionalStaff=staffRepository.findByEmail(loginDTO.getLogin());
            if (optionalStaff.isPresent()){
                Staff staff=optionalStaff.get();
                staff.setType(false);
                staffRepository.save(staff);
                return new ApiResponse("Logout", true);
            }
            else {
                return new ApiResponse("Login xato",false);
            }
        }catch (BadCredentialsException e){
            e.getStackTrace();
            return new ApiResponse("Login yoki password xato", false);
        }
    }
}
