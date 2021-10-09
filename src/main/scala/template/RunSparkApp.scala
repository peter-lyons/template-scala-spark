package template

import DateValidator._
import SparkEnvironment._

object RunSparkApp extends App 
  with DateValidator 
  with SparkEnvironment 
  {

  import java.time.format.DateTimeFormatter
  import java.time.LocalDate
  import org.apache.spark.sql.functions._
  import org.apache.spark.sql.Dataset
  import org.apache.spark.sql.Row

  // Import Spark Implicits (for using the $ operator)
  import spark.implicits._

  //val date: String = args(0) // uncomment before build
  val date: String = "20210101" // for debugging

  // verify input date is valid, program exit if not
  validateDate(date)

  val inputDate = formatDate(date)

  /**
    * Formats the input date string as LocalDate after input string has been validated
    *
    * @param d input date as String
    * @return input date as LocalDate
    */
  def formatDate(d: String): LocalDate = {
    val dateFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd")
    LocalDate.parse(d, dateFormat)
  }

  println(inputDate)
}
