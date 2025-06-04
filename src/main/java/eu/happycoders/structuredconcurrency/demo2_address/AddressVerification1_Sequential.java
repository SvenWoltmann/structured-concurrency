package eu.happycoders.structuredconcurrency.demo2_address;

import static eu.happycoders.structuredconcurrency.util.SimpleLogger.log;

import eu.happycoders.structuredconcurrency.demo2_address.model.Address;
import eu.happycoders.structuredconcurrency.demo2_address.model.AddressVerificationResponse;
import eu.happycoders.structuredconcurrency.demo2_address.service.AddressVerificationService;

public class AddressVerification1_Sequential {

  static void main() throws InterruptedException {
    AddressVerification1_Sequential addressVerification =
        new AddressVerification1_Sequential(new AddressVerificationService());
    AddressVerificationResponse response =
        addressVerification.verifyAddress(
            new Address("1600 Pennsylvania Avenue, N.W.", null, "Washington", "DC", "20500", "US"));
    log("Response: " + response);
  }

  private final AddressVerificationService verificationService;

  public AddressVerification1_Sequential(AddressVerificationService verificationService) {
    this.verificationService = verificationService;
  }

  AddressVerificationResponse verifyAddress(Address address) throws InterruptedException {
    try {
      return verificationService.verifyViaServiceA(address);
    } catch (Exception e) {
      log("Verification via service A failed: " + e.getMessage());
    }

    try {
      return verificationService.verifyViaServiceB(address);
    } catch (Exception e) {
      log("Verification via service B failed: " + e.getMessage());
    }

    try {
      return verificationService.verifyViaServiceC(address);
    } catch (Exception e) {
      log("Verification via service C failed: " + e.getMessage());
      throw e;
    }
  }
}
