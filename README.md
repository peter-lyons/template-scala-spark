# Spark Template
- Replicate this for pipeline projects

## Development Environment
To begin, my IDE of choice for this would be VSCode with Scala Metals for local build/test. Metals uses maven, so this works perfectly. I started with "creating a new scala project" in Metals, and chose a basic maven template. It's a barebones template that gives me the freedom to add layers as time goes on. One slight hiccup is that I'm using apple silicon, so there were a few extra steps in getting things like JRE/JDK/Spark etc.. set up.

One of the first priorites was to configure the Maven pom.xml file to support build profiles, and add all the dependencies required for Metals/bloop. The next dependencies to be added were for spark, redis, scalascaper, and scalajhttp.

For data storage I have a Redis instance running on a raspberry pi on my network, with plans to add PostgresSql, Airflow and Jenkins for continuous deployment and scheduling.

What I like about this setup is the ability to rapidly test via Metals/Bloop continuous building. As long as any scala object extends App, which contains the main method, then you can run that object via the built in VSCode debugger.

Then, when everything is good to go, I can package through commandline Maven args and specify the build profile for a particular pipeline, and it will build with the main class as defined in the profile properties. It can also be overriden by passing in a different class arg when invoking through spark-submit.

## Draft Data Pipeline

### Data Transformation
- Spark for data transformation

### Data Quality
- Data quality with Deequ
  - A straightforward library available through mvnrepository created by aws labs to run diagnostics on dataframes/tabular data formats
  - Can be used to check comptleteness, uniqueness, skew etc.. on various columns. Job can be configured to succeed/fail based on result of these deequ tests.

### Data Storage
- OracleDB
- PostgresSQL 

### Build Automation
- Jenkins
- GitLab dual push pipeline

### Job Scheduling
- Airflow 2.x

### Deployment
- TBD

### Maven Pom
- https://www.tutorialspoint.com/maven/maven_build_profiles.htm
- https://www.baeldung.com/maven-profiles

### Maven Assembly
- https://maven.apache.org/plugins/maven-assembly-plugin/assembly.html
- https://medium.com/@kasunpdh/using-the-maven-assembly-plugin-to-build-a-zip-distribution-5cbca2a3b052

### Postgres
- https://opensource.com/article/17/10/set-postgres-database-your-raspberry-pi

### Data Quality
- https://github.com/awslabs/deequ

### Project Documentation
- https://docs.scala-lang.org/overviews/scaladoc/for-library-authors.html

### Configuration
- https://medium.com/@ramkarnani24/reading-configurations-in-scala-f987f839f54d
- https://github.com/lightbend/config

### VSCode Launcher Settings
One significant problem I encounted was adding the conf directory, where I keep application specific configurations, to the build/debugger classpath when using VSCode lenses. So trying to run debug configurations without executing a full maven package (where the conf is added to the zip no problem) was a nightmare. In retrospect I wish I had thought of it sooner, but was busy searching for ways to add the directory to the target during Metals/Bloop maven packaging. 

The answer I was looking for all along, was to add ```"-Dconfig.file=conf"``` to the JVM options within the vscode ```launch.json``` file. So my run configuration now looks like this:
```
{
  "version": "0.2.0",
  "configurations": [
    {
      "type": "scala",
      "request": "launch",
      "name": "nhl-data-centre",
      "mainClass": "nhl.NhlSalaryDb.NhlSalaryDb",
      "args": ["42"],
      "jvmOptions": ["-Dconfig.file=conf"],
      "env": {},
      "buildTarget": "nhl-data-centre"
    }
  ]
}
```