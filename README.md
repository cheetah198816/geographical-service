**Excel Parsing Microservice using Spring Boot and Spring Batch**

This microservice is divided into two parts :

1. **excel-batch-job** - 
	
    1. This module processes and parses the excel file and writes to the database asynchronously.
    2. It runs on port 9091.
    3. The main class to start the module is ExcelBatchJobApplication.java

2. **geographical-microservice** - 
	
    1. This module reads the results from the database.
	2. It runs on port of 9090.
	3. The main class to start the module is GeographicalApplication.java

Notice : Please start the excel-batch module first and then geographical-microservice.

**Database** :

The database used is PostGreSql 10. Please ensure you have it installed on the system and the relevant database and schema is created. You can find the database and the schema in the application properties spring.datasource.url property 

**Basic Authentication** :

Basic Authentication is supported. The credentials are 

**Username** : user

**Password** : abcd1234

**Base File Path** : 

The base file path property is present in the application.properties of the excel-batch module in the resources folder of the test and main.
You can change the file path property accordingly.

**Integration Tests** :

For running the Integration Tests please ensure that the sample geodata.xls file is in the base file path. You can find the sample file in the test/resources folder of the excel-batch module.

**Endpoints Test** :

You can test the application REST endpoints through Postman.
Please install Postman and login using the below credentials and find the relevant endpoints in the Excel Microservice :

**Username** : cheetah198816

**Password** : abcd1234

