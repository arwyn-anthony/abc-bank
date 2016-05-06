package com.abc;

/**
 * Created by arwyn.anthony on 5/3/2016.
 */
public enum AccountType
{
  CHECKING("Checking Account"),
  SAVINGS("Savings Account"),
  SUPER_SAVINGS("Super Savings Account"),
  LIMITED_WITHDRAWAL_SAVINGS("Limited Withdrawal Savings Account");

  private final String description;

  AccountType(final String description)
  {
    this.description = description;
  }

  public String getDescription()
  {
    return description;
  }
}
