package com.example.firmaplatform.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Staff implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String phoneNumber;
    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @CreationTimestamp
    @Column(nullable = false)
    private Timestamp createdTime;

    @UpdateTimestamp
    @Column(nullable = false)
    private Timestamp updatedTime;

    private String emailCode;

    @Column(nullable = false)
    private boolean type;

    @OneToOne
    private Roles roles;



    private Boolean accountNonExpired = true;
    private Boolean accountNonLocked = true;
    private Boolean credentialsNonExpired = true;
    private Boolean enabled = false;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(roles);
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
