
#!/bin/bash
isExistApp=`pgrep java`
if [[ -n  $isExistApp ]]; then
   service tomcat9 stop
fi
