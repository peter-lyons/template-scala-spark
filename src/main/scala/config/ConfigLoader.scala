package config

import com.typesafe.config.{Config, ConfigException, ConfigFactory}
import scala.util.{Try,Success,Failure}

/**
  * ConfigLoader class will attempt to load the base configuration file stored in application.conf
  * 
  * In the future this may be re-factored to load multiple different configuration files.
  *
  */
object ConfigLoader {
  /**
    * Load configs for this class
    * 
    * MAKE SURE TO SET IN BLOOP JSON IF RUNNING VSCODE LENS
    * to classpath in the json file: 
    * 
    * TO USE ARGS LAUNCH DEBUGGER VIA VSCODE DEBUGGER TAB
    * 
    * "/Users/peter/Documents/template-spark/conf",
    * 
    * This is not a problem in the maven zip/assembly
    */
  def loadConfig(): Config = {  
    tryConfigLoader().get
  }

  /**
    * Will try to load the Config from application.conf
    * 
    * @return
    */
  def tryConfigLoader(): Option[Config] = {
    try {
      val config = ConfigFactory.parseResourcesAnySyntax("application.conf") // loads the default assembly
      val emptyConfig = ConfigFactory.empty() // creates an empty config for comparison
      if(config == emptyConfig){
        throw new Exception("Bad path")        
      } else {
        Some(config)
      }
    } catch {
      case _ : Throwable =>  None//does nothing really
    }
  }
}