package com.example.firmaplatform.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Salary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private double sum;

    @ManyToOne
    private Staff toWho;

    @CreationTimestamp
    @Column(nullable = false)
    private Timestamp createdTime;

    @UpdateTimestamp
    @Column(nullable = false)
    private Timestamp updatedTime;
}
