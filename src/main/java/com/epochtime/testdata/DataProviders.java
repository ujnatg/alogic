package com.epochtime.testdata;

import com.epochtime.Constants.DataSets;
import com.epochtime.utils.Helper;
import java.time.Month;
import org.testng.annotations.DataProvider;

public class DataProviders {

  public static final int HTTP_OK = 200;
  public static final int HTTP_ERROR = 400;

  @DataProvider(name = DataSets.EPOCH_TO_DATE_SCENARIOS)
  public Object[][] epochToDateScenariosProvider() {

    return new Object[][] {
      // Base tests
      // Numeric flow positive
      {"0", "\"1970-01-01 12:00:00\"", HTTP_OK, "Middle range allowed epoch time value"},
      {
        "9223372036854775807",
        "\"292277026596-12-04 03:30:07\"",
        HTTP_OK,
        "Maximum allowed epoch time value 64bit"
      },
      {"9223372036854775808", "false", HTTP_ERROR, "Outside valid range high 64bit"},
      {"-1", "\"1969-12-31 11:59:59\"", HTTP_OK, "Negative value test"},
      {
        "-9223372036854775808",
        "\"-292277022657-01-27 08:29:52\"",
        HTTP_OK,
        "Minimum allowed value 64bit"
      },
      {"-9223372036854775809", "false", HTTP_ERROR, "Outside valid range low"},

      // Switching seconds counter
      {"59", "\"1970-01-01 12:00:59\"", HTTP_OK, "Seconds counter border value"},
      {"60", "\"1970-01-01 12:01:00\"", HTTP_OK, "Seconds counter reset and increase minutes"},
      // Switching minutes counter
      {"" + (60 * 60 - 1), "\"1970-01-01 12:59:59\"", HTTP_OK, "Minutes counter border value"},
      {"3600", "\"1970-01-01 01:00:00\"", HTTP_OK, "Minutes counter reset and increase hour"},
      // Switching hours counter
      {"" + (24 * 60 * 60 - 1), "\"1970-01-01 11:59:59\"", HTTP_OK, "Hours counter border value"},
      {
        "" + 24 * 60 * 60,
        "\"1970-01-02 12:00:00\"",
        HTTP_OK,
        "Hours counter reset and increase day"
      },
      // Switching days month30 counter 4,6,9,11
      {
        "" + (Helper.secondsBeforeMonthStarts(Month.MAY, false) - 1),
        "\"1970-30-04 11:59:59\"",
        HTTP_OK,
        "Month April days counter border value"
      },
      {
        "" + Helper.secondsBeforeMonthStarts(Month.MAY, false),
        "\"1970-01-05 12:00:00\"",
        HTTP_OK,
        "Month April days counter reset and increase month"
      },
      {
        "" + (Helper.secondsBeforeMonthStarts(Month.JULY, false) - 1),
        "\"1970-30-06 11:59:59\"",
        HTTP_OK,
        "Month June days counter border value"
      },
      {
        "" + Helper.secondsBeforeMonthStarts(Month.JULY, false),
        "\"1970-01-07 12:00:00\"",
        HTTP_OK,
        "Month June days counter reset and increase month"
      },
      {
        "" + (Helper.secondsBeforeMonthStarts(Month.OCTOBER, false) - 1),
        "\"1970-30-09 11:59:59\"",
        HTTP_OK,
        "Month September days counter border value"
      },
      {
        "" + Helper.secondsBeforeMonthStarts(Month.OCTOBER, false),
        "\"1970-01-10 12:00:00\"",
        HTTP_OK,
        "Month September days counter reset and increase month"
      },
      {
        "" + (Helper.secondsBeforeMonthStarts(Month.DECEMBER, false) - 1),
        "\"1970-30-11 11:59:59\"",
        HTTP_OK,
        "Month November days counter border value"
      },
      {
        "" + Helper.secondsBeforeMonthStarts(Month.DECEMBER, false),
        "\"1970-01-12 12:00:00\"",
        HTTP_OK,
        "Month November days counter reset and increase month"
      },

      // TODO Switching days month31 counter  1, 3, 5, 7, 8, 10, 12 months
      // TODO Switching days month28 counter for not leap year
      // TODO Switching days month29 counter for leap year
      // TODO Switching year on 31 dec 23:59 + 1
      // TODO Add tests for negative time axis direction, if supported negative epoch

      // Numeric flow negative
      {"0.0", "false", HTTP_ERROR, "Decimal value"},
      {"0e0", "false", HTTP_ERROR, "Exp value"},
      {" 0", "false", HTTP_ERROR, "Leading space"},
      {"0 ", "false", HTTP_ERROR, "Trailing space"},
      {"0x0", "false", HTTP_ERROR, "Hex format"},
    };
  }

