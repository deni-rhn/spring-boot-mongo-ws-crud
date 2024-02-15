package com.todoapp.apptodo.event;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import reactor.core.publisher.FluxSink;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.function.Consumer;

@Component
public class TaskEventPublisher implements ApplicationListener<TaskEvent>, Consumer<FluxSink<TaskEvent>> {

  private final Executor executor;
  private final BlockingQueue<TaskEvent> queue;

  TaskEventPublisher() {
    this.executor = Executors.newSingleThreadExecutor();
    this.queue = new LinkedBlockingDeque<>();
  }

  @Override
  public void onApplicationEvent(TaskEvent taskEvent) {
    this.queue.offer(taskEvent);
  }

  @Override
  public void accept(FluxSink<TaskEvent> sink) {
    this.executor.execute(() -> {
      while (true) {
        try {
          TaskEvent taskEvent = queue.take();
          sink.next(taskEvent);
        } catch (InterruptedException exception) {
          ReflectionUtils.rethrowRuntimeException(exception);
        }
      }
    });
  }
}
