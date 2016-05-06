package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Account
{
  private final AccountType accountType;
  private List<Transaction> transactions;
  private double totalValueOfAccount = 0.0;
  private InterestCalculator calculator;

  public Account(AccountType accountType)
  {
    this.calculator = InterestCalculatorFactory.getInterestCalculator(accountType);
    this.accountType = accountType;
    this.transactions = new ArrayList<Transaction>(0);
  }

  public void deposit(double amount)
  {
    if ( amount <= 0 )
    {
      throw new IllegalArgumentException("amount must be greater than zero");
    }
    else
    {
      transactions.add(new Transaction(amount));
      totalValueOfAccount = sumAllTransactions();
    }
  }

  public void withdraw(double amount)
    throws
    InsufficientFundsException
  {
    if ( amount <= 0 )
    {
      throw new IllegalArgumentException("amount must be greater than zero");
    }
    else if(amount > totalValueOfAccount)
    {
      throw new InsufficientFundsException();
    }
    else
    {
      transactions.add(new Transaction(-amount));
      totalValueOfAccount = sumAllTransactions();
    }
  }

  public double getInterestEarned()
  {
    return calculator.getInterestEarned(this);
  }

  public double sumAllTransactions()
  {
    double amount = 0.0;
    for ( Transaction t : transactions )
    {
      amount += t.amount;
    }
    return amount;
  }

  public AccountType getAccountType()
  {
    return accountType;
  }

  public List<Transaction> getTransactions()
  {
    return transactions;
  }

  public String getStatement()
  {
    StringBuilder result = new StringBuilder();

    result.append(accountType.getDescription());
    result.append("\n");

    for ( Transaction transaction : transactions )
    {
      result.append( transaction.getDescription());
    }

    result.append("Total " + AccountValueFormatter.toDollars(totalValueOfAccount));
    result.append("\n");

    return result.toString();
  }

  public double getValue()
  {
    return totalValueOfAccount;
  }

  public boolean hasWithdrawalsInPeriod(final int withdrawalPeriod)
  {
    boolean result = false;

    for ( Transaction transaction : transactions )
    {
      if(transaction.isWithdrawal()
      && DateProvider.getInstance().dateWithinNDays(transaction.getTransactionDate(), withdrawalPeriod))
      {
        result = true;
        break;
      }
    }

    return result;
  }
}
