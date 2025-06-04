package eu.happycoders.structuredconcurrency.demo3_suppliers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.StructuredTaskScope.Joiner;
import java.util.concurrent.StructuredTaskScope.Subtask;

public class BestResultJoiner<T> implements Joiner<T, T> {

  private final Comparator<T> comparator;

  private T bestResult;
  private final List<Throwable> exceptions = Collections.synchronizedList(new ArrayList<>());

  public BestResultJoiner(Comparator<T> comparator) {
    this.comparator = comparator;
  }

  @Override
  public boolean onComplete(Subtask<? extends T> subtask) {
    switch (subtask.state()) {
      case UNAVAILABLE -> {
        // Ignore
      }
      case SUCCESS -> {
        T result = subtask.get();
        synchronized (this) {
          if (bestResult == null || comparator.compare(result, bestResult) > 0) {
            bestResult = result;
          }
        }
      }
      case FAILED -> exceptions.add(subtask.exception());
    }

    return false; // Don't cancel the scope
  }

  @Override
  public T result() throws SupplierDeliveryTimeCheckException {
    if (bestResult != null) {
      return bestResult;
    } else {
      SupplierDeliveryTimeCheckException exception = new SupplierDeliveryTimeCheckException();
      exceptions.forEach(exception::addSuppressed);
      throw exception;
    }
  }
}
