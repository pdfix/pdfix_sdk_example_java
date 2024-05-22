# PDFix SDK example Java Maven
Example project demonstrating the capabilities of PDFix SDK written in Java.

## Description

For more information please visit [https://pdfix.net](https://pdfix.net).

## Quick Setup

Run setup_maven.sh to download and install all dependencies, then execute main class net.pdfix.App.
```
./setup_maven.sh
```

## Compile App

```
mvn compile -f pom.xml
mvn package -f pom.xml
```

## Run the sample

Make sure to copy dependencies from `{zip}/include/java` to `lib/` folder next to net.pdfix.App-8.0.1.jar.

Run example_run.sh or use command:
```
java -jar target/net.pdfix.App-8.0.1.jar --pdfix-bin <your_path>/pdfix/bin --test <test_name>
```
- `--pdfix-bin` - [optional] path to pdfix bin folder if stored on custom location
- `--test` -  [optional] the name of the test to run. For example AddTags, AddWatermark. All tests are executed if empty

## Manual Setup

Download PDFix SDK from https://pdfix.net/download

__Copy class libraries__ from the `{zip}/include/java` into ${project.basedir}/pdfix/include/java/ so that you see there

- net.pdfix.pdfixlib-${sdk_verison_num}.jar
- net.pdfix.ocrtesseract-${sdk_verison_num}.jar

__Install native libraries__ from `{zip}/bin` on your system by copying them into `java.library.path` (e.g. /Library/Java/Extensions/)

- Linux: `libpdfix.so, libpdf_to_html.so, libocr_tesseract.so`
- MacOS: `libpdfix.dylib, libpdf_to_html.dylib, libocr_tesseract.dylib`
- Windows: `pdfix.dll, pdf_to_html.dll, ocr_tesseract.dll`

## Have a question? Need help?
Let us know and we’ll get back to you. Write us to support@pdfix.net or fill the [contact form](https://pdfix.net/support/).