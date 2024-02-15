package com.todoapp.apptodo.event;

import com.todoapp.apptodo.entity.Task;
import org.springframework.context.ApplicationEvent;

public class TaskEvent extends ApplicationEvent {

  public static final String TASK_CREATED = "CREATED";
  public static final String TASK_UPDATED = "UPDATED";

  private String eventType;

  public TaskEvent(String evtType, Task task) {
    super(task);
    this.eventType = evtType;
  }

  public String getEventType() {
    return eventType;
  }
}
