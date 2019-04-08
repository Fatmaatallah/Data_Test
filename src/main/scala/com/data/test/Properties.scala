package com.data.test

import com.typesafe.config.ConfigFactory

object Properties {

  val config = ConfigFactory.load()

  val INPUT_DATA = config.getString("input.data")
  val OUTPUT_DIR = config.getString("output.dir")
  val MODEL_DIR = config.getString("model.dir")
  val NumberOfClusters = config.getInt("number.clusters")
  val NumberOfSeeds = config.getInt("number.seeds")


}
