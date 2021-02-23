Prerequisite software

Download and install
•java (jdk1.8.0_191)
•maven (maven-3.6.0)
•git (latest)
•eclipse (Java EE developers version ) 
 Add M2E and Testng plugin to eclipse


TwitterApiExecution.java having test methods about API task regrading data generation
TwitterSeleniumExecution.java contains test methods about Selenium execution
TwitterPageObject.java -- All required Webelements and methods
ExcelFiles : data generation excel files and Reports

To run from terminal :
mvn clean test (its takes Defaukt browser as Firefox)
mvn clean test -Dbrowser=Chrome based on speicified browser

