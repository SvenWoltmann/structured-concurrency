package eu.happycoders.structuredconcurrency.demo3_suppliers;

import static eu.happycoders.structuredconcurrency.util.SimpleLogger.log;

import eu.happycoders.structuredconcurrency.demo3_suppliers.model.SupplierDeliveryTime;
import eu.happycoders.structuredconcurrency.demo3_suppliers.service.SupplierDeliveryTimeServiceUsingScopedValue;
import java.util.List;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.StructuredTaskScope.Subtask;
import java.util.concurrent.StructuredTaskScope.Subtask.State;

public class SupplierDeliveryTimeCheck4_NestedStructuredTaskScopeUsingScopedValue {

  private static final boolean FAIL_ALL = false;

  public static void main(String[] args) throws Exception {
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
    try (StructuredTaskScope<SupplierDeliveryTime> scope = new StructuredTaskScope<>()) {
      List<Subtask<SupplierDeliveryTime>> subtasks =
          productIds.stream()
              .map(productId -> scope.fork(() -> getSupplierDeliveryTime(productId, supplierIds)))
              .toList();

      scope.join();

      return subtasks.stream()
          .filter(subtask -> subtask.state() == State.SUCCESS)
          .map(Subtask::get)
          .toList();
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
