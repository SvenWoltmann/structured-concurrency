package eu.happycoders.structuredconcurrency.demo1_invoice;

import static eu.happycoders.structuredconcurrency.util.SimpleLogger.log;

import eu.happycoders.structuredconcurrency.demo1_invoice.model.Customer;
import eu.happycoders.structuredconcurrency.demo1_invoice.model.Invoice;
import eu.happycoders.structuredconcurrency.demo1_invoice.model.InvoiceTemplate;
import eu.happycoders.structuredconcurrency.demo1_invoice.model.Order;
import eu.happycoders.structuredconcurrency.demo1_invoice.service.CustomerService;
import eu.happycoders.structuredconcurrency.demo1_invoice.service.InvoiceTemplateService;
import eu.happycoders.structuredconcurrency.demo1_invoice.service.OrderService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Future.State;

public class InvoiceGenerator4b_NewVirtualThreadPerTaskCancelling {

  static void main() throws Throwable {
    InvoiceGenerator4b_NewVirtualThreadPerTaskCancelling invoiceGenerator =
        new InvoiceGenerator4b_NewVirtualThreadPerTaskCancelling(
            new OrderService(), new CustomerService(), new InvoiceTemplateService());
    invoiceGenerator.createInvoice(10012, 61157, "en");
  }

  private final OrderService orderService;
  private final CustomerService customerService;
  private final InvoiceTemplateService invoiceTemplateService;

  public InvoiceGenerator4b_NewVirtualThreadPerTaskCancelling(
      OrderService orderService,
      CustomerService customerService,
      InvoiceTemplateService invoiceTemplateService) {
    this.orderService = orderService;
    this.customerService = customerService;
    this.invoiceTemplateService = invoiceTemplateService;
  }

  Invoice createInvoice(int orderId, int customerId, String language) throws Throwable {
    Future<Order> orderFuture;
    Future<Customer> customerFuture;
    Future<InvoiceTemplate> invoiceTemplateFuture;

    try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
      log("Submitting tasks");

      orderFuture =
          executor.submit(
              () -> {
                try {
                  return orderService.getOrder(orderId);
                } catch (Exception e) {
                  executor.shutdownNow();
                  throw e;
                }
              });

      // Attention, possible race conditions here:
      // If the thread above starts and fails and calls executor.shutdownNow() before the following
      // submit() is called, then submit() will throw a RejectedExecutionException.

      customerFuture =
          executor.submit(
              () -> {
                try {
                  return customerService.getCustomer(customerId);
                } catch (Exception e) {
                  executor.shutdownNow();
                  throw e;
                }
              });

      // Attention, possible race conditions here:
      // If one of the threads above starts and fails and calls executor.shutdownNow() before the
      // following submit() is called, then submit() will throw a RejectedExecutionException.

      invoiceTemplateFuture =
          executor.submit(
              () -> {
                try {
                  return invoiceTemplateService.getTemplate(language);
                } catch (Exception e) {
                  executor.shutdownNow();
                  throw e;
                }
              });

      log("Waiting for executor to shut down...");
    }

    if (orderFuture.state() == State.SUCCESS
        && customerFuture.state() == State.SUCCESS
        && invoiceTemplateFuture.state() == State.SUCCESS) {
      Order order = orderFuture.get();
      Customer customer = customerFuture.get();
      InvoiceTemplate invoiceTemplate = invoiceTemplateFuture.get();

      log("Generating and returning invoice");
      return Invoice.generate(order, customer, invoiceTemplate);
    } else if (orderFuture.state() == State.FAILED
        && !(orderFuture.exceptionNow() instanceof InterruptedException)) {
      throw orderFuture.exceptionNow();
    } else if (customerFuture.state() == State.FAILED
        && !(customerFuture.exceptionNow() instanceof InterruptedException)) {
      throw customerFuture.exceptionNow();
    } else if (invoiceTemplateFuture.state() == State.FAILED) {
      throw invoiceTemplateFuture.exceptionNow();
    } else if (orderFuture.state() == State.FAILED) {
      throw orderFuture.exceptionNow();
    } else if (customerFuture.state() == State.FAILED) {
      throw customerFuture.exceptionNow();
    } else {
      throw new IllegalStateException();
    }
  }
}
