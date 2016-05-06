package com.abc;

import static sun.management.snmp.jvminstr.JvmThreadInstanceEntryImpl.ThreadStateMap.Byte1.other;

/**
 * Created by arwyn.anthony on 5/3/2016.
 */
public class CustomerSummary
{

  private final String firstName;
  private final String lastName;
  private final int numberOfAccounts;

  public CustomerSummary(final String firstName,
                         final String lastName,
                         final int numberOfAccounts)
  {
    this.firstName = firstName;
    this.lastName = lastName;
    this.numberOfAccounts = numberOfAccounts;
  }

  @Override
  public int hashCode()
  {
    return firstName.hashCode() + lastName.hashCode() + numberOfAccounts;
  }

  @Override
  public boolean equals(final Object other)
  {
    boolean result = false;

    if(this == other)
    {
      result = true;
    }
    else
    {
      if(other != null && other instanceof CustomerSummary)
      {
        CustomerSummary otherSummary = (CustomerSummary) other;

        if(firstName.equals(otherSummary.firstName)
          && lastName.equals(otherSummary.lastName)
          && numberOfAccounts == otherSummary.numberOfAccounts)
        {
          result = true;
        }
      }
    }

    return result;
  }
}
