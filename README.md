# EECS4413-Ecommerce-Project
<div id="top"></div>

<!-- How to Run -->
##How to Run
1. Download the SQL file ShoppingCart_FINAL.sql.
2. Launch MySQL Workbench (Tested on Version 8.0 CE).
3. Select Local instance mySQL80.
4. Once launched, create a new schema using using the icon at the top of the screen.
5. Name the schema as you wish (i.e. shoppingcart) and Apply->Apply->Finish.
6. Next at the top of the screen select Server->Data import.
7. Select 'Import from Self-Contained File' and navigate to the previously downloaded SQL file.
8. Select 'Default Target Schema' and choose the schema just created(i.e. shopping cart).
9. Then select the 'Import Progress' tab at the top and 'Start Import' and the SQL database should be downloaded.

10. Open this google drive link and download the latest version of the application (.zip): https://drive.google.com/file/d/1nysYfjDqOp74tSOTglzwAT6OaJpfeAZF/view?usp=sharing
11. Launch Eclipse (Eclipse IDE for Java Enterprise Developers)
12. Select File->Import then from the drop-down menu select General->Existing Project Into Workspace.
13. Select archive file and navigate to the downloaded zip file.
14. Once the project has been successfully imported, open the file and navigate to the application.properties file through KrispyKart/src/main/resources/application.properties.
15. In line 19, set the schema to your created schema. (i.e. spring.datasource.url=jdbc:mysql://localhost:3306/shoppingcart)
16. On lines 20 and 21, set your respective username and password for MySQL.
17. Once saved, navigate to KrispyKartApp.java. (KrispyKart/src/main/java/org.o7planning.krispykart/KrispyKartApp.java)
18. Right click the application and select Run as -> Java Application. The spring application should show that it is running on the console.
19. Finally, go to your browser and enter local localhost:8080 to see the application. (tested on Google Chrome)
20. If you would like to view the admin and manager privileges on the application, for admin enter username: admin, password: admin111. For manager enter username: manager, Password: manager222.

<!-- CONTACT -->
## Contact

Arjit Johar - arjit39@my.yorku.ca

Melvin Gagarao - melving@my.yorku.ca

Sharujan Rajakumar - sharujan@my.yorku.ca

Varuhn Ruthirakuhan - vruth11@my.yorku.ca

<p align="right">(<a href="#top">back to top</a>)</p>
