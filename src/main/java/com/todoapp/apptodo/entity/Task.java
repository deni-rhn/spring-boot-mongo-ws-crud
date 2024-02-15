package com.todoapp.apptodo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(value = "task")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public @Data class Task implements Serializable {

  @Id
  private String taskId;
  private String taskTitle;
  private String taskDescription;
  private TaskStatus taskStatus;
  private boolean isActive;
}
