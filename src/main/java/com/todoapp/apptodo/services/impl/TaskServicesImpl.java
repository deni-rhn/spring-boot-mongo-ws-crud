package com.todoapp.apptodo.services.impl;

import com.mongodb.lang.Nullable;
import com.todoapp.apptodo.dto.TaskDto;
import com.todoapp.apptodo.entity.Task;
import com.todoapp.apptodo.entity.TaskStatus;
import com.todoapp.apptodo.event.TaskEvent;
import com.todoapp.apptodo.repo.TaskRepo;
import com.todoapp.apptodo.services.TaskService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TaskServicesImpl implements TaskService {

  @NonNull
  private TaskRepo taskRepo;

  @Autowired
  private final ApplicationEventPublisher publisher;

  public static Task taskDtoToEntity(TaskDto taskDto) {
    Task task = new Task();
    BeanUtils.copyProperties(taskDto, task);
    return task;
  }

  private final void publishTaskEvent(String eventType, Task task) {
    this.publisher.publishEvent(new TaskEvent(eventType, task));
  }

  @Override
  public Mono<Task> createTask(TaskDto taskDto) {
    if (Objects.nonNull(taskDto)) {
      return taskRepo.save(taskDtoToEntity(taskDto)).doOnSuccess(item -> publishTaskEvent(TaskEvent.TASK_CREATED, item));
    } else {
      return null;
    }
  }

  @Override
  public Mono<Task> findById(String id) {
    return taskRepo.findById(id);
  }

  @Override
  public Flux<Task> findAll() {
    return taskRepo.findAll().filter(item -> !item.getTaskStatus().equals(TaskStatus.DELETED));
  }

  @Override
  public Mono<Task> updateTask(TaskDto taskDto) {
    return taskRepo.findById(taskDto.getTaskId()).map(tsk -> taskDtoToEntity(taskDto)).flatMap(this.taskRepo::save)
      .doOnSuccess(item -> publishTaskEvent(TaskEvent.TASK_UPDATED, item));
  }

  @Override
  public Mono<Void> deleteTask(String id) {
    return taskRepo.deleteById(id);
  }

}
