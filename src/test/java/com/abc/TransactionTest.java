package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TransactionTest
{
  @Test
  public void testCreationOfTransaction()
  {
    double amount = 5;
    Transaction transaction = new Transaction(amount);
    assertTrue(transaction.getAmount() == amount);
  }
}
