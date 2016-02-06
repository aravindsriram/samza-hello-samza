
To use gradle to build/run the idp-samza project:

1) the project is configured to download and use gradle version 2.6 - on first task execution, it will download the required gradle jars.

2) download/install yarn/kafka/zookeeper:

	$ ./gradlew installGrid

3) build idp-samza job package:

	$ ./gradlew distTar

4) deploy idp-samza project to grid:

	$ ./gradlew deployIdpSamza

5) start the grid (starts up yarn/kafka/zookeeper):

	$ ./gradlew startGrid

6) run the various Samza tasks that are part of project:

	$ ./gradlew runActorFeed
	$ ./gradlew runNotifyIdService
	
7) view all the current Kafka topics:

	$ ./gradlew listKafkaTopics

8) view the Kafka topics output by the various Samza tasks:

	$ ./gradlew dumpAlerts
	( output of Kafka topic scrolls by)
	CTRL-c
	Note: Webrec needs to be running locally, connecting to localhost:9092 zookeeper instance.
	      You can use the following URL to generate and Alert event in webrec
          http://localhost:8080/webrec/alerts/opened?cid=ECCOUSA&uid=100196980009&consumerId=635fa700-aeff-cb24be-e879-a0fc297771db&type=MSA&iteration=1&sstart=null&cm=EMAIL&rrz=NO&amp;rs=10000270
          
	$ ./gradlew dumpActors
	( output of Kafka topic scrolls by)
	CTRL-c

9) see the REST calls made by the samza task - NotifyIdService
	
	$ python bin/pyserver.py 9080

10) stop all the components:

	$ ./gradlew stopGrid
