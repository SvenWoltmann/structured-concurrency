package eu.happycoders.structuredconcurrency.demo1_invoice;

import eu.happycoders.structuredconcurrency.demo1_invoice.model.Customer;
import eu.happycoders.structuredconcurrency.demo1_invoice.model.Invoice;
import eu.happycoders.structuredconcurrency.demo1_invoice.model.InvoiceTemplate;
import eu.happycoders.structuredconcurrency.demo1_invoice.model.Order;
import eu.happycoders.structuredconcurrency.demo1_invoice.service.CustomerService;
import eu.happycoders.structuredconcurrency.demo1_invoice.service.InvoiceTemplateService;
import eu.happycoders.structuredconcurrency.demo1_invoice.service.OrderService;
import java.util.concurrent.CompletableFuture;

public class InvoiceGenerator2_CompletableFuture {

  static void main() {
    InvoiceGenerator2_CompletableFuture invoiceGenerator =
        new InvoiceGenerator2_CompletableFuture(
            new OrderService(), new CustomerService(), new InvoiceTemplateService());
    invoiceGenerator.createInvoiceAsync(10012, 61157, "en").join();
  }

  private final OrderService orderService;
  private final CustomerService customerService;
  private final InvoiceTemplateService invoiceTemplateService;

  public InvoiceGenerator2_CompletableFuture(
      OrderService orderService,
      CustomerService customerService,
      InvoiceTemplateService invoiceTemplateService) {
    this.orderService = orderService;
    this.customerService = customerService;
    this.invoiceTemplateService = invoiceTemplateService;
  }

  CompletableFuture<Invoice> createInvoiceAsync(int orderId, int customerId, String language) {
    CompletableFuture<Order> orderFuture = orderService.getOrderAsync(orderId);
    CompletableFuture<Customer> customerFuture = customerService.getCustomerAsync(customerId);
    CompletableFuture<InvoiceTemplate> templateFuture =
        invoiceTemplateService.getTemplateAsync(language);

    return CompletableFuture.allOf(orderFuture, customerFuture, templateFuture)
        .thenCompose(
            _ -> {
              Order order = orderFuture.resultNow();
              Customer customer = customerFuture.resultNow();
              InvoiceTemplate template = templateFuture.resultNow();
              Invoice invoice = Invoice.generate(order, customer, template);

              return CompletableFuture.completedFuture(invoice);
            });
  }
}
