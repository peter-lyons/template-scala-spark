package template.SparkEnvironment

trait SparkEnvironment {
  import org.apache.spark.SparkContext
  import org.apache.spark.sql.SparkSession
  import org.apache.spark.SparkConf

  // define spark context
  /*
  val sc = new SparkContext(new SparkConf()
      .setMaster("local")
      .setAppName("redisApp")
      // initial redis host - can be any node in cluster mode
      .set("spark.redis.host", "0.0.0.0")
      // initial redis port
      .set("spark.redis.port", "6379")
      // optional redis AUTH password
      .set("spark.redis.auth", "Pilea1527")
  )*/

  val spark = SparkSession
  .builder()
  .appName("template-spark")
  .master("local[*]")
  .getOrCreate()
  
  val sc = spark.sparkContext  

  /*
  // Define Spark Session
  val spark = SparkSession
    .builder
    .config("spark.master", "local")
    .appName("Load CSV")
    .getOrCreate()*/

  spark.sparkContext.setLogLevel("ERROR")
}
