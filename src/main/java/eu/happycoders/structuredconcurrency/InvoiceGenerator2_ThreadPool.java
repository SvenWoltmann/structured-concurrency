package eu.happycoders.structuredconcurrency;

import static eu.happycoders.structuredconcurrency.util.SimpleLogger.log;

import eu.happycoders.structuredconcurrency.model.Customer;
import eu.happycoders.structuredconcurrency.model.Invoice;
import eu.happycoders.structuredconcurrency.model.InvoiceTemplate;
import eu.happycoders.structuredconcurrency.model.Order;
import eu.happycoders.structuredconcurrency.service.CustomerService;
import eu.happycoders.structuredconcurrency.service.InvoiceTemplateService;
import eu.happycoders.structuredconcurrency.service.OrderService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class InvoiceGenerator2_ThreadPool {

  public static void main(String[] args) throws InterruptedException, ExecutionException {
    InvoiceGenerator2_ThreadPool invoiceGenerator =
        new InvoiceGenerator2_ThreadPool(
            new OrderService(), new CustomerService(), new InvoiceTemplateService());
    invoiceGenerator.createInvoice(10012, 61157, "en");
  }

  private final OrderService orderService;
  private final CustomerService customerService;
  private final InvoiceTemplateService invoiceTemplateService;

  private final ExecutorService executor = Executors.newCachedThreadPool();

  public InvoiceGenerator2_ThreadPool(
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
