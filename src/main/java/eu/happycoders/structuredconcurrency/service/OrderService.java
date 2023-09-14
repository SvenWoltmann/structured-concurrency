package eu.happycoders.structuredconcurrency.service;

import static eu.happycoders.structuredconcurrency.util.SimpleLogger.log;

import eu.happycoders.structuredconcurrency.model.Order;
import java.util.concurrent.ThreadLocalRandom;

public class OrderService {

  public Order getOrder(int orderId) throws InterruptedException {
    log("Loading order");
    try {
      Thread.sleep(ThreadLocalRandom.current().nextLong(500, 1000));
    } catch (InterruptedException e) {
      log("Order loading was interrupted");
      throw e;
    }

    if (ThreadLocalRandom.current().nextDouble() < 0.2) {
      log("Error loading order");
      throw new RuntimeException("Error loading order");
    }

    log("Finished loading order");
    return new Order();
  }
}
