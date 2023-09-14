package eu.happycoders.structuredconcurrency.model;

public record Invoice() {
  public static Invoice generate(Order order, Customer customer, InvoiceTemplate template) {
    return new Invoice();
  }
}
