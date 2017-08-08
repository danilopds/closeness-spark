package br.semantix.settings

import com.typesafe.config.ConfigFactory

/**
  * Created by danilo.d.silva on 08/08/2017.
  */
object Settings {
  private val config = ConfigFactory.load()

  private val sparkSettings = config.getConfig("spark_settings")
  private val hadoopSettings = config.getConfig("hadoop_settings")
  private val dataSettings = config.getConfig("data_settings")

  lazy val appName: String = sparkSettings.getString("appName")
  lazy val winUtils: String = hadoopSettings.getString("winUtils")
  lazy val edgesGraph: String = dataSettings.getString("edges")
}
