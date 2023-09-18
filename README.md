# Structure Concurrency Examples

This repository contains several examples for "Structured Concurrency",
a feature described in [JEP 453](https://openjdk.org/jeps/453)
and available in [Java 21](https://www.happycoders.eu/java/java-21-features/) 
as a preview feature.

I use these examples in my presentations on structured concurrency.

## Java 21 IDE Support

[IntelliJ IDEA](https://www.jetbrains.com/idea/) supports Java 21 as of version 2023.2.2.
As long as this version is not yet released,
you can install the release candidate via the [JetBrains Toolbox App](https://www.jetbrains.com/toolbox-app/)
by going into the IntelliJ IDEA settings and activating the "Early Access Program".


## Compile and Run the Demos From the Command Line

You can also compile the application manually like this:

```
javac --enable-preview --source 21 -d target/classes src/main/java/eu/happycoders/structuredconcurrency/util/*.java src/main/java/eu/happycoders/structuredconcurrency/demo1_invoice/model/*.java src/main/java/eu/happycoders/structuredconcurrency/demo1_invoice/service/*.java src/main/java/eu/happycoders/structuredconcurrency/demo1_invoice/*.java src/main/java/eu/happycoders/structuredconcurrency/demo2_address/model/*.java src/main/java/eu/happycoders/structuredconcurrency/demo2_address/service/*.java src/main/java/eu/happycoders/structuredconcurrency/demo2_address/*.java src/main/java/eu/happycoders/structuredconcurrency/demo3_suppliers/model/*.java src/main/java/eu/happycoders/structuredconcurrency/demo3_suppliers/service/*.java src/main/java/eu/happycoders/structuredconcurrency/demo3_suppliers/*.java
```

And then run the three demos like this:

```
java -cp target/classes eu.happycoders.structuredconcurrency/demo1_invoice/InvoiceGenerator3_ThreadPool
java -cp target/classes --enable-preview eu.happycoders.structuredconcurrency/demo1_invoice/InvoiceGenerator5_StructuredTaskScope
java -cp target/classes --enable-preview eu.happycoders.structuredconcurrency/demo1_invoice/InvoiceGenerator6_ShutdownOnFailure
java -cp target/classes --enable-preview eu.happycoders.structuredconcurrency/demo2_address/AddressVerification2_ShutdownOnSuccess
java -cp target/classes --enable-preview eu.happycoders.structuredconcurrency/demo3_suppliers/SupplierDeliveryTimeCheck2_StructuredTaskScope
java -cp target/classes --enable-preview eu.happycoders.structuredconcurrency/demo3_suppliers/SupplierDeliveryTimeCheck3_NestedStructuredTaskScope
```

## Java Downloads

You can download Java 21 from here: https://jdk.java.net/21/

To install multiple Java versions on Linux or macOS, I recommend using [SDKMAN!](https://sdkman.io/)

To install multiple Java versions on Windows,
have a look at this tutorial: [How to Change Java Versions in Windows](https://www.happycoders.eu/java/how-to-switch-multiple-java-versions-windows/)


## Other Java 21 Examples

You might also find these GitHub repositories interesting:

Virtual Threads:
* https://github.com/SvenWoltmann/virtual-threads
* https://github.com/SvenWoltmann/virtual-threads-quarkus
* https://github.com/SvenWoltmann/virtual-threads-spring

Scoped Values:
* https://github.com/SvenWoltmann/scoped-values

Pattern Matching for Switch:
* https://github.com/SvenWoltmann/pattern-matching-for-switch
