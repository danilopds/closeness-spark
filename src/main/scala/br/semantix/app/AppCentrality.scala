package br.semantix.app

import br.semantix.handlers.SparkHandler
import br.semantix.settings.Settings
import org.apache.spark.SparkContext
import br.semantix.settings.Settings.{appName, edgesGraph}
import ml.sparkling.graph.api.operators.measures.VertexMeasureConfiguration
import org.apache.spark.graphx.{Edge, Graph}
import org.apache.spark.rdd.RDD
import ml.sparkling.graph.operators.OperatorsDSL._

/**
  * Created by danilo.d.silva on 08/08/2017.
  */
object AppCentrality {
  val sc: SparkContext = SparkHandler.getSparkContext(appName)
  val defaultAttribute = 1

  def main(args: Array[String]): Unit = {
    System.setProperty("hadoop.home.dir", Settings.winUtils)

    val edges: RDD[Edge[Int]] =
      sc.textFile(edgesGraph).map { line =>
        val fields = line.split(" ")
        val output : Edge[Int] = Edge(fields(0).toLong, fields(1).toLong, defaultAttribute)
        output
      }

    val graph : Graph[Any, Int] = Graph.fromEdges(edges, "defaultProperty")
    /*graph.edges.foreach(println)
    println("num edges = " + graph.numEdges)
    println("num vertices = " + graph.numVertices)*/

    val centralityGraph: Graph[Double, _] = graph.closenessCentrality(VertexMeasureConfiguration(treatAsUndirected=true))
    centralityGraph.vertices.foreach(println)
  }
}