  @DataProvider(name = DataSets.DATE_TO_EPOCH_SCENARIOS)
  public Object[][] dateToEpochScenariosProvider() {

    return new Object[][] {
      // Base tests
      {"0", "\"1970-01-01 00:00:00\"", HTTP_OK, "Middle range allowed 0 seconds time"},
      {"39600", "\"1970-01-01 12:00:00\"", HTTP_OK, "Middle range allowed 0 seconds time"},
      {"43200", "\"1970-01-01 13:00:00\"", HTTP_OK, "Noon hours reset"},
      {
        "1",
        "\"1970-01-01 00:00:01\"",
        HTTP_OK,
        "Middle range allowed 0 year 0 month 0 day 0 hour 0 minute 1 second time"
      },
      {
        "61",
        "\"1970-01-01 00:01:01\"",
        HTTP_OK,
        "Middle range allowed 0 year 0 month 0 day 0 hour 1 minute 1 second time"
      },
      {
        "3661",
        "\"1970-01-01 01:01:01\"",
        HTTP_OK,
        "Middle range allowed 0 year 0 month 0 day 1 hour 1 minute one second time"
      },
      {
        "90061",
        "\"1970-01-01 01:01:01\"",
        HTTP_OK,
        "Middle range allowed 0 year 0 month 1 day 1 hour 1 minute one second time"
      },
      {
        "2768461",
        "\"1970-02-02 01:01:01\"",
        HTTP_OK,
        "Middle range allowed 0 year 1 month 1 day 1 hour 1 minute one second time"
      },
      {
        "34304461",
        "\"1971-02-02 01:01:01\"",
        HTTP_OK,
        "Middle range allowed 1 year 1 month 1 day 1 hour 1 minute one second time"
      },
      {
        "192070861",
        "\"1976-02-02 01:01:01\"",
        HTTP_OK,
        "Range allowed include lap year. 5 year 1 month 1 day 1 hour 1 minute one second time"
      },
      {"0", "\"1970-1-1 0:0:0\"", HTTP_OK, "Datetime format without leading 0"},
      {"false", "\"1970-00-00 00:00:01\"", HTTP_ERROR, "Bad data format"},
      {"0", "\"1970-01-01 13:00:00\"", HTTP_OK, "Noon hours reset"},
      // Negative
      {"false", "\"1970-01-01 12:00:60\"", HTTP_ERROR, "Bad datetime"},
      {"false", "\"1970-01-01 12:60:00\"", HTTP_ERROR, "Bad datetime"},
      {"false", "\"1970-01-01 25:00:00\"", HTTP_ERROR, "Bad datetime"},
      {"false", "\"1970-01-01 00:00:00\"", HTTP_ERROR, "Bad datetime"},
      {"false", "\"1970-01-01 25:00:00\"", HTTP_ERROR, "Bad datetime"},
      {"false", "\"1970-01-01 24:00:00\"", HTTP_ERROR, "Bad datetime"},
      {"false", "\"1970-13-01 00:00:00\"", HTTP_ERROR, "Bad datetime"},
      {"false", "\"1970-01-32 00:00:00\"", HTTP_ERROR, "Bad datetime"},
    };
  }
}
