package eu.happycoders.structuredconcurrency.demo3_suppliers;

import static eu.happycoders.structuredconcurrency.util.SimpleLogger.log;

import eu.happycoders.structuredconcurrency.demo3_suppliers.model.SupplierDeliveryTime;
import eu.happycoders.structuredconcurrency.demo3_suppliers.service.SupplierDeliveryTimeService;
import java.util.ArrayList;
import java.util.List;

public class SupplierDeliveryTimeCheck1_Sequential {

  private static final boolean FAIL_ALL = false;

  public static void main(String[] args) throws SupplierDeliveryTimeCheckException {
    SupplierDeliveryTimeCheck1_Sequential supplierDeliveryTimeCheck =
        new SupplierDeliveryTimeCheck1_Sequential(new SupplierDeliveryTimeService(FAIL_ALL));
    SupplierDeliveryTime response =
        supplierDeliveryTimeCheck.getSupplierDeliveryTime(
            "B075PT2JH9", List.of("A", "B", "C", "D", "E"));
    log("Response: " + response);
  }

  private final SupplierDeliveryTimeService service;

  public SupplierDeliveryTimeCheck1_Sequential(SupplierDeliveryTimeService service) {
    this.service = service;
  }

  SupplierDeliveryTime getSupplierDeliveryTime(String productId, List<String> supplierIds)
      throws SupplierDeliveryTimeCheckException {
    List<Throwable> exceptions = new ArrayList<>();
    SupplierDeliveryTime fastestDeliveryTime = null;

    for (String supplierId : supplierIds) {
      try {
        SupplierDeliveryTime deliveryTime = service.getDeliveryTime(productId, supplierId);
        if (fastestDeliveryTime == null
            || deliveryTime.deliveryTimeHours() < fastestDeliveryTime.deliveryTimeHours()) {
          fastestDeliveryTime = deliveryTime;
        }
      } catch (Throwable e) {
        exceptions.add(e);
      }
    }

    log("--> fastestDeliveryTime = " + fastestDeliveryTime);
    log("--> exceptions          = " + exceptions);

    if (fastestDeliveryTime != null) {
      return fastestDeliveryTime;
    } else {
      SupplierDeliveryTimeCheckException exception = new SupplierDeliveryTimeCheckException();
      exceptions.forEach(exception::addSuppressed);
      throw exception;
    }
  }
}
