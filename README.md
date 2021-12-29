# RRPSS
The main objective of this assignment is
-to apply the Object-Oriented (OO) concepts you have learnt in the course
-to model, design and develop an OO application
-to gain familiarity with using Java as an object oriented programming language
-to work collaboratively as a group to achieve a common goal

Information
Menu items should be categorized according to its type, eg, Main course, drinks, dessert, etc.
Menu items can be added with details like name, description, price, etc.
Promotional set package comes in a single package price with descriptions of the items to be served.
A customer may order a set package or ala carte menu items.
An order should indicate the staff who created the order.
Staff information can be in the form of name, gender, employee ID and job title.
Reservation is made by providing details like date, arrival time, #pax, name, contact number, etc and the table status is ‘reserved’. The system should check availability and allocate a suitable table.
Contact number is used to identify reservation.
When a reservation is made, the table is reserved till the reservation booking is removed (eg time expired). Once a table is reserved, it cannot be booked for that particular session (AM/PM).
Once an order is entered, the table status is ‘occupied’*.
Once an order invoice is printed, it is assumed that payment has been made and the table statue is ‘vacated’*.
Table comes in different seating capacity, in even sizes, with minimum of 2 and maximum of 10 pax ("Persons At Table").
Order invoice can be printed to list the order details (eg, table number, timestamp) and a complete breakdown of order items details with taxes details.
Sale revenue report will detail the period, individual sale items (either ala carte or promotional items) and total revenue.
Functional Requirements
Create/Update/Remove menu item
Create/Update/Remove promotion
Create order
View order
Add/Remove order item/s to/from order
Create reservation booking
Check/Remove reservation booking
Check table availability
Print bill invoice
Print sale revenue report by period (eg day or month)

Assumptions
Reservation can only be made in at most 1 month in advance.
Reservation will be automatically removed if the guest does not arrive within XX minutes (eg 30 minutes) after the stated arrival time*.
The currency will be in Singapore Dollar (SGD) and Good and Services Tax (GST) and service charge must be included in the order invoice.
There is no requirement for access control and there is no need for authentication (login/logout) in order to use the application.
There is no need to interface with external system, eg Payment, printer, etc. Payment via credit card will always be successful.
The restaurant has 30 tables – 5 x 10-seats, 5 x 8-seats, 10 x 4-seats, 10 x 2-seats.
Tables cannot be combined/joined (eg, join 3 x 2-seats table to form 6-seats table) and the table ID is fixed.
