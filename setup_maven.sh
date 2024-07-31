#!/bin/bash

# set the current directory
pushd "$(dirname $0)"

# download and unpack the pdfix sdk build
# mkdir "./tmp"

# pushd $(pwd)/tmp

# mkdir /tmp/some_tmp_dir                         && \
# cd /tmp/some_tmp_dir                            && \
# curl -sS http://foo.bar/filename.zip > file.zip && \
# unzip file.zip                                  && \
# rm file.zip

SDK_VER=8.1.6
SDK_HASH=afc51915

arch=$(uname -m)

https://github.com/pdfix/pdfix_sdk_builds/releases/download/8.1.5/pdfix_sdk_8.1.5_7fe88bf7_windows_x86_64.zip

if [[ "$OSTYPE" == "linux-gnu" ]]; then
  SDK_ZIP="pdfix_sdk_"$SDK_VER"_"$SDK_HASH"_linux"_"$arch.tar.gz"
  EXTRACT="tar -xzvf"
elif [[ "$OSTYPE" == "darwin"* ]]; then
  SDK_ZIP="pdfix_sdk_"$SDK_VER"_"$SDK_HASH"_macos"_"$arch.zip"
  EXTRACT=unzip
elif [[ "$OSTYPE" == "msys" ]]; then
  SDK_ZIP="pdfix_sdk_"$SDK_VER"_"$SDK_HASH"_windows"_"$arch.zip"
  EXTRACT=unzip
else
  echo "error: unknown platform"
  exit 1
fi

SDK_URL="https://github.com/pdfix/pdfix_sdk_builds/releases/download/$SDK_VER/$SDK_ZIP"
echo $SDK_URL

rm -rf pdfix
mkdir -p pdfix                         && \
pushd pdfix                            && \
curl -L -sS $SDK_URL > $SDK_ZIP && \
$EXTRACT $SDK_ZIP                                  && \
rm $SDK_ZIP
popd

JAR_FILE="net.pdfix.pdfixlib-$SDK_VER.jar.zip"
JAR_URL="https://github.com/pdfix/pdfix_sdk_builds/releases/download/$SDK_VER/$JAR_FILE"
mkdir -p lib && \
pushd lib && \
curl -L -sS $JAR_URL > $JAR_FILE && \
unzip $JAR_FILE && \
rm $JAR_FILE
popd

#update version number in maven project pom.xml
mvn -f "pom.xml" versions:set -DnewVersion=$SDK_VER

exit 0
#wget $SDK_URL

# Usage

# curl -s https://github.com/pdfix/pdfix_sdk_builds/releases/latest \
# | grep "browser_download_url.*deb" \
# | cut -d : -f 2,3 \
# | tr -d \" \
# | wget -qi -
popd

# rm -rf "./tmp"