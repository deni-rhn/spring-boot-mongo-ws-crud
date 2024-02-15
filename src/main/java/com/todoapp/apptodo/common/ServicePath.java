package com.todoapp.apptodo.common;

public class ServicePath {
  public static final String BASE_PATH = "/v1/task";

  public static final String WS_TASK_EVENT = BASE_PATH + "/ws";

  public static final String CREATE_TASK = BASE_PATH + "/create";

  public static final String UPDATE_TASK = BASE_PATH + "/update";

  public static final String GET_TASK_BY_ID = BASE_PATH + "/{taskId}";

  public static final String GET_TASK_LIST = BASE_PATH + "/list";
}
