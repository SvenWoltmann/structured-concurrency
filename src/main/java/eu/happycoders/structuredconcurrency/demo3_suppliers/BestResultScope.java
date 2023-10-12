package eu.happycoders.structuredconcurrency.demo3_suppliers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.StructuredTaskScope;
import java.util.function.Supplier;

public class BestResultScope<T> extends StructuredTaskScope<T> {

  private final Comparator<T> comparator;

  private T bestResult;
  private final List<Throwable> exceptions = Collections.synchronizedList(new ArrayList<>());

  public BestResultScope(Comparator<T> comparator) {
    this.comparator = comparator;
  }

  @Override
  protected void handleComplete(Subtask<? extends T> subtask) {
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
  }

  public <X extends Throwable> T resultOrElseThrow(Supplier<? extends X> exceptionSupplier)
      throws X {
    ensureOwnerAndJoined();
    if (bestResult != null) {
      return bestResult;
    } else {
      X exception = exceptionSupplier.get();
      exceptions.forEach(exception::addSuppressed);
      throw exception;
    }
  }
}
