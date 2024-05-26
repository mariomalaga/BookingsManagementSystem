# BookingsManagementSystem
This project is a Booking Management System with 2 APIs, in the first API (/classes) you can create, check, update, delete classes in a date range and in the second API(/bookings) you can book for a specific class, check bookings, delete a booking and update a booking.

## Prerequisites
You need to have Java installed in your machine.

## Project initialization
You have 2 ways to initialize the project: 
- If you are using an IDE, you need to run the file "MainApplication" located in "src\main\java\com\glofox" and the server will start.
- If you are not using an IDE, you can run the project opening a terminal in the root path of the project and run the next command "./gradlew bootRun".

Now that your server is running you can use Postman to call the endpoints. The base URL and port to call will be "http://localhost:8080" and the main endpoints will be "/classes" and "/bookings".

## /classes endpoints
In this endpoint you can manage the classes.

This endpoint is composed by the next endpoints:
- POST /classes/addClass
  
  You can create a new class sending the next JSON structure body:
  ```
    {
      "className" : "pilates", // Name of the class
      "start_date" : "2024-06-02", // Start Date of the class
      "end_date" : "2024-06-03", // End date of the class
      "capacity" : 10 // Maximum capacity for a class per day
    }
  ```
   >You can not create a class for a time range that is already in use for another class

- GET /classes/checkClasses
  
  This endpoint will return a list of classes.
  >If you don´t have the file classes.txt, it will return a NotFound exception

- DELETE /classes/deleteClass/{id}
  
  In this endpoint you will delete an specific class. You need to specific the "id", you can find the id if you do the "GET /classes/checkClasses" or checking the file "classes.txt".

- PUT /classes/updateClass/{id}

  In this endpoint you will update an specific class and a JSON body with the data you want to update.
  ```
    {
      "className" : "acrogym", // Name of the class
      "start_date" : "2024-06-02", // Start Date of the class
      "end_date" : "2024-06-03", // End date of the class
      "capacity" : 10 // Maximum capacity for a class per day
    }
  ```
  

## /bookings endpoints
  In this endpoint an user can book a class for an specific date if the class exists.

  This endpoint is composed by the next endpoints:
- POST /bookings/bookDate

  You can book a class sending the next JSON structure body
  ```
  {
    "name": "Mario", // Name of the user that wants to book a class
    "bookingDate": "2024-05-10" // Date the user wants to book a class
  }
  ```

  >You can not book a class if the class doesn´t exists.

- GET /bookings/checkBookings

  This endpoint will return a list of all the bookings.
  >If you don´t have the file bookings.txt, it will return a NotFound exception

- DELETE /bookings/deleteBooking/{id}

  In this endpoint you will delete an specific booking.

- PUT /bookings/updateBooking/{id}

  In this endpoint you will update an specific booking and a JSON body with the data you want to update.
  ```
  {
    "name": "Carlos", // Name of the user that wants to book a class
    "bookingDate": "2024-05-10" // Date the user wants to book a class
  }
  ```
