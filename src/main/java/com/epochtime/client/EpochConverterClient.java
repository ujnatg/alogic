package com.epochtime.client;

import static io.restassured.RestAssured.given;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;

public class EpochConverterClient {

  private static String baseUri = "https://helloacm.com/api/unix-timestamp-converter/?cached";
  private static String epochTimeUri = baseUri + "&s={epochTimeStamp}";

  public ExtractableResponse convert(
      String epochTimeStamp, ContentType contentType) {
    return given(
            new RequestSpecBuilder()
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .addFilter(new AllureRestAssured())
                .build())
        .baseUri(baseUri)
        .contentType(contentType)
        .pathParam("epochTimeStamp", epochTimeStamp)
        .get(epochTimeUri)
        .then()
        .extract();
  }
}
