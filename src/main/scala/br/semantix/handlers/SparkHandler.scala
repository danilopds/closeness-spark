package br.semantix.handlers

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by danilo.d.silva on 08/08/2017.
  */
object SparkHandler {

  def getSparkContext(appName: String): SparkContext = {

    // Spark configuration
    val conf = new SparkConf()
      .setMaster("local[*]")
      .setAppName(appName)

    // Setup Spark context
    val sc = SparkContext.getOrCreate(conf)
    sc
  }

}
