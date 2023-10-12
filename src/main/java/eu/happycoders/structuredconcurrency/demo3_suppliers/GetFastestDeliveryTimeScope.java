package eu.happycoders.structuredconcurrency.demo3_suppliers;

import static eu.happycoders.structuredconcurrency.util.SimpleLogger.log;

import eu.happycoders.structuredconcurrency.demo3_suppliers.model.SupplierDeliveryTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.StructuredTaskScope;

public class GetFastestDeliveryTimeScope extends StructuredTaskScope<SupplierDeliveryTime> {

  private SupplierDeliveryTime fastestDeliveryTime;
  private final List<Throwable> exceptions = Collections.synchronizedList(new ArrayList<>());

  @Override
  protected void handleComplete(Subtask<? extends SupplierDeliveryTime> subtask) {
    switch (subtask.state()) {
      case UNAVAILABLE -> {
        // Ignore
      }
      case SUCCESS -> {
        SupplierDeliveryTime deliveryTime = subtask.get();
        synchronized (this) {
          if (fastestDeliveryTime == null
              || deliveryTime.deliveryTimeHours() < fastestDeliveryTime.deliveryTimeHours()) {
            fastestDeliveryTime = deliveryTime;
          }
        }
      }
      case FAILED -> exceptions.add(subtask.exception());
    }
  }

  public SupplierDeliveryTime result() throws SupplierDeliveryTimeCheckException {
    ensureOwnerAndJoined();

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
