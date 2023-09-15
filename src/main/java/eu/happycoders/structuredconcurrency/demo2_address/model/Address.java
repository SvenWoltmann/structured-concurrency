package eu.happycoders.structuredconcurrency.demo2_address.model;

public record Address(
    String addressLine1,
    String addressLine2,
    String city,
    String state,
    String postalCode,
    String countryCode) {}
