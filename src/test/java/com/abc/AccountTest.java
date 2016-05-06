package com.abc;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;

/**
 * Created by arwyn.anthony on 5/4/2016.
 */
public class AccountTest
{
  private static final double DOUBLE_DELTA = 1e-15;

  @Rule
  public ExpectedException exception = ExpectedException.none();

  private Account checkingAccount;
  private Account standardSavingAccount;
  private Account superSavingsAccount;
  private Account limtedWithdrawalAccount;

  @Before
  public void initialise()
  {
    checkingAccount = new Account(AccountType.CHECKING);
    standardSavingAccount = new Account(AccountType.SAVINGS);
    superSavingsAccount = new Account(AccountType.SUPER_SAVINGS);
    limtedWithdrawalAccount = new Account(AccountType.LIMITED_WITHDRAWAL_SAVINGS);
  }

  @Test
  public void testAccountNegativeDeposit()
  {
    exception.expect(IllegalArgumentException.class);

    checkingAccount.deposit(-1000);
  }

  @Test
  public void testAccountNegativeWithdrawal()
    throws
    InsufficientFundsException
  {
    exception.expect(IllegalArgumentException.class);

    checkingAccount.withdraw(-1000);
  }

  @Test
  public void testAccountWithdrawalGreaterThanFunds()
    throws
    InsufficientFundsException
  {
    checkingAccount.deposit(500);

    exception.expect(InsufficientFundsException.class);

    checkingAccount.withdraw(1000);
  }

  @Test
  public void testCheckingInterest()
  {
    checkingAccount.deposit(1000.0);

    double interest = checkingAccount.getInterestEarned();

    assertEquals(1.0,
                 interest,
                 DOUBLE_DELTA);
  }

  @Test
  public void testStandardSavingsInterestWhenLessThanthreshold()
  {
    standardSavingAccount.deposit(500);

    double interest = standardSavingAccount.getInterestEarned();

    assertEquals(0.5,
                 interest,
                 DOUBLE_DELTA);
  }

  @Test
  public void testStandardSavingsInterestWhenMoreThanThreshold()
  {
    standardSavingAccount.deposit(5000);

    double interest = standardSavingAccount.getInterestEarned();

    assertEquals(9.0,
                 interest,
                 DOUBLE_DELTA);
  }

  @Test
  public void testSuperSavingsInterestWhenLessThanLowerThreshold()
  {
    superSavingsAccount.deposit(1000);

    double interest = superSavingsAccount.getInterestEarned();

    assertEquals(20.0,
                 interest,
                 DOUBLE_DELTA);
  }

  @Test
  public void testSuperSavingsInterestWhenLessThanHigherThreshold()
  {
    superSavingsAccount.deposit(2000);

    double interest = superSavingsAccount.getInterestEarned();

    assertEquals(70,
                 interest,
                 DOUBLE_DELTA);
  }

  @Test
  public void testSuperSavingsInterestWhenMoreThanHigherThreshold()
  {
    superSavingsAccount.deposit(3000);

    double interest = superSavingsAccount.getInterestEarned();

    assertEquals(170,
                 interest,
                 DOUBLE_DELTA);
  }

  @Test
  public void testLimitedWithdrawalInterestWhenAccountHasNoWithdrawals()
  {
    limtedWithdrawalAccount.deposit(1000.0);

    double interest = limtedWithdrawalAccount.getInterestEarned();

    assertEquals(50.0,
                 interest,
                 DOUBLE_DELTA);
  }

  @Test
  public void testLimitedWithdrawalInterestWhenAccountHasNoWithdrawalsWithinPeriod()
    throws
    InsufficientFundsException
  {
    DateProvider.getInstance().setDayMonthYear(1,
                                               Calendar.APRIL,
                                               2016);

    limtedWithdrawalAccount.deposit(1000.0);
    limtedWithdrawalAccount.withdraw(500.0);

    DateProvider.getInstance().setDayMonthYear(5,
                                               Calendar.MAY,
                                               2016);

    double interest = limtedWithdrawalAccount.getInterestEarned();

    assertEquals(25.0,
                 interest,
                 DOUBLE_DELTA);
  }

  @Test
  public void testLimitedWithdrawalInterestWhenAccountHasWithdrawalsWithinPeriod()
    throws
    InsufficientFundsException
  {
    DateProvider.getInstance().setDayMonthYear(1,Calendar.MAY,2016);

    limtedWithdrawalAccount.deposit(1000.0);
    limtedWithdrawalAccount.withdraw(500.0);

    DateProvider.getInstance().setDayMonthYear(5,Calendar.MAY,2016);

    double interest = limtedWithdrawalAccount.getInterestEarned();

    assertEquals(0.5,
                 interest,
                 DOUBLE_DELTA);
  }
}
