package com.example.firmaplatform.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Tasks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String taskName;

    @Column(nullable = false)
    private String taskInfo;

    @Column(nullable = false)
    private String taskExpDate;

    @OneToOne
    private Staff staff;
}
