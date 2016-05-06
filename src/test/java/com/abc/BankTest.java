package com.abc;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class BankTest
{
  private static final double DOUBLE_DELTA = 1e-15;

  private Bank bank;
  private Customer customer;

  @Before
  public void initialise()
  {
    bank = new Bank();

    customer = bank.addCustomer("Arwyn",
                                "Anthony",
                                "001-01-0101");
  }

  @Test
  public void testCustomerReport()
  {
    customer.openAccount(AccountType.CHECKING);

    final List<CustomerSummary> expected = new ArrayList<CustomerSummary>();
    expected.add(new CustomerSummary("Arwyn",
                                     "Anthony",
                                     1));

    assertEquals(expected,
                 bank.customerSummary());
  }

  @Test
  public void testInterestPaidOnCheckingAccountWithoutDeposit()
  {
    Account account = customer.openAccount(AccountType.CHECKING);

    assertEquals(0.0,
                 bank.totalInterestPaid(),
                 DOUBLE_DELTA);
  }

  @Test
  public void testInterestPaidOnCheckingAccountWithDeposit()
  {
    Account account = customer.openAccount(AccountType.CHECKING);

    account.deposit(100.0);

    assertEquals(0.1,
                 bank.totalInterestPaid(),
                 DOUBLE_DELTA);
  }

  @Test
  public void testInterestPaidOnSavingsAccountWithoutDeposit()
  {
    Account account = customer.openAccount(AccountType.SAVINGS);

    assertEquals(0.0,
                 bank.totalInterestPaid(),
                 DOUBLE_DELTA);

  }

  @Test
  public void testInterestPaidOnSavingsAccountWithDepositLessThanHigherThreshold()
  {
    Account account = customer.openAccount(AccountType.SAVINGS);

    account.deposit(1000.0);

    assertEquals(1.0,
                 bank.totalInterestPaid(),
                 DOUBLE_DELTA);

  }

  @Test
  public void testInterestPaidOnSavingsAccountWithDepositMoreThanHigherThreshold()
  {
    Account account = customer.openAccount(AccountType.SAVINGS);

    assertEquals(0.0,
                 bank.totalInterestPaid(),
                 DOUBLE_DELTA);

    account.deposit(1500.0);

    assertEquals(2.0,
                 bank.totalInterestPaid(),
                 DOUBLE_DELTA);
  }


  @Test
  public void testInterestPaidOnMaxiSavingsAccountWithoutDeposit()
  {
    Account account = customer.openAccount(AccountType.SUPER_SAVINGS);

    assertEquals(0.0,
                 bank.totalInterestPaid(),
                 DOUBLE_DELTA);
  }

  @Test
  public void testInterestPaidOnMaxiSavingsAccountWithDepositLessThanFirstThreshold()
  {
    Account account = customer.openAccount(AccountType.SUPER_SAVINGS);

    account.deposit(1000.0);

    assertEquals(20.0,
                 bank.totalInterestPaid(),
                 DOUBLE_DELTA);
  }
  @Test
  public void testInterestPaidOnMaxiSavingsAccountWithDepositLessThanSecondThreshold()
  {
    Account account = customer.openAccount(AccountType.SUPER_SAVINGS);

    account.deposit(2000.0);

    assertEquals(70.0,
                 bank.totalInterestPaid(),
                 DOUBLE_DELTA);

  }

  @Test
  public void testInterestPaidOnMaxiSavingsAccountWithDepositMoreThanSecondThreshold()
  {
    Account account = customer.openAccount(AccountType.SUPER_SAVINGS);

    account.deposit(3000.0);

    assertEquals(170.0,
                 bank.totalInterestPaid(),
                 DOUBLE_DELTA);
  }
}
