package com.abc;

/**
 * Created by arwyn.anthony on 5/3/2016.
 */
public class DefaultInterestCalculator implements InterestCalculator
{

  private double interestRate;

  public DefaultInterestCalculator(final double interestRate)
  {
    this.interestRate = interestRate;
  }

  public double getInterestEarned(final Account account)
  {
    return account.getValue() * interestRate;
  }
}
