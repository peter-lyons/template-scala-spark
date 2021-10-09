package path

import template.SparkEnvironment.SparkEnvironment
import org.apache.spark.sql.types.StructType

/**
  * The idea for this class object is to provide 3 parameters;
  * 
  * 1. A unique sessinon ID (String)
  * 2. A string containing events (String)
  * 3. A timestamp (Long)
  * 4. Keywords to mark the beginning and end of a flow within the session (String, String)
  *
  * What will be returned 
  * 
  * 1. The beginning and end events in a flow with their timestamps
  * 2. The total time it took to complete the flow
  * 3. Whether the flow was "fully completed"
  * 4. The unique session id that the flow occured in
  * 5. A flow id 
  * 
  */
object PathMethods extends App with SparkEnvironment{
  import spark.implicits._
  import org.apache.spark.sql.functions._
  import org.apache.spark.sql.Row
  import org.apache.spark.sql.functions.udf
  import scala.collection.immutable.ListMap

  // define test data 
  val testSeq: Seq[(Int, String, String, Long)] = Seq(
  (1, "finish", "process 1", 1623290617),
  (1, "middle", "process 1", 1623290317),
  (1, "start",  "process 1", 1623280317),
  (1, "finish", "process 2", 1623280000),
  (1, "start", "process 2",  1623279000),
  (2, "middle", "process 2", 1623270617),
  (2, "start", "process 2",  1623260617),
  (2, "finish", "process 1", 1623253010),
  (2, "start", "process 1",  1623240000))

  // create test dataframe
  val testDf = testSeq
    .toDF("id", "columnA", "columnB", "timestamp")
    .drop("columnB") // drop assigned flow for now

  // order by timestamp
  val orderLabel = udf((values: Seq[Map[Int, String]]) => 
    values
      .flatten
      .toSeq
      .sortBy(_._1)
  )

  // assign flows
  // define start and end point of a flow within a session?
  // in this case it would be "start", and "finish"
  // whats the best way logically to traverse ?
  def assignId(s: String) = {

  }

  val applyFlow = udf((values: Seq[(Int, String)]) => 
    values
  )

  // group by flow and get the min and max elements with timestamp
  val groupCounts = udf((values: Seq[(Int, (String, String))]) => 
    values
      .groupBy(_._2._1)
      .toSeq
      .map(v => (v._1, v._2.minBy(x => x._1), (v._2.maxBy(x => x._1))))
  )

  // apply methids
  val output = testDf
    .select($"id", map($"timestamp", $"columnA").as("hitInfo"))
    .groupBy("id")
    .agg(collect_list($"hitInfo").as("pathInfo"))
    .withColumn("order", orderLabel(col("pathInfo")))
    .withColumn("groupCounts", groupCounts($"order"))
    .drop("order", "pathInfo")

  output.show(100, false)
  output.printSchema()

  val df = output
    .select($"id", explode($"groupCounts").as("flows"))

  df.printSchema()
  df.show(20, false)

  val outDf = df
    .withColumn("processTime", when($"flows._3._2._2" === "finish", ($"flows._3._1" - $"flows._2._1")).otherwise(0))
    .withColumn("completedFlow", when($"flows._3._2._2" === "finish", 1).otherwise(0))

  outDf.show(20, false)
}
