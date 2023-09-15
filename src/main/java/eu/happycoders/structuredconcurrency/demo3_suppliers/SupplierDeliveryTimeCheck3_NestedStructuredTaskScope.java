package eu.happycoders.structuredconcurrency.demo3_suppliers;

import static eu.happycoders.structuredconcurrency.util.SimpleLogger.log;

import eu.happycoders.structuredconcurrency.demo3_suppliers.model.SupplierDeliveryTime;
import eu.happycoders.structuredconcurrency.demo3_suppliers.service.SupplierDeliveryTimeService;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.StructuredTaskScope.ShutdownOnSuccess;

public class SupplierDeliveryTimeCheck3_NestedStructuredTaskScope {

  private static final boolean FAIL_ALL = false;

  public static void main(String[] args)
      throws SupplierDeliveryTimeCheckException, InterruptedException, ExecutionException {
    SupplierDeliveryTimeCheck3_NestedStructuredTaskScope supplierDeliveryTimeCheck =
        new SupplierDeliveryTimeCheck3_NestedStructuredTaskScope(
            new SupplierDeliveryTimeService(FAIL_ALL));
    SupplierDeliveryTime response =
        supplierDeliveryTimeCheck.getSupplierDeliveryTime(
            List.of("B075PT2JH9", "1243", "asdf"), List.of("A", "B", "C", "D", "E"));
    log("Response: " + response);
  }

  private final SupplierDeliveryTimeService service;

  public SupplierDeliveryTimeCheck3_NestedStructuredTaskScope(SupplierDeliveryTimeService service) {
    this.service = service;
  }

  SupplierDeliveryTime getSupplierDeliveryTime(List<String> productIds, List<String> supplierIds)
      throws InterruptedException, ExecutionException {
    try (ShutdownOnSuccess<SupplierDeliveryTime> scope = new ShutdownOnSuccess<>()) {
      for (String productId : productIds) {
        scope.fork(() -> getSupplierDeliveryTime(productId, supplierIds));
      }

      scope.join();
      return scope.result();
    }
  }

  SupplierDeliveryTime getSupplierDeliveryTime(String productId, List<String> supplierIds)
      throws SupplierDeliveryTimeCheckException, InterruptedException {
    try (GetFastestDeliveryTimeScope scope = new GetFastestDeliveryTimeScope()) {
      for (String supplierId : supplierIds) {
        scope.fork(() -> service.getDeliveryTime(productId, supplierId));
      }

      scope.join();
      return scope.result();
    }
  }
}
