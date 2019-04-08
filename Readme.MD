# CityBikes Clustering
Clustering of citybikes stations based on the location.


## Requirements
* [Java 8]
* [Scala 2.12.8]
* [SBT 1.2.8]
* [Spark 2.4.0]

This application is deployed as a release version 1.0.
  
## Launching Application 
* COnfig</br>

Before executing the code, you have to modifiy the configuration file available   in**./src/resources/application.properties**.
<pre>

    input.data="Path to the input data"
    output.dir="The directory that will be used to store clustered data"
    model.dir= "Directory that will be used to store the model for another use"
    number.clusters=number of cluster(s)
    number.seeds=number of seed(s)
</pre>

* Running with spar-submit</br>

Use assembly in SBT to create the fat jar with all required dependencies.
<pre>sbt clean assembly</pre>
Run via invoking spark-submit.
<pre> 
spark-submit --class com.data.test.Run C:\Users\HP\IdeaProjects\Data_Test\target\scala-2.12\Data_Test-assembly-0.1.jar
</pre>

## Results
The number of clusters for this use case is 5 and it could be then interpreted as north, south, east, west and center.
   


