package eu.happycoders.structuredconcurrency.demo1_invoice.service;

import static eu.happycoders.structuredconcurrency.util.SimpleLogger.log;

import eu.happycoders.structuredconcurrency.demo1_invoice.model.Order;
import java.util.concurrent.ThreadLocalRandom;

public class OrderService {

  public Order getOrder(int orderId) throws InterruptedException {
    log("Loading order");
    try {
      long minSleepTime = Properties.PRESENTATION_MODE ? 900 : 500;
      long maxSleepTime = 1000;
      Thread.sleep(ThreadLocalRandom.current().nextLong(minSleepTime, maxSleepTime));
    } catch (InterruptedException e) {
      log("Order loading was interrupted");
      throw e;
    }

    if (!Properties.PRESENTATION_MODE) {
      if (ThreadLocalRandom.current().nextDouble() < 0.2) {
        log("Error loading order");
        throw new RuntimeException("Error loading order");
      }
    }

    log("Finished loading order");
    return new Order();
  }
}
