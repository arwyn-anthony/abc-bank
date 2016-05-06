package com.abc;

import static java.lang.Math.abs;

/**
 * Created by arwyn.anthony on 5/4/2016.
 */
public class AccountValueFormatter
{
  public static String toDollars(double d)
  {
    return String.format("$%,.2f",
                         abs(d));
  }
}
