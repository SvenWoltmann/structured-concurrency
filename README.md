# Structure Concurrency Examples

This repository contains several examples for "Structured Concurrency",
a feature described in [JEP 453](https://openjdk.org/jeps/453)
and available in [Java 21](https://www.happycoders.eu/java/java-21-features/) 
as a preview feature.

I use these examples in my presentations on structured concurrency.

## <br>Java 21 IDE Support

[IntelliJ IDEA](https://www.jetbrains.com/idea/) supports Java 21 as of version 2023.2.2.
As long as this version is not yet released,
you can install the release candidate via the [JetBrains Toolbox App](https://www.jetbrains.com/toolbox-app/)
by going into the IntelliJ IDEA settings and activating the "Early Access Program".


## <br>Compile and Run the Demos From the Command Line

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
java -cp target/classes --enable-preview eu.happycoders.structuredconcurrency/demo3_suppliers/SupplierDeliveryTimeCheck4_NestedStructuredTaskScopeUsingScopedValue
```

## <br>Java Downloads

You can download Java 21 from here: https://jdk.java.net/21/

To install multiple Java versions on Linux or macOS, I recommend using [SDKMAN!](https://sdkman.io/)

To install multiple Java versions on Windows,
have a look at this tutorial: [How to Change Java Versions in Windows](https://www.happycoders.eu/java/how-to-switch-multiple-java-versions-windows/)


## <br>Other Java 21 Examples

You might also find these GitHub repositories interesting:

Virtual Threads:
* https://github.com/SvenWoltmann/virtual-threads
* https://github.com/SvenWoltmann/virtual-threads-quarkus
* https://github.com/SvenWoltmann/virtual-threads-spring

Scoped Values:
* https://github.com/SvenWoltmann/scoped-values

Pattern Matching for Switch:
* https://github.com/SvenWoltmann/pattern-matching-for-switch

## <br>Other Resources

### Java Versions PDF Cheat Sheet

**Stay up-to-date** with the latest Java features with [this PDF Cheat Sheet](https://www.happycoders.eu/java-versions/)!

[<img src="/img/Java_Versions_PDF_Cheat_Sheet_Mockup_936.v2.png" alt="Java Versions PDF Cheat Sheet Mockup" style="width: 468px; max-width: 100%;">](https://www.happycoders.eu/java-versions/)

* Avoid lengthy research with this **concise overview of all Java versions up to Java 23**.
* **Discover the innovative features** of each new Java version, summarized on a single page.
* **Impress your team** with your up-to-date knowledge of the latest Java version.

👉 [Download the Java Versions PDF](https://www.happycoders.eu/java-versions/)<br>

_(Hier geht's zur deutschen Version &rarr; [Java-Versionen PDF](https://www.happycoders.eu/de/java-versionen/))_


### <br>The Big O Cheat Sheet

With this [1-page PDF cheat sheet](https://www.happycoders.eu/big-o-cheat-sheet/), you'll always have the **7 most important complexity classes** at a glance.

[<img src="/img/big-o-cheat-sheet-pdf-en-transp_936.v2.png" alt="Big O PDF Cheat Sheet Mockup" style="width: 468px; max-width: 100%;">](https://www.happycoders.eu/big-o-cheat-sheet/)

* **Always choose the most efficient data structures** and thus increase the performance of your applications.
* **Be prepared for technical interviews** and confidently present your algorithm knowledge.
* **Become a sought-after problem solver** and be known for systematically tackling complex problems.

👉 [Download the Big O Cheat Sheet](https://www.happycoders.eu/big-o-cheat-sheet/)<br>

_(Hier geht's zur deutschen Version &rarr; [O-Notation Cheat Sheet](https://www.happycoders.eu/de/o-notation-cheat-sheet/))_


### <br>HappyCoders Newsletter
👉 Want to level up your Java skills?
Sign up for the [HappyCoders newsletter](http://www.happycoders.eu/newsletter/) and get regular tips on programming, algorithms, and data structures!

_(Hier geht's zur deutschen Version &rarr; [HappyCoders-Newsletter deutsch](https://www.happycoders.eu/de/newsletter/))_


### <br>🇩🇪 An alle Java-Programmierer, die durch fundierte Kenntnisse über Datenstrukturen besseren Code schreiben wollen

Trage dich jetzt auf die [Warteliste](https://www.happycoders.eu/de/mastering-data-structures-warteliste/) von „Mastering Data Structures in Java“ ein, und erhalte das beste Angebot!

[<img src="/img/mastering-data-structures-product-mockup-cropped-1600.v2.png" alt="Mastering Data Structures Mockup" style="width: 640px; max-width: 100%;">](https://www.happycoders.eu/de/mastering-data-structures-warteliste/)

👉 [Zur Warteliste](https://www.happycoders.eu/de/mastering-data-structures-warteliste/)

