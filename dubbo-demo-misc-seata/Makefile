
# prepare exec environment
# 1. craeta seata data bases in mysql, and create tables as sql\dubbo_biz.sql
#     and sql\undo_log.sql says.
# 2. start seata server with seata-server.sh scripts.
#
account_exec:
	java -jar target/dubbo-demo-misc-seata-1.0-SNAPSHOT.jar account

business_exec:
	java -jar target/dubbo-demo-misc-seata-1.0-SNAPSHOT.jar business

order_exec:
	java -jar target/dubbo-demo-misc-seata-1.0-SNAPSHOT.jar order

storage_exec:
	java -jar target/dubbo-demo-misc-seata-1.0-SNAPSHOT.jar storage
