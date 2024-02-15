package com.todoapp.apptodo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.todoapp.apptodo.entity.Task;
import com.todoapp.apptodo.event.TaskEvent;
import com.todoapp.apptodo.event.TaskEventPublisher;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;
import reactor.core.publisher.Flux;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
public class TaskWSController {

  private static final Logger logger = LoggerFactory.getLogger(TaskWSController.class);


  @Bean
  HandlerMapping handlerMapping(WebSocketHandler webSocketHandler) {
    return new SimpleUrlHandlerMapping() {{
      setUrlMap(Collections.singletonMap("/task/ws", webSocketHandler));
      setOrder(10);
    }};
  }

  @Bean
  WebSocketHandlerAdapter webSocketHandlerAdapter() {
    return new WebSocketHandlerAdapter();
  }

  @Bean
  WebSocketHandler webSocketHandler(TaskEventPublisher eventPublisher, ObjectMapper objectMapper) {
    Flux<TaskEvent> publish = Flux.create(eventPublisher).share();

    return session -> {
      Flux<WebSocketMessage> msgFlux = publish.map(evt -> {
        try {
          Task task = (Task) evt.getSource();
          Map<String, Task> data = new HashMap<>();
          data.put(evt.getEventType(), task);

          return objectMapper.writeValueAsString(data);
        } catch (JsonProcessingException j) {
          throw new RuntimeException(j);
        }
      }).map(str -> {
        logger.info("Publishing message to websocket : {}", str);
        return session.textMessage(str);
      });

      return session.send(msgFlux);
    };
  }
}
