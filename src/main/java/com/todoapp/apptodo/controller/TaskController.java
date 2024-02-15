package com.todoapp.apptodo.controller;

import com.todoapp.apptodo.common.ServicePath;
import com.todoapp.apptodo.dto.TaskDto;
import com.todoapp.apptodo.entity.Task;
import com.todoapp.apptodo.services.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
public class TaskController {

  private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

  @Autowired
  private final TaskService taskService;

  @PostMapping(ServicePath.CREATE_TASK)
  public ResponseEntity<Mono<Task>> createTask(@RequestBody TaskDto taskDto) throws ExecutionException, InterruptedException {
    Mono<Task> taskCreate = taskService.createTask(taskDto);

    return new ResponseEntity<Mono<Task>>(taskCreate, HttpStatus.OK);
  }

  @GetMapping(ServicePath.GET_TASK_BY_ID)
  public ResponseEntity<Mono<Task>> findById(@PathVariable("taskId") String id) {
    Mono<Task> findTaskById = taskService.findById(id);

    return new ResponseEntity<Mono<Task>>(findTaskById, HttpStatus.OK);
  }

  @GetMapping(ServicePath.GET_TASK_LIST)
  public Flux<Task> findAll() {

    return taskService.findAll();
  }

  @PutMapping(ServicePath.UPDATE_TASK)
  public Mono<Task> updateTask(@RequestBody TaskDto taskDto) {
    return taskService.updateTask(taskDto);
  }

}
