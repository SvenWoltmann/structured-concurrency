package eu.happycoders.structuredconcurrency.demo2_address.service;

import static eu.happycoders.structuredconcurrency.util.SimpleLogger.log;

import eu.happycoders.structuredconcurrency.demo2_address.model.Address;
import eu.happycoders.structuredconcurrency.demo2_address.model.AddressVerificationResponse;
import eu.happycoders.structuredconcurrency.service.Properties;
import java.util.concurrent.ThreadLocalRandom;

public class AddressVerificationService {

  public AddressVerificationResponse verifyViaServiceA(Address address)
      throws InterruptedException {
    return verifyViaService(address, "A");
  }

  public AddressVerificationResponse verifyViaServiceB(Address address)
      throws InterruptedException {
    return verifyViaService(address, "B");
  }

  public AddressVerificationResponse verifyViaServiceC(Address address)
      throws InterruptedException {
    return verifyViaService(address, "C");
  }

  private AddressVerificationResponse verifyViaService(Address address, String service)
      throws InterruptedException {
    log("Verifying address via service " + service);
    try {
      // In presentation mode, we want the services to return in a predictable order
      long minSleepTime =
          Properties.PRESENTATION_MODE
              ? (switch (service) {
                case "A" -> 900;
                case "B" -> 300;
                case "C" -> 600;
                default -> throw new IllegalStateException();
              })
              : 500;
      long maxSleepTime = Properties.PRESENTATION_MODE ? minSleepTime + 100 : 1000;
      Thread.sleep(ThreadLocalRandom.current().nextLong(minSleepTime, maxSleepTime));
    } catch (InterruptedException e) {
      log("Verifying address via service " + service + " interrupted");
      throw e;
    }

    // In presentation mode, we either want that
    // a) all of them fail, so we get an error
    // b) none fails, so we get the first's (B) response, and we cancel the two others
    // c) the first one (B) fails, so we get the second's (C) response, and we cancel the third (A)
    double errorProbability =
        Properties.PRESENTATION_MODE ? (service.equals("B") ? 0.5 : 1.0) : 0.75;
    if (ThreadLocalRandom.current().nextDouble() < errorProbability) {
      log("Error loading address via service " + service);
      throw new RuntimeException("Error loading address via service " + service);
    }

    log("Finished loading address via service " + service);
    return new AddressVerificationResponse("Verification response from service " + service);
  }
}
