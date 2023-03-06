package com.epochtime.utils;

import com.epochtime.Constants;
import java.time.Month;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

public class Helper {

  public static String wrapStringWithDoubleQuotes(String string) {
    return new StringBuilder().append("\"").append(string).append("\"").toString();
  }

  public static long secondsBeforeMonthStarts(Month month, boolean leapYear) {
    AtomicLong seconds = new AtomicLong();
    IntStream.range(1, month.getValue())
        .forEach(
            monthNumber -> {
              if (Month.of(monthNumber).equals(Month.FEBRUARY)) {
                seconds.addAndGet((leapYear ? 29 : 28) * Constants.SECONDS_IN_DAY);
              } else {
                seconds.addAndGet(Month.of(monthNumber).maxLength() * Constants.SECONDS_IN_DAY);
              }
            });
    return seconds.get();
  }
}
