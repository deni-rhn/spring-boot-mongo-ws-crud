package com.todoapp.apptodo.dto;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.todoapp.apptodo.entity.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public @Data class TaskDto implements Serializable {
  private String taskId;
  private String taskTitle;
  private String taskDescription;
  @JsonSetter(nulls = Nulls.SKIP)
  private TaskStatus taskStatus = TaskStatus.IN_PLAN;
  private boolean isActive;
}
