package com.abc;

/**
 * Created by arwyn.anthony on 5/3/2016.
 */
public class StandardSavingsInterestCalculator implements InterestCalculator
{

  private double lowerThreshold;
  private double lowerInterestRate;
  private double higherInterestRate;

  public StandardSavingsInterestCalculator(final double lowerInterestRate,
                                           final double threshHoldForHigherInterest,
                                           final double higherInterestRate)
  {
    this.lowerThreshold = threshHoldForHigherInterest;
    this.lowerInterestRate = lowerInterestRate;
    this.higherInterestRate = higherInterestRate;
  }

  public double getInterestEarned(final Account account)
  {
    double result = 0.0;

    double accountValue = account.getValue();

    if ( accountValue > 0.0 )
    {
      if ( accountValue <= lowerThreshold )
      {
        result = accountValue * lowerInterestRate;
      }
      else
      {
        result = 1 + (accountValue - lowerThreshold) * higherInterestRate;
      }
    }
    return result;
  }

}
