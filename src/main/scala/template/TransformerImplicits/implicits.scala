package template.TransformerImplicits
import template.SparkEnvironment._
import org.apache.avro.data.Json
import org.apache.spark.sql.Dataset
import org.apache.spark.sql.Row

object implicits {
  import org.apache.spark.sql.functions._

  /**
    * Implicit class for dataframes, add methods to this class for implicit function calls.
    *
    * @param df implicit parameter dataframe
    */
  implicit class DatasetUtilsExtended(df: Dataset[Row]){

    /**
      * Explodes a dataframe where a column containing lists into multiple columns.
      *
      * @param inputColumn name of column to explode
      * @return dataframe
      */
    def explodeDataframeTransform(inputColumn: String): Dataset[Row] = {
      df.select(s"$inputColumn").columns.foldLeft(df)((df, column) => 
          df.withColumn(column, explode(col(column))))
    }

    /**
    * Write output for DataFrame
    *
    * @param df
    */
    def writeCsvOutput(outputPath: String): Unit = {
      df
        .repartition(1)
        .write
        .format("csv")
        .mode("overwrite")
        .option("header", "true")
        .save(outputPath)
      println("output complete")
    }
  }
}
