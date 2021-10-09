package template.DateValidator

trait DateValidator {
  import java.time.format.DateTimeFormatter
  import java.time.LocalDate

  def checkIsValidDate(d: String): Option[LocalDate] = {
    val dateFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd")
    try {
        Some(LocalDate.parse(d, dateFormat))
    } catch {
        case e: Exception => None
    }
  }

  def validateDate(d: String): Unit = {
    checkIsValidDate(d) match {
      case Some(d) => {
          println(s"Input Date: $d")
      }
      case None => {
        println("Please format date as yyyyMMdd")
        //throw new Exception 
      }
    } 
  }

}
