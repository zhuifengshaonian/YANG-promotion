#!/bin/bash
re=`find . -name *.xml`
for i in $re
do
  sed -i 's/opendaylight/onos/g' $i
done
