package eu.happycoders.structuredconcurrency;

import static eu.happycoders.structuredconcurrency.util.SimpleLogger.log;

import eu.happycoders.structuredconcurrency.model.Customer;
import eu.happycoders.structuredconcurrency.model.Invoice;
import eu.happycoders.structuredconcurrency.model.InvoiceTemplate;
import eu.happycoders.structuredconcurrency.model.Order;
import eu.happycoders.structuredconcurrency.service.CustomerService;
import eu.happycoders.structuredconcurrency.service.InvoiceTemplateService;
import eu.happycoders.structuredconcurrency.service.OrderService;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.StructuredTaskScope.Subtask;

public class InvoiceGenerator4_StructuredTaskScope {

  public static void main(String[] args) throws InterruptedException {
    InvoiceGenerator4_StructuredTaskScope invoiceGenerator =
        new InvoiceGenerator4_StructuredTaskScope(
            new OrderService(), new CustomerService(), new InvoiceTemplateService());
    invoiceGenerator.createInvoice(10012, 61157, "en");
  }

  private final OrderService orderService;
  private final CustomerService customerService;
  private final InvoiceTemplateService invoiceTemplateService;

  public InvoiceGenerator4_StructuredTaskScope(
      OrderService orderService,
      CustomerService customerService,
      InvoiceTemplateService invoiceTemplateService) {
    this.orderService = orderService;
    this.customerService = customerService;
    this.invoiceTemplateService = invoiceTemplateService;
  }

  Invoice createInvoice(int orderId, int customerId, String language) throws InterruptedException {
    try (StructuredTaskScope<Object> scope = new StructuredTaskScope<>()) {
      log("Forking tasks");

      Subtask<Order> orderSubtask = scope.fork(() -> orderService.getOrder(orderId));
      Subtask<Customer> customerSubtask = scope.fork(() -> customerService.getCustomer(customerId));
      Subtask<InvoiceTemplate> invoiceTemplateSubtask =
          scope.fork(() -> invoiceTemplateService.getTemplate(language));

      log("Waiting for all tasks to finish");
      scope.join();

      log("Retrieving results");
      Order order = orderSubtask.get();
      Customer customer = customerSubtask.get();
      InvoiceTemplate template = invoiceTemplateSubtask.get();

      log("Generating and returning invoice");
      return Invoice.generate(order, customer, template);
    }
  }
}
