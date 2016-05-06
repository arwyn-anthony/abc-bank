package com.abc;

import java.util.Calendar;
import java.util.Date;

public class DateProvider
{
  private static DateProvider instance = new DateProvider();
  private Calendar calendar = Calendar.getInstance();

  public static DateProvider getInstance()
  {
    return instance;
  }

  private DateProvider()
  {

  }

  public Date now()
  {
    return calendar.getTime();
  }

  public void setDayMonthYear(final int day,
                              final int month,
                              final int year)
  {
    calendar.set(Calendar.DAY_OF_MONTH,
                 day);
    calendar.set(Calendar.MONTH,
                 month);
    calendar.set(Calendar.YEAR,
                 year);

    Date now = calendar.getTime();
  }

  // NB
  // very simple implementation
  // ignores: year boundaries, leap years
  // i.e. anything comlicated.
  public boolean dateWithinNDays(final Date transactionDate,
                                 final int withdrawalPeriod)
  {
    calendar.add(Calendar.DAY_OF_YEAR, -1 * withdrawalPeriod);

    Date cutoff = calendar.getTime();

    return transactionDate.after(cutoff);
  }
}
