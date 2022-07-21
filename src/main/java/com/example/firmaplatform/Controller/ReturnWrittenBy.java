package com.example.firmaplatform.Controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.UUID;

@Configuration
@EnableJpaAuditing
public class ReturnWrittenBy {
    @Bean
    AuditorAware<Integer> auditorAware(){
        return new CreatedByUser();
    }
}
