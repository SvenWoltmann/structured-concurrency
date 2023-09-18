package eu.happycoders.structuredconcurrency.demo3_suppliers.service;

import static eu.happycoders.structuredconcurrency.demo3_suppliers.SupplierDeliveryTimeCheck4_NestedStructuredTaskScopeUsingScopedValue.API_KEY;
import static eu.happycoders.structuredconcurrency.util.SimpleLogger.log;

import eu.happycoders.structuredconcurrency.demo3_suppliers.model.SupplierDeliveryTime;
import java.util.concurrent.ThreadLocalRandom;

public class SupplierDeliveryTimeServiceUsingScopedValue {

  private final boolean failAll;

  public SupplierDeliveryTimeServiceUsingScopedValue(boolean failAll) {
    this.failAll = failAll;
  }

  public SupplierDeliveryTime getDeliveryTime(String productId, String supplier)
      throws InterruptedException {
    String apiKey = API_KEY.get();
    log(
        "Retrieving delivery time from supplier %s (using API key %s)..."
            .formatted(supplier, apiKey));

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
