## **SETUP**
The most important setup is to configure the .env file located in the root folder
required.size.to.persist= is the config used to customize the quantity lines to read before sending to the database (10 lines is the default config and worked fine)
router.from is the host folder where you can put your CSV items


## **Running the Service**
In the root folder, there is a docker-compose that will take care of Postgres database.
A docker-compose up is the only command required.

**Please, make sure you have docker-compose installed.**
Once the service is working, a massive log will display on the docker console, meaning that the batch service is parsing and storing data as expected.
A file is moved to a '.camel' directory when the reading process ends.



MI reads from excel and store in a postgree
MS reads from postgree/redis cache
https://www.journaldev.com/18141/spring-boot-redis-cache

