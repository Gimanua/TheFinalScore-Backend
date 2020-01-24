# TheFinalScore-Backend
This is the readme for the Final Score project.

## Getting started
To get started you need to install a program to run the backend with. A example is Apache Netbeans. 

## Installation
You will need the Wildfly Server(We have tried other servers, it won't work). It is found here: https://wildfly.org/downloads/
Download the version called "18.0.1.Final". Then you also need the wildfly plugin here: http://plugins.netbeans.org/plugin/53333/wildfly-application-server. Download it  and then run it. Then you need to open the services with Ctrl+5 in netbeans. Then click add server. Wildfly should appear in the "Add Server Instance". Select wildfly the click next. Now locate where the fist file is located and open it in the "Server Location" field. Then you can click finnish. Now you need to tell the project to use the wildfly server. Right click the project in the projects tab then click propeties. Select Run under Build. There you select "Wildfly application Server" in the "Server :" field.

## Database
Download XAMPP controller. Start the Apache and MySql servers. Click the admin button on the MySql server, your web browser should open. Click import then select the "The_Final_Score.sql" file and click run.

You also need to create a user called 'user' with the password 'm25pFs9JqkCUr9w0' which has access to the table The_Final_Score. Alternatively you need to change how the Java connection to the Database is established.

## Running
Open the movie.java (any .java file works) file in the entities folder. Now you can start it with pressing F6 (It is recomended to "Clean and build" first). Now a tab should pop up in your browser. Don't worry if you get the 403 or 404 error message. It works anyway. 

## Architechture
The back end talkes to the frontend, supplying it with the data for the movies from the API (OMDB + the movie db). The back end also talkes to the database, to store user login credentials, and what moves what users has added to their lists.

## Made by:
* Adrian 
* Erik 
* Matteus
