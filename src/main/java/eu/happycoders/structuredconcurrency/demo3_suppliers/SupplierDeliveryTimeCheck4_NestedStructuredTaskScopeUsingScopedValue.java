package eu.happycoders.structuredconcurrency.demo3_suppliers;

import static eu.happycoders.structuredconcurrency.util.SimpleLogger.log;

import eu.happycoders.structuredconcurrency.demo3_suppliers.model.SupplierDeliveryTime;
import eu.happycoders.structuredconcurrency.demo3_suppliers.service.SupplierDeliveryTimeServiceUsingScopedValue;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.StructuredTaskScope.Joiner;
import java.util.concurrent.StructuredTaskScope.Subtask;

public class SupplierDeliveryTimeCheck4_NestedStructuredTaskScopeUsingScopedValue {

  private static final boolean FAIL_ALL = false;

  static void main() throws Exception {
    SupplierDeliveryTimeCheck4_NestedStructuredTaskScopeUsingScopedValue supplierDeliveryTimeCheck =
        new SupplierDeliveryTimeCheck4_NestedStructuredTaskScopeUsingScopedValue(
            new SupplierDeliveryTimeServiceUsingScopedValue(FAIL_ALL));
    List<SupplierDeliveryTime> responses =
        supplierDeliveryTimeCheck.getSupplierDeliveryTimes(
            List.of("B004V9OA84", "0201310090", "0134685997"),
            List.of("A", "B", "C", "D", "E"),
            "t0p-s3cr3t");
    log("Responses: " + responses);
  }

  private final SupplierDeliveryTimeServiceUsingScopedValue service;

  public SupplierDeliveryTimeCheck4_NestedStructuredTaskScopeUsingScopedValue(
      SupplierDeliveryTimeServiceUsingScopedValue service) {
    this.service = service;
  }

  public static final ScopedValue<String> API_KEY = ScopedValue.newInstance();

  List<SupplierDeliveryTime> getSupplierDeliveryTimes(
      List<String> productIds, List<String> supplierIds, String apiKey) throws Exception {
    return ScopedValue.where(API_KEY, apiKey)
        .call(() -> getSupplierDeliveryTimes(productIds, supplierIds));
  }

  List<SupplierDeliveryTime> getSupplierDeliveryTimes(
      List<String> productIds, List<String> supplierIds) throws InterruptedException {
    try (var scope =
        StructuredTaskScope.open(Joiner.<SupplierDeliveryTime>allSuccessfulOrThrow())) {
      productIds.forEach(
          productId -> scope.fork(() -> getSupplierDeliveryTime(productId, supplierIds)));

      return scope.join().map(Subtask::get).toList();
    }
  }

  SupplierDeliveryTime getSupplierDeliveryTime(String productId, List<String> supplierIds)
      throws InterruptedException {
    try (var scope =
        StructuredTaskScope.open(
            new BestResultJoiner<>(
                Comparator.comparing(SupplierDeliveryTime::deliveryTimeHours).reversed()))) {
      for (String supplierId : supplierIds) {
        scope.fork(() -> service.getDeliveryTime(productId, supplierId));
      }

      return scope.join();
    }
  }
}
