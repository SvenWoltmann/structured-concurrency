package eu.happycoders.structuredconcurrency.demo2_address;

import static eu.happycoders.structuredconcurrency.util.SimpleLogger.log;

import eu.happycoders.structuredconcurrency.demo2_address.model.Address;
import eu.happycoders.structuredconcurrency.demo2_address.model.AddressVerificationResponse;
import eu.happycoders.structuredconcurrency.demo2_address.service.AddressVerificationService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.StructuredTaskScope.ShutdownOnSuccess;

public class AddressVerification2_ShutdownOnSuccess {

  public static void main(String[] args) throws InterruptedException, ExecutionException {
    AddressVerification2_ShutdownOnSuccess addressVerification =
        new AddressVerification2_ShutdownOnSuccess(new AddressVerificationService());
    AddressVerificationResponse response =
        addressVerification.verifyAddress(
            new Address("1600 Pennsylvania Avenue, N.W.", null, "Washington", "DC", "20500", "US"));
    log("Response: " + response);
  }

  private final AddressVerificationService verificationService;

  public AddressVerification2_ShutdownOnSuccess(AddressVerificationService verificationService) {
    this.verificationService = verificationService;
  }

  AddressVerificationResponse verifyAddress(Address address)
      throws InterruptedException, ExecutionException {
    try (ShutdownOnSuccess<AddressVerificationResponse> scope = new ShutdownOnSuccess<>()) {
      log("Forking tasks");

      scope.fork(() -> verificationService.verifyViaServiceA(address));
      scope.fork(() -> verificationService.verifyViaServiceB(address));
      scope.fork(() -> verificationService.verifyViaServiceC(address));

      log("Waiting for one task to finish");

      scope.join();

      log("Retrieving result");

      return scope.result();
    }
  }
}
