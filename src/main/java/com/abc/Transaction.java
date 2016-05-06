package com.abc;

import java.util.Date;

public class Transaction
{
  protected static final String DESCRIPTION_PAD = "  ";
  protected static final String VALUE_PAD = " ";

  public final double amount;
  private Date transactionDate;

  public Transaction(double amount)
  {
    this.amount = amount;
    this.transactionDate = DateProvider.getInstance().now();
  }

  public double getAmount()
  {
    return amount;
  }

  public String getDescription()
  {
    final StringBuilder result = new StringBuilder();

    String description = amount < 0 ?
                         "withdrawal" :
                         "deposit";

    result.append(DESCRIPTION_PAD);
    result.append(description);
    result.append(VALUE_PAD);
    result.append(AccountValueFormatter.toDollars(amount));
    result.append("\n");

    return result.toString();
  }

  public Date getTransactionDate()
  {
    return transactionDate;
  }

  public boolean isWithdrawal()
  {
    return amount < 0;
  }
}
