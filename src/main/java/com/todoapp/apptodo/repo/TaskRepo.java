package com.todoapp.apptodo.repo;

import com.todoapp.apptodo.entity.Task;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepo extends ReactiveMongoRepository<Task, String> {

}
