# PDFix SDK example Java Maven
Example project demonstrating the capabilities of PDFix SDK written in Java.

## Description

For more information please visit [https://pdfix.net](https://pdfix.net).

## Setup

Download `net.pdfix.pdfixlib.jar` and copy to `libs/`

All resources are available on https://pdfix.net/download.

## Compile App

```
mvn compile -f pom.xml
mvn package -f pom.xml
```

## Run the sample

Make sure to copy dependencies from `{zip}/include/java` to `lib/` folder next to net.pdfix.App-8.0.1.jar.

Run example_run.sh or use command:
```
java -jar target/net.pdfix.App-8.0.1.jar --test <test_name>
```
- `--test` -  [optional] the name of the test to run. For example AddTags, AddWatermark. All tests are executed if empty

## Manual Setup

Download PDFix SDK JAR file from https://pdfix.net/download

__Copy class libraries__ from the `{zip}/include/java` into ${project.basedir}/lib/ so that you see there

- net.pdfix.pdfixlib-${sdk_verison_num}.jar

## Have a question? Need help?
Let us know and we’ll get back to you. Write us to support@pdfix.net or fill the [contact form](https://pdfix.net/support/).