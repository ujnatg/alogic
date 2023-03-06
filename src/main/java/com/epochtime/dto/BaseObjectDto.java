package com.epochtime.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressWarnings("unused")
public abstract class BaseObjectDto {

  @JsonIgnore private ObjectMapper objectMapper = new ObjectMapper();

  @JsonIgnore public int statusCode;
  @JsonIgnore public int errorCode;
}
