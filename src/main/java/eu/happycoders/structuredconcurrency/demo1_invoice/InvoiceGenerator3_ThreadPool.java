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

public class InvoiceGenerator3_ThreadPool {

  public static void main(String[] args) throws InterruptedException, ExecutionException {
    InvoiceGenerator3_ThreadPool invoiceGenerator =
        new InvoiceGenerator3_ThreadPool(
            new OrderService(), new CustomerService(), new InvoiceTemplateService());
    invoiceGenerator.createInvoice(10012, 61157, "en");
  }

  private final OrderService orderService;
  private final CustomerService customerService;
  private final InvoiceTemplateService invoiceTemplateService;

  private final ExecutorService executor = Executors.newCachedThreadPool();

  public InvoiceGenerator3_ThreadPool(
      OrderService orderService,
      CustomerService customerService,
      InvoiceTemplateService invoiceTemplateService) {
    this.orderService = orderService;
    this.customerService = customerService;
    this.invoiceTemplateService = invoiceTemplateService;
  }

  Invoice createInvoice(int orderId, int customerId, String language)
      throws InterruptedException, ExecutionException {
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
