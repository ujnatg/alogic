package com.applause.auto.api.test;

import static com.epochtime.Constants.DataSets.DATE_TO_EPOCH_SCENARIOS;
import static com.epochtime.Constants.DataSets.EPOCH_TO_DATE_SCENARIOS;

import com.epochtime.client.EpochConverterClient;
import com.epochtime.testdata.DataProviders;
import io.qameta.allure.Allure;
import io.qameta.allure.Story;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

@Log4j2
public class BlackBoxAPITest extends BaseAPITest {

  EpochConverterClient epochConverterClient = new EpochConverterClient();

  @SneakyThrows
  @Story("Epoch to Datetime")
  @Test(dataProvider = EPOCH_TO_DATE_SCENARIOS, dataProviderClass = DataProviders.class)
  public void epochToDateTimeTest(
      String epochTime, String expectedResponse, int statusCode, String description) {
    Allure.getLifecycle().updateTestCase(testResult -> testResult.setName(description));
    SoftAssert softAssert = new SoftAssert();
    ExtractableResponse response =
        epochConverterClient.convert(epochTime, ContentType.TEXT);

    softAssert.assertEquals(
        response.statusCode(), statusCode, "Unexpected response code " + description);

    softAssert.assertEquals(
        response.asString(), expectedResponse, "Unexpected response " + description);

    softAssert.assertAll();
  }

  @SneakyThrows
  @Story("Datetime to epoch")
  @Test(dataProvider = DATE_TO_EPOCH_SCENARIOS, dataProviderClass = DataProviders.class)
  public void dateTimeToEpochTest(
      String epochTime, String dateTime, int statusCode, String description) {
    Allure.getLifecycle().updateTestCase(testResult -> testResult.setName(description));
    SoftAssert softAssert = new SoftAssert();
    ExtractableResponse response =
        epochConverterClient.convert(dateTime, ContentType.TEXT);

    softAssert.assertEquals(
        response.statusCode(), statusCode, "Unexpected response code " + description);

    softAssert.assertEquals(response.asString(), epochTime, "Unexpected response " + description);

    softAssert.assertAll();
  }
}
