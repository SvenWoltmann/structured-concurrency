package eu.happycoders.structuredconcurrency.demo3_suppliers.service;

import static eu.happycoders.structuredconcurrency.util.SimpleLogger.log;

import eu.happycoders.structuredconcurrency.demo3_suppliers.model.SupplierDeliveryTime;
import java.util.concurrent.ThreadLocalRandom;

public class SupplierDeliveryTimeService {

  private final boolean failAll;

  public SupplierDeliveryTimeService(boolean failAll) {
    this.failAll = failAll;
  }

  public SupplierDeliveryTime getDeliveryTime(String productId, String supplier)
      throws InterruptedException {
    log("Retrieving delivery time from supplier " + supplier);

    try {
      Thread.sleep(ThreadLocalRandom.current().nextLong(250, 1000));
    } catch (InterruptedException e) {
      log("Retrieving delivery time from supplier " + supplier + " interrupted");
      throw e;
    }

    // 40% failure probability --> 2 out of 5 requests should fail
    if (failAll || ThreadLocalRandom.current().nextDouble() < 0.4) {
      log("Error retrieving delivery time from supplier " + supplier);
      throw new RuntimeException("Error retrieving delivery time from supplier " + supplier);
    }

    int deliveryTimeHours = ThreadLocalRandom.current().nextInt(1, 7 * 24);

    log(
        "Finished retrieving delivery time from supplier %s: %d hours"
            .formatted(supplier, deliveryTimeHours));

    return new SupplierDeliveryTime(supplier, deliveryTimeHours);
  }
}
