package com.todoapp.apptodo.services;

import com.todoapp.apptodo.dto.TaskDto;
import com.todoapp.apptodo.entity.Task;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TaskService {
  Mono<Task> createTask(TaskDto taskDto);

  Mono<Task> findById(String id);

  Flux<Task> findAll();

  Mono<Task> updateTask(TaskDto taskDto);

  Mono<Void> deleteTask(String id);
}
