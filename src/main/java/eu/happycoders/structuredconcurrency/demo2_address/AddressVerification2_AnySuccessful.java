package eu.happycoders.structuredconcurrency.demo2_address;

import static eu.happycoders.structuredconcurrency.util.SimpleLogger.log;

import eu.happycoders.structuredconcurrency.demo2_address.model.Address;
import eu.happycoders.structuredconcurrency.demo2_address.model.AddressVerificationResponse;
import eu.happycoders.structuredconcurrency.demo2_address.service.AddressVerificationService;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.StructuredTaskScope.Joiner;

public class AddressVerification2_AnySuccessful {

  static void main() throws InterruptedException {
    AddressVerification2_AnySuccessful addressVerification =
        new AddressVerification2_AnySuccessful(new AddressVerificationService());
    AddressVerificationResponse response =
        addressVerification.verifyAddress(
            new Address("1600 Pennsylvania Avenue, N.W.", null, "Washington", "DC", "20500", "US"));
    log("Response: " + response);
  }

  private final AddressVerificationService verificationService;

  public AddressVerification2_AnySuccessful(AddressVerificationService verificationService) {
    this.verificationService = verificationService;
  }

  AddressVerificationResponse verifyAddress(Address address) throws InterruptedException {
    try (var scope =
        StructuredTaskScope.open(
            Joiner.<AddressVerificationResponse>anySuccessfulOrThrow())) {
      log("Forking tasks");

      scope.fork(() -> verificationService.verifyViaServiceA(address));
      scope.fork(() -> verificationService.verifyViaServiceB(address));
      scope.fork(() -> verificationService.verifyViaServiceC(address));

      log("Waiting for one task to finish");

      return scope.join();
    }
  }
}
