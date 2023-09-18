package eu.happycoders.structuredconcurrency.demo3_suppliers;

import static eu.happycoders.structuredconcurrency.util.SimpleLogger.log;

import eu.happycoders.structuredconcurrency.demo3_suppliers.model.SupplierDeliveryTime;
import eu.happycoders.structuredconcurrency.demo3_suppliers.service.SupplierDeliveryTimeService;
import java.util.List;

public class SupplierDeliveryTimeCheck2_StructuredTaskScope {

  private static final boolean FAIL_ALL = false;

  public static void main(String[] args)
      throws SupplierDeliveryTimeCheckException, InterruptedException {
    SupplierDeliveryTimeCheck2_StructuredTaskScope supplierDeliveryTimeCheck =
        new SupplierDeliveryTimeCheck2_StructuredTaskScope(
            new SupplierDeliveryTimeService(FAIL_ALL));
    SupplierDeliveryTime response =
        supplierDeliveryTimeCheck.getSupplierDeliveryTime(
            "B004V9OA84", List.of("A", "B", "C", "D", "E"));
    log("Response: " + response);
  }

  private final SupplierDeliveryTimeService service;

  public SupplierDeliveryTimeCheck2_StructuredTaskScope(SupplierDeliveryTimeService service) {
    this.service = service;
  }

  SupplierDeliveryTime getSupplierDeliveryTime(String productId, List<String> supplierIds)
      throws SupplierDeliveryTimeCheckException, InterruptedException {
    try (var scope = new GetFastestDeliveryTimeScope()) {
      for (String supplierId : supplierIds) {
        scope.fork(() -> service.getDeliveryTime(productId, supplierId));
      }

      scope.join();
      return scope.result();
    }
  }
}
