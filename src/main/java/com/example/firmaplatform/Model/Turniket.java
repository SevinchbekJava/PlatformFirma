package com.example.firmaplatform.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Turniket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @CreatedBy
//    @Column(nullable = false)
    private UUID createdBy;

    @LastModifiedBy
//    @Column(nullable = false)
    private UUID updatedBy;

    @CreationTimestamp
    @Column(nullable = false)
    private Timestamp createdTime;

    @UpdateTimestamp
    @Column(nullable = false)
    private Timestamp updatedTime;
}
