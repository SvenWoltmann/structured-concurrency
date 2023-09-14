package eu.happycoders.structuredconcurrency.service;

import static eu.happycoders.structuredconcurrency.util.SimpleLogger.log;

import eu.happycoders.structuredconcurrency.model.Customer;
import java.util.concurrent.ThreadLocalRandom;

public class CustomerService {

  public Customer getCustomer(int customerId) throws InterruptedException {
    log("Loading customer");
    try {
      Thread.sleep(ThreadLocalRandom.current().nextLong(500, 1000));
    } catch (InterruptedException e) {
      log("Customer loading was interrupted");
      throw e;
    }

    if (ThreadLocalRandom.current().nextDouble() < 0.2) {
      log("Error loading customer");
      throw new RuntimeException("Error loading customer");
    }

    log("Finished loading customer");
    return new Customer();
  }
}
