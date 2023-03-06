package com.applause.auto.api.test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpHeaders;

@Log4j2
public class BaseAPITest {

  static {
    RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    RestAssured.useRelaxedHTTPSValidation();
  }
}
