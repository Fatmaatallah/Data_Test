package com.data.test

import java.util.logging.{Level, Logger}
import java.io.{BufferedWriter, FileWriter}

import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.clustering.KMeans
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.sql.{SaveMode, SparkSession}
import org.apache.spark.ml.evaluation.ClusteringEvaluator


object Run {

  def main(args: Array[String]) : Unit ={

    val logger = Logger.getLogger(getClass.getName)

    // Initializing SparkContext
    val spark = SparkSession.builder()
      .master("local")
      .appName("Data_Test")
      .getOrCreate()

    logger.log(Level.INFO, "Spark context has been initialized with name {0}", spark.sparkContext.appName)

    // Importing data
    val dataset = spark.read.json(Properties.INPUT_DATA)


    logger.log(Level.INFO, "Dataset has been imported. It contains {0} entries", dataset.count())

    // Displaying a sample of imported data
    dataset.show(10, false)


    // Assembling the Vector of input data wish is station positions (latitude and longitude)
    val assembler = new VectorAssembler()
      .setInputCols(Array("latitude","longitude"))
      .setOutputCol("features")

    // Initializing the KMeans clustering model
    val kmeans = new KMeans()
      .setK(Properties.NumberOfClusters).setSeed(Properties.NumberOfSeeds)
      .setFeaturesCol("features")
      .setPredictionCol("cluster")

    // Building the Pipeline for clustering
    // Step 1 : assembling features
    // Step 2 : Running the clustering on data
    val pipeline = new Pipeline().setStages(Array(assembler, kmeans))


    // Running the clustering pipeline
    val model = pipeline.fit(dataset)

    // Saving the model
    model.write
      .overwrite()
      .save(Properties.MODEL_DIR)

    logger.log(Level.INFO, "Model has been saved in {0}", Properties.MODEL_DIR)

    val clusters = model.transform(dataset)


    // Saving the dataset with labels
    clusters.drop("features")
      .repartition(1)
      .write
      .mode(SaveMode.Overwrite)
      .format("com.databricks.spark.csv")
      .option("header", "true")
      .option("delimiter", ";")
      .save(Properties.OUTPUT_DIR)

    logger.log(Level.INFO, "Clustered data has been saved in {0}", Properties.OUTPUT_DIR)

    // Displaying a sample of data with labels
    clusters.drop("features").show(10, false)

    spark.close()
  }
}
