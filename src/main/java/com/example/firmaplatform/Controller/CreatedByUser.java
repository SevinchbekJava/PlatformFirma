package com.example.firmaplatform.Controller;

import com.example.firmaplatform.Model.Staff;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class CreatedByUser implements AuditorAware<Integer> {
    @Override
    public Optional<Integer> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication!=null && authentication.isAuthenticated() && !authentication.getPrincipal().equals("anonymousUser")){
            Staff staff = (Staff) authentication.getPrincipal();
            return Optional.of(staff.getId());
        }
        return Optional.empty();
    }
}
