@echo off
@color F0
@title KMS_343
set CLASSPATH=.;dist\*;dist\lib\*
java -Xms5G -Xmx10G -Dnet.sf.odinms.wzpath=wz server.Start
pause