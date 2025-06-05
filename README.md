# Structure Concurrency Examples

This repository contains a collection of examples demonstrating _Structured Concurrency_, a feature introduced as a preview in [Java 21](https://www.happycoders.eu/java/java-21-features/), with major enhancements in [Java 25](https://www.happycoders.eu/java/java-25-features/).

A comprehensive explanation of Structured Concurrency is available in this article:\
👉 [Structured Concurrency in Java](https://www.happycoders.eu/java/structured-concurrency-structuredtaskscope/)

These examples are also used in my presentations, which is why not all of the code is directly referenced in the article.


## Java 21 vs. Java 25

As mentioned above, *Structured Concurrency* underwent significant changes in Java 25.

- The `main` branch contains the updated examples for Java 25.
- The last commit compatible with Java 21–24 is available under the tag [`java-21`](https://github.com/SvenWoltmann/structured-concurrency/tree/java-21).

To check out the Java 21 version after cloning the repository, run:

```bash
git checkout java-21
```

## Compile and Run from the Command Line

To compile the examples (replace `25` with your Java version):

Linux/macOS (using '/' to break up lines):

```bash
javac --enable-preview --source 25 -d target/classes \
  src/main/java/eu/happycoders/structuredconcurrency/util/*.java \
  src/main/java/eu/happycoders/structuredconcurrency/demo1_invoice/model/*.java \
  src/main/java/eu/happycoders/structuredconcurrency/demo1_invoice/service/*.java \
  src/main/java/eu/happycoders/structuredconcurrency/demo1_invoice/*.java \
  src/main/java/eu/happycoders/structuredconcurrency/demo2_address/model/*.java \
  src/main/java/eu/happycoders/structuredconcurrency/demo2_address/service/*.java \
  src/main/java/eu/happycoders/structuredconcurrency/demo2_address/*.java \
  src/main/java/eu/happycoders/structuredconcurrency/demo3_suppliers/model/*.java \
  src/main/java/eu/happycoders/structuredconcurrency/demo3_suppliers/service/*.java \
  src/main/java/eu/happycoders/structuredconcurrency/demo3_suppliers/*.java
```

Windows (using '^' to break up lines):

```bash
javac --enable-preview --source 25 -d target/classes ^
  src/main/java/eu/happycoders/structuredconcurrency/util/*.java ^
  src/main/java/eu/happycoders/structuredconcurrency/demo1_invoice/model/*.java ^
  src/main/java/eu/happycoders/structuredconcurrency/demo1_invoice/service/*.java ^
  src/main/java/eu/happycoders/structuredconcurrency/demo1_invoice/*.java ^
  src/main/java/eu/happycoders/structuredconcurrency/demo2_address/model/*.java ^
  src/main/java/eu/happycoders/structuredconcurrency/demo2_address/service/*.java ^
  src/main/java/eu/happycoders/structuredconcurrency/demo2_address/*.java ^
  src/main/java/eu/happycoders/structuredconcurrency/demo3_suppliers/model/*.java ^
  src/main/java/eu/happycoders/structuredconcurrency/demo3_suppliers/service/*.java ^
  src/main/java/eu/happycoders/structuredconcurrency/demo3_suppliers/*.java
```

Run the demos as follows:

```bash
java -cp target/classes eu.happycoders.structuredconcurrency/demo1_invoice/InvoiceGenerator3_ThreadPool
java -cp target/classes --enable-preview eu.happycoders.structuredconcurrency/demo1_invoice/InvoiceGenerator5_StructuredTaskScope
java -cp target/classes --enable-preview eu.happycoders.structuredconcurrency/demo2_address/AddressVerification2_AnySuccessfulResult
java -cp target/classes --enable-preview eu.happycoders.structuredconcurrency/demo3_suppliers/SupplierDeliveryTimeCheck2_StructuredTaskScope
java -cp target/classes --enable-preview eu.happycoders.structuredconcurrency/demo3_suppliers/SupplierDeliveryTimeCheck3_NestedStructuredTaskScope
java -cp target/classes --enable-preview eu.happycoders.structuredconcurrency/demo3_suppliers/SupplierDeliveryTimeCheck4_NestedStructuredTaskScopeUsingScopedValue
```

## Java Downloads

Java 25 Early Access: https://jdk.java.net/25/

Java 24: https://jdk.java.net/24/

Archive of older versions: https://jdk.java.net/archive/


### Java Version Management

- **Linux/macOS:** Use [SDKMAN!](https://sdkman.io/) to manage multiple Java versions.

- **Windows:** Follow [this guide](https://www.happycoders.eu/java/how-to-switch-multiple-java-versions-windows/) to switch between Java versions.


## Related Repositories (Project Loom)

Explore more Loom-related code:

**Virtual Threads:**

* https://github.com/SvenWoltmann/virtual-threads
* https://github.com/SvenWoltmann/virtual-threads-quarkus
* https://github.com/SvenWoltmann/virtual-threads-spring

**Scoped Values:**

* https://github.com/SvenWoltmann/scoped-values


## Other Resources

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

