#OnlineShop
Online shop application.

Run application:
- mvn spring-boot:run
or
- mvn package
- java -jar online-shop-1.0-SNAPSHOT.jar 

####Set the next configuration parameters for application:

DB configuration:
- spring.datasource.url=jdbc:postgresql://localhost:5434/online-shopma
- spring.datasource.username=postgres
- spring.datasource.password=postgres

ElasticSearch configuration:
- spring.data.elasticsearch.cluster-nodes = 127.0.0.1:9300
