
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

	$ ./gradlew dumpActors
	( output of Kafka topic scrolls by)
	CTRL-c

9) see the REST calls made by the samza task - NotifyIdService
	
	$ python bin/pyserver.py 9080

10) stop all the components:

	$ ./gradlew stopGrid
