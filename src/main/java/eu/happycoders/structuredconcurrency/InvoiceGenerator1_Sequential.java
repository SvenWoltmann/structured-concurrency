package eu.happycoders.structuredconcurrency;

import eu.happycoders.structuredconcurrency.model.Customer;
import eu.happycoders.structuredconcurrency.model.Invoice;
import eu.happycoders.structuredconcurrency.model.InvoiceTemplate;
import eu.happycoders.structuredconcurrency.model.Order;
import eu.happycoders.structuredconcurrency.service.CustomerService;
import eu.happycoders.structuredconcurrency.service.InvoiceTemplateService;
import eu.happycoders.structuredconcurrency.service.OrderService;

public class InvoiceGenerator1_Sequential {

  public static void main(String[] args) throws InterruptedException {
    InvoiceGenerator1_Sequential invoiceGenerator =
        new InvoiceGenerator1_Sequential(
            new OrderService(), new CustomerService(), new InvoiceTemplateService());
    invoiceGenerator.createInvoice(10012, 61157, "en");
  }

  private final OrderService orderService;
  private final CustomerService customerService;
  private final InvoiceTemplateService invoiceTemplateService;

  public InvoiceGenerator1_Sequential(
      OrderService orderService,
      CustomerService customerService,
      InvoiceTemplateService invoiceTemplateService) {
    this.orderService = orderService;
    this.customerService = customerService;
    this.invoiceTemplateService = invoiceTemplateService;
  }

  Invoice createInvoice(int orderId, int customerId, String language) throws InterruptedException {
    Order order = orderService.getOrder(orderId);

    Customer customer = customerService.getCustomer(customerId);

    InvoiceTemplate template = invoiceTemplateService.getTemplate(language);

    return Invoice.generate(order, customer, template);
  }
}
