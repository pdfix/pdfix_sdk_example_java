# PDFix SDK example Java Maven
Example project demonstrating the capabilities of PDFix SDK written in Java.

## Description

For more information please visit [https://pdfix.net](https://pdfix.net).

## Setup

The example needs the PDFix SDK Java library `net.pdfix.pdfixlib-<version>.jar` in the `lib/` folder.
The jar is not part of this repository and is not published to Maven Central.
It already contains the native libraries for all supported platforms.

1. Check the SDK version in `pom.xml` (`<pdfixlib.version>`).
2. Open the matching release on [GitHub Releases](https://github.com/pdfix/pdfix_sdk_builds/releases)
   (e.g. `v9.3.0`) and download the asset `java-net.pdfix.pdfixlib-<version>_<build>.jar.zip`.
3. Unzip it and copy `net.pdfix.pdfixlib-<version>.jar` into `lib/`:

```
mkdir -p lib
unzip java-net.pdfix.pdfixlib-9.3.0_*.jar.zip net.pdfix.pdfixlib-9.3.0.jar -d lib
```

`mvn clean` installs the jar from `lib/` into the local Maven repository (`maven-install-plugin`),
so run it before the first build and after every SDK version change.

## Compile App

```
mvn clean
mvn compile
```

## Run the sample

```
mvn exec:java -Dexec.mainClass="net.pdfix.App"
```

To run a single test, pass its name, for example AddTags or AddWatermark. All tests are executed if empty:
```
mvn exec:java -Dexec.mainClass="net.pdfix.App" -Dexec.args="--test AddTags"
```

## Have a question? Need help?
Let us know and we’ll get back to you. Write us to support@pdfix.net or fill the [contact form](https://pdfix.net/support/).