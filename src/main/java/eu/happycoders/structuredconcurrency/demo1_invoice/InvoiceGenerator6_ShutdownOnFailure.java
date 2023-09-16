package eu.happycoders.structuredconcurrency.demo1_invoice;

import static eu.happycoders.structuredconcurrency.util.SimpleLogger.log;

import eu.happycoders.structuredconcurrency.demo1_invoice.model.Customer;
import eu.happycoders.structuredconcurrency.demo1_invoice.model.Invoice;
import eu.happycoders.structuredconcurrency.demo1_invoice.model.InvoiceTemplate;
import eu.happycoders.structuredconcurrency.demo1_invoice.model.Order;
import eu.happycoders.structuredconcurrency.demo1_invoice.service.CustomerService;
import eu.happycoders.structuredconcurrency.demo1_invoice.service.InvoiceTemplateService;
import eu.happycoders.structuredconcurrency.demo1_invoice.service.OrderService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.StructuredTaskScope.Subtask;

public class InvoiceGenerator6_ShutdownOnFailure {

  public static void main(String[] args) throws InterruptedException, ExecutionException {
    InvoiceGenerator6_ShutdownOnFailure invoiceGenerator =
        new InvoiceGenerator6_ShutdownOnFailure(
            new OrderService(), new CustomerService(), new InvoiceTemplateService());
    invoiceGenerator.createInvoice(10012, 61157, "en");
  }

  private final OrderService orderService;
  private final CustomerService customerService;
  private final InvoiceTemplateService invoiceTemplateService;

  public InvoiceGenerator6_ShutdownOnFailure(
      OrderService orderService,
      CustomerService customerService,
      InvoiceTemplateService invoiceTemplateService) {
    this.orderService = orderService;
    this.customerService = customerService;
    this.invoiceTemplateService = invoiceTemplateService;
  }

  Invoice createInvoice(int orderId, int customerId, String language)
      throws InterruptedException, ExecutionException {
    try (StructuredTaskScope.ShutdownOnFailure scope =
        new StructuredTaskScope.ShutdownOnFailure()) {
      log("Forking tasks");

      Subtask<Order> orderSubtask = scope.fork(() -> orderService.getOrder(orderId));
      Subtask<Customer> customerSubtask = scope.fork(() -> customerService.getCustomer(customerId));
      Subtask<InvoiceTemplate> invoiceTemplateSubtask =
          scope.fork(() -> invoiceTemplateService.getTemplate(language));

      log("Waiting for all tasks to finish");
      scope.join();
      scope.throwIfFailed();

      log("Retrieving results");
      Order order = orderSubtask.get();
      Customer customer = customerSubtask.get();
      InvoiceTemplate template = invoiceTemplateSubtask.get();

      log("Generating and returning invoice");
      return Invoice.generate(order, customer, template);
    }
  }
}
