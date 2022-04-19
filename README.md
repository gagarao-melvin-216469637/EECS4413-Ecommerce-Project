# EECS4413-Ecommerce-Project
<div id="top"></div>

<!-- How to Run -->
## How to Run

1. Download the SQL file `ShoppingCart_FINAL.sql`.
2. Launch MySQL Workbench (Tested on Version 8.0 CE).
3. Select Local instance mySQL80.
4. Once launched, create a new schema using using the icon at the top of the screen.
5. Name the schema as you wish (i.e. shoppingcart)
* MySQL
  ```sh
  Apply->Apply->Finish
  ```
7. Next at the top of the screen select
8. Select 'Import from Self-Contained File' and navigate to `ShoppingCart_FINAL.sql`.
9. Select 'Default Target Schema' and choose the schema just created(i.e. shopping cart).
10. Then select the 'Import Progress' tab at the top and 'Start Import' and the SQL database should be downloaded.
* MySQL
  ```sh
  Server->Data import
  Import from Self-Contained File
  Default Target Schema
  Import Progress
  Start Import
  ```
11. Open this google drive link and download the latest version of the application (.zip): `https://drive.google.com/file/d/1nysYfjDqOp74tSOTglzwAT6OaJpfeAZF/view?usp=sharing`
12. Launch Eclipse (Eclipse IDE for Java Enterprise Developers)
13. Select File->Import then from the drop-down menu select General->Existing Project Into Workspace.
14. Select archive file and navigate to the downloaded zip file.
15. Once the project has been successfully imported, open the file and navigate to the `application.properties` file through 
Eclipse
```sh
   KrispyKart/src/main/resources/application.properties
```
16. In line 19, set the schema to your created schema. (i.e. spring.datasource.url=jdbc:mysql://localhost:3306/shoppingcart)
17. On lines 20 and 21, set your respective username and password for MySQL.
18. Once saved, navigate to 
19. Right click the application and select Run as -> Java Application. The spring application should show that it is running on the console.
Eclipse
```sh
   KrispyKartApp.javaKrispyKart/src/main/java/org.o7planning.krispykart/KrispyKartApp.java
   Run as -> Java Application
```
20. Finally, go to your browser and enter local localhost:8080 to see the application. (tested on Google Chrome)
21. If you would like to view the admin and manager privileges on the application, for admin enter username: admin, password: admin111. For manager enter username: manager, password: manager222.
* Admin
  ```sh
  username: admin
  password: admin111
  ```
  * Manager
  ```sh
  username: manager
  password: manager222
  ```

<h1>REST API</h1>
Below are examples of the implemented REST API for products and orders.

<h2>Product</h2>

**GET http://localhost:8080/products/**
```sh
GET http://localhost:8080/products/Google → [ {all products} ]
```

**GET http://localhost:8080/products/{id}**
```sh
GET http://localhost:8080/products/Google → [ {"code": "Google", "image": "9KWVIgABA", "name": "Pixel 2", "price": 1129.0, "date": "2022-04-17"} ]
```

**POST http://localhost:8080/products/{product}**
```sh
POST http://localhost:8080/products{"code": "Fake", "image": "9KWVIgABA", "name": "Pixel 2", "price": 1129.0, "date": "2022-04-17"} → Adds to MySQL database if not already present
```

**DELETE http://localhost:8080/products/{product}**
```sh
DELETE http://localhost:8080/products{"code": "Google", "image": "9KWVIgABA", "name": "Pixel 2", "price": 1129.0, "date": "2022-04-17"} → Removes matching product
```

<h2>Orders</h2>

**GET http://localhost:8080/orders/**
```sh
GET http://localhost:8080/products/ → [ {all orders} ]
```

**GET http://localhost:8080/orders/{id}**
```sh
GET http://localhost:8080/orders{0ed3166a} → [ {"id": "0ed3166a","amount": 989.0, "customer_address": "94 Headquarters Road", "customer_email": "shield@email.com", "customer_name": "Steve Rogers", "customer_phone": "9687590143", "order_date": "2022-04-17", "order_num": 5} ]
```

**POST http://localhost:8080/orders/{order}**
```sh
POST http://localhost:8080/orders{"id": "iu7d78","amount": 989.0, "customer_address": "94 Headquarters Road", "customer_email": "shield@email.com", "customer_name": "Steve Rogers", "customer_phone": "9687590143", "order_date": "2022-04-17", "order_num": 5} → Adds to MySQL database if not already present
```

**DELETE http://localhost:8080/orders/{order}**
```sh
DELETE http://localhost:8080/orders{"id": "iu7d78","amount": 989.0, "customer_address": "94 Headquarters Road", "customer_email": "shield@email.com", "customer_name": "Steve Rogers", "customer_phone": "9687590143", "order_date": "2022-04-17", "order_num": 5} → Removes matching product
```

<!-- CONTACT -->
## Contact

Arjit Johar - arjit39@my.yorku.ca

Melvin Gagarao - melving@my.yorku.ca

Sharujan Rajakumar - sharujan@my.yorku.ca

Varuhn Ruthirakuhan - vruth11@my.yorku.ca

<p align="right">(<a href="#top">back to top</a>)</p>
