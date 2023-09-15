package eu.happycoders.structuredconcurrency.demo1_invoice.service;

import static eu.happycoders.structuredconcurrency.util.SimpleLogger.log;

import eu.happycoders.structuredconcurrency.demo1_invoice.model.Customer;
import java.util.concurrent.ThreadLocalRandom;

public class CustomerService {

  public Customer getCustomer(int customerId) throws InterruptedException {
    log("Loading customer");
    try {
      long minSleepTime = Properties.PRESENTATION_MODE ? 300 : 500;
      long maxSleepTime = Properties.PRESENTATION_MODE ? 400 : 1000;
      Thread.sleep(ThreadLocalRandom.current().nextLong(minSleepTime, maxSleepTime));
    } catch (InterruptedException e) {
      log("Customer loading was interrupted");
      throw e;
    }

    if (!Properties.PRESENTATION_MODE) {
      if (ThreadLocalRandom.current().nextDouble() < 0.2) {
        log("Error loading customer");
        throw new RuntimeException("Error loading customer");
      }
    }

    log("Finished loading customer");
    return new Customer();
  }
}
