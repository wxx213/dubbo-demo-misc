#
# start sentinel first
# java -Dserver.port=8083 -Dcsp.sentinel.dashboard.server=localhost:8083 -Dproject.name=sentinel-dashboard -jar sentinel-dashboard.jar
#

provider_exec:
	java -jar -Djava.net.preferIPv4Stack=true -Dproject.name=sentinel-demo-provider -Dcsp.sentinel.dashboard.server=192.168.2.101:8083 target/dubbo-demo-misc-sentinel-1.0-SNAPSHOT.jar provider

consumer_exec:
	java -jar -Djava.net.preferIPv4Stack=true -Dproject.name=sentinel-demo-consumer -Dcsp.sentinel.dashboard.server=192.168.2.101:8083 target/dubbo-demo-misc-sentinel-1.0-SNAPSHOT.jar
	
	
