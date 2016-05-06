package com.abc;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class CustomerTest
{
  private static final double DOUBLE_DELTA = 1e-15;

  @Rule
  public ExpectedException exception = ExpectedException.none();

  Customer target;

  @Before
  public void initialise()
  {
    target = new Customer("Arwyn",
                          "Anthony",
                          "001-01-0101");
  }

  @Test //Test customer statement generation
  public void testAccountStatement()
    throws
    InsufficientFundsException
  {
    Account checkingAccount = target.openAccount(AccountType.CHECKING);
    Account savingsAccount = target.openAccount(AccountType.SAVINGS);

    checkingAccount.deposit(100.0);
    savingsAccount.deposit(4000.0);
    savingsAccount.withdraw(200.0);

    String statement = target.getStatement();

    assertEquals("Statement for Arwyn\n" +
                 "\n" +
                 "Checking Account\n" +
                 "  deposit $100.00\n" +
                 "Total $100.00\n" +
                 "\n" +
                 "Savings Account\n" +
                 "  deposit $4,000.00\n" +
                 "  withdrawal $200.00\n" +
                 "Total $3,800.00\n" +
                 "\n" +
                 "Total In All Accounts $3,900.00",
                 statement);
  }

  @Test
  public void testOpenCheckingAccount()
  {
    testOpenAccountOfType(target,
                          AccountType.CHECKING);
    assertEquals(1,
                 target.getNumberOfAccounts());
  }

  @Test
  public void testOpenSavingsAccount()
  {
    testOpenAccountOfType(target,
                          AccountType.SAVINGS);
    assertEquals(1,
                 target.getNumberOfAccounts());
  }

  @Test
  public void testOpenMaxiSavingsAccount()
  {
    testOpenAccountOfType(target,
                          AccountType.SUPER_SAVINGS);
    assertEquals(1,
                 target.getNumberOfAccounts());
  }

  @Test
  public void testOpenMultipleAccounts()
  {
    testOpenAccountOfType(target,
                          AccountType.CHECKING);
    testOpenAccountOfType(target,
                          AccountType.SAVINGS);
    testOpenAccountOfType(target,
                          AccountType.SUPER_SAVINGS);
    assertEquals(3,
                 target.getNumberOfAccounts());
  }

  @Test
  public void testCustomerSummary()
  {
    CustomerSummary expected = new CustomerSummary("Arwyn",
                                                   "Anthony",
                                                   1);
    target.openAccount(AccountType.CHECKING);
    CustomerSummary actual = target.getSummary();

    assertEquals(expected,
                 actual);
  }

  private void testOpenAccountOfType(final Customer customer,
                                     final AccountType requiredType)
  {
    customer.openAccount(requiredType);

    assertTrue(customer.hasAccountOfType(requiredType));
  }

  @Test
  public void testAccountTransferWhenAccountUnavailable()
    throws
    AccountUnavailableException,
    InsufficientFundsException
  {
    Account checkingAccount = target.openAccount(AccountType.CHECKING);

    exception.expect(AccountUnavailableException.class);
    target.transfer(AccountType.SAVINGS,
                    AccountType.CHECKING,
                    100.0);
  }

  @Test
  public void testAccountTransferWhenInsuffientFunds()
    throws
    AccountUnavailableException,
    InsufficientFundsException
  {
    Account checkingAccount = target.openAccount(AccountType.CHECKING);
    Account savingsAccount = target.openAccount(AccountType.SAVINGS);

    exception.expect(InsufficientFundsException.class);
    target.transfer(AccountType.SAVINGS,
                    AccountType.CHECKING,
                    100.0);
  }

  @Test
    public void testAccountTransferWhenSuffientFunds()
      throws
      AccountUnavailableException,
      InsufficientFundsException
    {
      Account checkingAccount = target.openAccount(AccountType.CHECKING);

      Account savingsAccount = target.openAccount(AccountType.SAVINGS);

      savingsAccount.deposit(1000);

      target.transfer(AccountType.SAVINGS,
                      AccountType.CHECKING,
                      100.0);

      assertEquals(900.0,
                   savingsAccount.getValue(),
                   DOUBLE_DELTA);

      assertEquals(100.0,
                   checkingAccount.getValue(),
                   DOUBLE_DELTA);

    }
}
