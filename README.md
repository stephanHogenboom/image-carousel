# image-carousel
image-carousel

An webserver app that allows images to be uploaded and retrieved.


# contributors:
Stephan Hogenboom   
Nobert Bakker   
Lucas Hogenboom   


## Setup the database first  

### Running a Postgres database locally - Windows

     1. [Download Postgres](https://www.postgresql.org/download/)
     2. Use the standard setup during installation. Choose a port of your choice. Do not forget the admin password. No steps 
    needed in the Stack Builder.
     3. Open Startmenu -> PostgreSQL 9.6 -> pgAdmin 4
     4. Create user taskservice: click right mouse button on 'Login/Group Roles' -> Create -> Login/Group Role... 
    Password is filled in the Definition tab. Tick the rights-boxes 'Can Login?' & 'Superuser' in Privileges.
     5. Create the database taskservice click right mouse button on 'Databases' -> Create -> Database... 
    Make sure that the owner is the taskservice user.
     6. Make sure that the data in the application.properties file is equal to all the info for your local db. Start taskservice

### Running a Postgres database locally - Linux

If the server is a unix/ linux machine it is easier to use docker to run a instance of postgres.
To run postgres locally through docker open a terminal and type the following line(replace the words between <> with the actual values): 
    
    $ sudo docker run --name <SERVICE_NAME>-postgres -p 5432:5432 -e POSTGRES_USER=<SERVICE_NAME> -e POSTGRES_PASSWORD=<SERVICE_NAME> -d postgres

For the default setting this would be: 
    
    $ sudo docker run --name imagecarousel-postgres -p 5432:5432 -e POSTGRES_USER=imagecarousel -e POSTGRES_PASSWORD=imagecarousel -d postgres

you can change the POSTGRES_USER and POSTGRES_PASSWORD to a value of your liking. If you do so, don't forget to also change the values of these fields in the /task-service/src/main/resources/application.properties file, before starting the application

