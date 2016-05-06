package com.abc;

/**
 * Created by arwyn.anthony on 5/3/2016.
 */
public class MaxiSavingsInterestCalculator implements InterestCalculator
{
  private double m_lowerThreshold;
  private double m_lowerInterestRate;
  private double m_higherThreshold;
  private double m_middleInterestRate;
  private double m_higherInterestRate;

  public MaxiSavingsInterestCalculator(final double lowerInterestRate,
                                       final double lowerThreshold,
                                       final double middleInterestRate,
                                       final double higherThreshold,
                                       final double higherInterestRate)
  {
    m_lowerThreshold = lowerThreshold;
    m_lowerInterestRate = lowerInterestRate;
    m_higherThreshold = higherThreshold;
    m_middleInterestRate = middleInterestRate;
    m_higherInterestRate = higherInterestRate;
  }

  public double getInterestEarned(final Account account)
  {
    double result = 0.0;

    // can we earn -ive interest?
    double accountValue = account.getValue();

    if ( accountValue > 0.0 )
    {
      if ( accountValue <= m_lowerThreshold )
      {
        result = accountValue * m_lowerInterestRate;
      }
      else
      {
        if ( accountValue > m_lowerThreshold && accountValue <= m_higherThreshold )
        {
          result = 20 + (accountValue - m_lowerThreshold) * m_middleInterestRate;
        }
        else
        {
          result = 70 + (accountValue - m_higherThreshold) * m_higherInterestRate;
        }
      }
    }

    return result;
  }
}
