package com.abc;

/**
 * Created by arwyn.anthony on 5/5/2016.
 */
public class LimitedWithdrawalSavingsInterestCalculator implements InterestCalculator
{
  private final double lowerInterestRate;
  private final int withdrawalPeriod;
  private final double higherInterestRate;

  public LimitedWithdrawalSavingsInterestCalculator(final double lowerInterestRate,
                                                    final int withdrawalPeriod,
                                                    final double higherInterestRate)
  {
    this.lowerInterestRate = lowerInterestRate;
    this.withdrawalPeriod = withdrawalPeriod;
    this.higherInterestRate = higherInterestRate;
  }

  public double getInterestEarned(final Account account)
  {
    double result = 0.0;

        // can we earn -ive interest?
    double accountValue = account.getValue();

    if(accountValue > 0.0)
    {
      if(account.hasWithdrawalsInPeriod(withdrawalPeriod))
      {
        result = accountValue * lowerInterestRate;
      }
      else
      {
        result = accountValue * higherInterestRate;
      }
    }

    return result;
  }
}
