package com.abc;

/**
 * Created by arwyn.anthony on 5/3/2016.
 */
public class InterestCalculatorFactory
{
  public static InterestCalculator getInterestCalculator(final AccountType accountType)
  {
    InterestCalculator result;
    switch (accountType)
    {
      case SAVINGS:
        result = new StandardSavingsInterestCalculator(0.001,
                                                       1000.0,
                                                       0.002);
        break;
      case SUPER_SAVINGS:
        result = new MaxiSavingsInterestCalculator(0.02,
                                                   1000.0,
                                                   0.05,
                                                   2000.0,
                                                   0.1);
        break;
      case LIMITED_WITHDRAWAL_SAVINGS:
        result = new LimitedWithdrawalSavingsInterestCalculator(0.001,
                                                                10,
                                                                0.05);
        break;
      default:
        result = new DefaultInterestCalculator(0.001);
    }

    return result;
  }
}
