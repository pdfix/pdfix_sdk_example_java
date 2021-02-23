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

SDK_VER=6.7.0
SDK_BUILD=e5132d42

if [[ "$OSTYPE" == "linux-gnu" ]]; then
  SDK_ZIP="pdfix_sdk_"$SDK_VER"_"$SDK_BUILD"_linux.tar.gz"
  EXTRACT="tar -xzvf"
elif [[ "$OSTYPE" == "darwin"* ]]; then
  SDK_ZIP="pdfix_sdk_"$SDK_VER"_"$SDK_BUILD"_macos.zip"
  EXTRACT=unzip
elif [[ "$OSTYPE" == "msys" ]]; then
  SDK_ZIP="pdfix_sdk_"$SDK_VER"_"$SDK_BUILD"_windows.zip"
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

# copy jar files
cp pdfix/include/java/* src/main/resources/lib

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