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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class InvoiceGenerator4_NewVirtualThreadPerTask {

  static void main() throws InterruptedException, ExecutionException {
    InvoiceGenerator4_NewVirtualThreadPerTask invoiceGenerator =
        new InvoiceGenerator4_NewVirtualThreadPerTask(
            new OrderService(), new CustomerService(), new InvoiceTemplateService());
    invoiceGenerator.createInvoice(10012, 61157, "en");
  }

  private final OrderService orderService;
  private final CustomerService customerService;
  private final InvoiceTemplateService invoiceTemplateService;

  public InvoiceGenerator4_NewVirtualThreadPerTask(
      OrderService orderService,
      CustomerService customerService,
      InvoiceTemplateService invoiceTemplateService) {
    this.orderService = orderService;
    this.customerService = customerService;
    this.invoiceTemplateService = invoiceTemplateService;
  }

  Invoice createInvoice(int orderId, int customerId, String language)
      throws InterruptedException, ExecutionException {
    try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
      log("Submitting tasks");

      Future<Order> orderFuture = executor.submit(() -> orderService.getOrder(orderId));
      Future<Customer> customerFuture =
          executor.submit(() -> customerService.getCustomer(customerId));
      Future<InvoiceTemplate> invoiceTemplateFuture =
          executor.submit(() -> invoiceTemplateService.getTemplate(language));

      log("Waiting for order");
      Order order = orderFuture.get();

      log("Waiting for customer");
      Customer customer = customerFuture.get();

      log("Waiting for template");
      InvoiceTemplate invoiceTemplate = invoiceTemplateFuture.get();

      log("Generating and returning invoice");
      return Invoice.generate(order, customer, invoiceTemplate);
    }
  }
}
