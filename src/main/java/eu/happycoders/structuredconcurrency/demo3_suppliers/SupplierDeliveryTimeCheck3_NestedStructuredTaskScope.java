package eu.happycoders.structuredconcurrency.demo3_suppliers;

import static eu.happycoders.structuredconcurrency.util.SimpleLogger.log;

import eu.happycoders.structuredconcurrency.demo3_suppliers.model.SupplierDeliveryTime;
import eu.happycoders.structuredconcurrency.demo3_suppliers.service.SupplierDeliveryTimeService;
import java.util.List;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.StructuredTaskScope.Subtask;
import java.util.concurrent.StructuredTaskScope.Subtask.State;

public class SupplierDeliveryTimeCheck3_NestedStructuredTaskScope {

  private static final boolean FAIL_ALL = false;

  public static void main(String[] args)
      throws SupplierDeliveryTimeCheckException, InterruptedException {
    SupplierDeliveryTimeCheck3_NestedStructuredTaskScope supplierDeliveryTimeCheck =
        new SupplierDeliveryTimeCheck3_NestedStructuredTaskScope(
            new SupplierDeliveryTimeService(FAIL_ALL));
    List<SupplierDeliveryTime> responses =
        supplierDeliveryTimeCheck.getSupplierDeliveryTimes(
            List.of("B004V9OA84", "0201310090", "0134685997"), List.of("A", "B", "C", "D", "E"));
    log("Responses: " + responses);
  }

  private final SupplierDeliveryTimeService service;

  public SupplierDeliveryTimeCheck3_NestedStructuredTaskScope(SupplierDeliveryTimeService service) {
    this.service = service;
  }

  List<SupplierDeliveryTime> getSupplierDeliveryTimes(
      List<String> productIds, List<String> supplierIds) throws InterruptedException {
    try (var scope = new StructuredTaskScope<SupplierDeliveryTime>()) {
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
    try (var scope = new GetFastestDeliveryTimeScope()) {
      for (String supplierId : supplierIds) {
        scope.fork(() -> service.getDeliveryTime(productId, supplierId));
      }

      scope.join();
      return scope.result();
    }
  }
}
