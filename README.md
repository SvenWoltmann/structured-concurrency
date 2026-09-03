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


<!-- happycoders-resources:start -->
<!-- Generated from happycoders-website-astro/data/github-readme by scripts/content/sync-github-readmes.mjs. Edit there, not here. -->

## <br>Additional Resources

### <br>Java Versions PDF Cheat Sheet

**Stay up-to-date** with the latest Java features with this **free** [PDF Cheat Sheet](https://www.happycoders.eu/java-versions/)!

[<img src="/img/java-versions-cheat-sheet-mockup.png" alt="Java Versions PDF Cheat Sheet Mockup" width="468">](https://www.happycoders.eu/java-versions/)

* Avoid lengthy research with this **concise overview of all Java versions from Java 10 to Java 26**.
* **Discover the innovative features** of each new Java version, summarized on a single page.
* **Impress your team** with your up-to-date knowledge of the latest Java version.

👉 [Download the free Java Versions PDF](https://www.happycoders.eu/java-versions/)<br>

_(Hier geht's zur deutschen Version &rarr; [Java-Versionen PDF](https://www.happycoders.eu/de/java-versionen/))_


### <br>The Big O Cheat Sheet

With this **free** [1-page PDF cheat sheet](https://www.happycoders.eu/big-o-cheat-sheet/), you'll always have the **7 most important complexity classes** at a glance.

[<img src="/img/big-o-cheat-sheet-mockup.png" alt="Big O PDF Cheat Sheet Mockup" width="304">](https://www.happycoders.eu/big-o-cheat-sheet/)

* **Always choose the most efficient data structures** and thus increase the performance of your applications.
* **Be prepared for technical interviews** and confidently present your algorithm knowledge.
* **Become a sought-after problem solver** and be known for systematically tackling complex problems.

👉 [Download the free Big O Cheat Sheet](https://www.happycoders.eu/big-o-cheat-sheet/)<br>

_(Hier geht's zur deutschen Version &rarr; [O-Notation Cheat Sheet](https://www.happycoders.eu/de/o-notation-cheat-sheet/))_


### <br>HappyCoders Newsletter
👉 Want to stay on top of modern Java?
Sign up for the [HappyCoders newsletter](https://www.happycoders.eu/newsletter/) – Modern Java: new versions & features, performance, and JVM insights – once a month.

_(Hier geht's zur deutschen Version &rarr; [HappyCoders-Newsletter deutsch](https://www.happycoders.eu/de/newsletter/))_


### <br>🇩🇪 An alle Java-Entwickler:innen, die durch fundierte Kenntnisse über Datenstrukturen besseren Code schreiben wollen

Trage dich jetzt unverbindlich auf die [Warteliste](https://www.happycoders.eu/de/mastering-data-structures-warteliste/) von „Mastering Data Structures in Java“ ein, und erhalte das beste Angebot!

[<img src="/img/mastering-data-structures-product-mockup.png" alt="Mastering Data Structures Mockup" width="640">](https://www.happycoders.eu/de/mastering-data-structures-warteliste/)

👉 [Zur Warteliste](https://www.happycoders.eu/de/mastering-data-structures-warteliste/)
<!-- happycoders-resources:end -->
