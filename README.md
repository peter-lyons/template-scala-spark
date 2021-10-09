# Spark Template
- Replicate this for pipeline projects

## Development Environment
- VSCode with Scala Metals for local build/test. Metals uses maven, so this works perfectly. 
- configure the Maven pom.xml file to support build profiles, and add all the dependencies required for Metals/bloop. 
- Next dependencies to be added are for spark, Postgres, and Oracle
- Database support for Oracle and Postgres. Credentials stored in a separate file on server.
- Package through command line Maven args and specify the build profile for a particular pipeline, and it will build with the main class as defined in the profile properties. It can also be overriden by passing in a different class arg when invoking through spark-submit.

## TODO
- Update JDK/JRE to java 11
- Scala 3?
- Create framework for implementing basic SQL
  - Use a pushdown query framework. Analysts provide the SQL query they want in production, and we can either submit it to Oracle or PostgresDB for execution. The result will be read into memory and written out to specified database (Postgres)
  - There's two options for approaching here, new project jar for each asset, or continually add assets to the same project. In the form of a config file.
- Create framework for implementing python files

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
  - pulls latest version of repository
  - runs mvn build args
  - copies the output target and airflow dag to specified directory
- GitLab dual push pipeline

### Job Scheduling
- Airflow 2.x

### Deployment
- Internal server

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
