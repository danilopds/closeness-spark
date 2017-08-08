package br.semantix.app

import br.semantix.handlers.SparkHandler
import br.semantix.settings.Settings
import org.apache.spark.SparkContext
import br.semantix.settings.Settings.{appName, edgesGraph}
import ml.sparkling.graph.api.loaders.GraphLoading.LoadGraph
import ml.sparkling.graph.api.operators.measures.VertexMeasureConfiguration
import ml.sparkling.graph.loaders.csv.GraphFromCsv.CSV
import ml.sparkling.graph.loaders.csv.GraphFromCsv.LoaderParameters.Delimiter
import org.apache.spark.graphx.{Edge, Graph}
import org.apache.spark.rdd.RDD
import ml.sparkling.graph.operators.OperatorsDSL._

/**
  * Created by danilo.d.silva on 08/08/2017.
  */
object AppCentrality {
  val sc: SparkContext = SparkHandler.getSparkContext(appName)

  def main(args: Array[String]): Unit = {
    System.setProperty("hadoop.home.dir", Settings.winUtils)

    val edges: RDD[Edge[String]] =
      sc.textFile(edgesGraph).map { line =>
        val fields = line.split(" ")
        Edge(fields(0).toLong, fields(1).toLong)
      }

    val graph : Graph[Any, String] = Graph.fromEdges(edges, "defaultProperty")
    println("num edges = " + graph.numEdges)
    println("num vertices = " + graph.numVertices)

    /*val centralityGraph: Graph[Double, _] = graph.closenessCentrality(VertexMeasureConfiguration(treatAsUndirected=true))
    println("num edges = " + centralityGraph.edges)
    println("num vertices = " + centralityGraph.vertices)*/
  }
}
