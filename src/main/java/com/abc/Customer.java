package com.abc;



import java.util.ArrayList;
import java.util.List;


public class Customer
{
  private final String lastName;
  private final String governmentid;
  private String firstName;
  private List<Account> accounts;

  public Customer(final String firstName,
                  final String lastName,
                  final String governmentId)
  {
    this.firstName = firstName;
    this.lastName = lastName;
    this.governmentid = governmentId;
    this.accounts = new ArrayList<Account>(0);
  }

  public String getFirstName()
  {
    return firstName;
  }

  public String getLastName()
  {
    return lastName;
  }

  public String getGovernmentid()
  {
    return governmentid;
  }

  public List<Account> getAccounts()
  {
    return accounts;
  }

  public CustomerSummary getSummary()
  {
    return new CustomerSummary(firstName, lastName, accounts.size());
  }

  public Account openAccount(AccountType accountType)
  {
    Account account = new Account(accountType);
    accounts.add(account);
    return account;
  }

  public int getNumberOfAccounts()
  {
    return accounts.size();
  }

  public double totalInterestEarned()
  {
    double total = 0;
    for ( Account a : accounts )
    {
      total += a.getInterestEarned();
    }
    return total;
  }

  public String getStatement()
  {
    StringBuilder statement = new StringBuilder();

    statement.append("Statement for " + firstName + "\n");
    statement.append("\n");

    double total = 0.0;
    for ( Account account : accounts )
    {
      statement.append(account.getStatement());
      statement.append("\n");
      total += account.getValue();
    }

    statement.append("Total In All Accounts " + AccountValueFormatter.toDollars(total));

    return statement.toString();
  }

  private String statementForAccount(Account a)
  {
    String s = "";

    //Translate to pretty account type
    switch (a.getAccountType())
    {
      case CHECKING:
        s += "Checking Account\n";
        break;
      case SAVINGS:
        s += "Savings Account\n";
        break;
      case SUPER_SAVINGS:
        s += "Maxi Savings Account\n";
        break;
    }

    //Now total up all the transactions
    double total = 0.0;
    for ( Transaction t : a.getTransactions() )
    {
      s += "  " + (t.amount < 0 ?
                   "withdrawal" :
                   "deposit") + " " + AccountValueFormatter.toDollars(t.amount) + "\n";
      total += t.amount;
    }
    s += "Total " + AccountValueFormatter.toDollars(total);
    return s;
  }

  public boolean hasAccountOfType(final AccountType required)
  {
    boolean result = false;

    for(Account account : accounts)
    {
      if(account.getAccountType() == required)
      {
        result=true;
        break;
      }
    }
    return result;
  }

  public void transfer(final AccountType fromAccount,
                       final AccountType toAccount,
                       final double amount)
    throws
    AccountUnavailableException,
    InsufficientFundsException
  {
    if(hasAccountOfType(fromAccount)
       && hasAccountOfType(toAccount))
    {
      Account from = getAccountOfType(fromAccount);
      Account to = getAccountOfType(toAccount);

      if(from.getValue() > amount)
      {
        from.withdraw(amount);
        to.deposit(amount);
      }
      else
      {
        throw new InsufficientFundsException();
      }
    }
    else
    {
      throw new AccountUnavailableException();
    }

  }

  private Account getAccountOfType(final AccountType required)
  {
    Account result = null;

    for(Account account : accounts)
    {
      if(account.getAccountType() == required)
      {
        result=account;
        break;
      }
    }
    return result;
  }
}
