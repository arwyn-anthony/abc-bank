package com.abc;

import java.util.*;

public class Bank
{
  private List<Customer> customers;

  public Bank()
  {
    customers = new ArrayList<Customer>();
  }

  public Customer addCustomer(Customer customer)
  {
    customers.add(customer);
    return customer;
  }

  public List<CustomerSummary> customerSummary()
  {
    List<CustomerSummary>  summaries = new ArrayList<CustomerSummary>();

    for ( Customer customer : customers )
    {
      summaries.add(customer.getSummary());
    }

    return summaries;
  }

  //Make sure correct plural of word is created based on the number passed in:
  //If number passed in is 1 just return the word otherwise add an 's' at the end
  private String format(int number,
                        String word)
  {
    return number + " " + (number == 1 ?
                           word :
                           word + "s");
  }

  public double totalInterestPaid()
  {
    double total = 0;
    for ( Customer c : customers )
    {
      total += c.totalInterestEarned();
    }
    return total;
  }


  public Customer addCustomer(final String firstName,
                              final String lastName,
                              final String governmentId)
  {
    final Customer result = new Customer(firstName,lastName,governmentId);

    customers.add(result);

    return result;
  }
}
