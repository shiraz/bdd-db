## PostgreSQL Database Behavior Driven Development Project

### Project Description:
This is a Maven project that has Cucumber tests that validates a DVD rental business data using the following technologies:
1. Apache
    1. Commons
    2. Lang3
2. Cucumber
3. Gherkin
4. Gson
5. Java 1.8
6. Maven
7. PostgreSQL
8. TestNG

### Test Description:

### Steps to Execute Tests:
1. Install Java 1.8 or higher.
2. Install Maven.
3. Install PostgreSQL (ensure to install pgAdmin as well).
4. [Steps to setup database]
5. Clone this repository.
6. Open up Terminal/Command Prompt, navigate to this project, and then execute the following command: 
    1. **mvn clean test -Dhost=localhost -DdbName=dvdrental -DdbPort=[port where the PostgreSQL database is setup] -DdbUsername=[The admin username of the local PostgreSQL setup] -DdbPwd=[The admin password of the local PostgreSQL setup]**
        1. Example: mvn clean test -Dhost=localhost -DdbName=dvdrental -DdbPort=5432 -DdbUsername=postgres -DdbPwd=admin
7. The reports can be found at the following locations: 
    1. Cucumber HTML Report: **target/cucumber-html-report/index.html**
        1. Note: This report only contains BDD data and no logs.
    2. Cucumber TestNG Report: **target/surefire-reports/Cucumber_TestNG Runner**
        1. Note: This report contains both BDD and logs reporting, which therefore makes it the best report for viewing 
    3. TestNG Report: **target/surefire-reports/emailable-report.html**
        1. Note: This report only has test report logs.
