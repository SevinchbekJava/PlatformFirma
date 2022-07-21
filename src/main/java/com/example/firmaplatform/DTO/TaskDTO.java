package com.example.firmaplatform.DTO;

import com.example.firmaplatform.Model.Staff;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TaskDTO {
    private Integer maker;
    private String taskName;
    private String taskInfo;
    private String taskExpDate;
    private Integer staffId;
}
