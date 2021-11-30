### zplus



## Table of contents
* [General info](#general-info)
* [Thoughts and Assumptions](#info)
* [Technologies](#technologies)
* [Setup](#setup)

## General info
This project is simple application that, for a given input of items, a receipt is printed out with the purchase details including the total cost of items and
total sales taxes amount.

More insights:
Basic sales tax is applicable at a rate of 10% on all goods(except books, food and medical products) which are exempted. 
Import duty is an additional sales tax applicable on all imported goods at a rate of 5%, with no exemptions.

## Thoughts and Assumptions

SalestaxApplication impliments (FunctonalInterface) CommandLineRunner, and the run calls salesservice.doBilling().
An inventory or list of Items are kept in a json file.This is read and maped to Item POJO.
Inputs are obtained from the commandline from the List of Items.
The user can select the item by entering the serial number of each item
For selecting more items please press 'y' - if you want to continue, otherwise 'n' to exit


Sales tax is calculated according to imported or not, status on each item and finally an agregated reciept is printed with total price as wel as Stestax rounded to nearest 0.05.


## Technologies
Project is created with:
* Java version: 11
* Springboot version: 2.6.3

	
## Setup
To run this project, clone and setup in IDE of your prefernce
Run as boot application.
Enter inputs in commandline

```
$ cd ../salestax
$ mvn clean install
$ mvn spring-boot:run
```
