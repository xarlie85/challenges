# CHALLENGE: VIDEO STORE API 
#### @developer: The challenge is not fully implemented yet but feel free to contribute, e.g. A JUnit test for returning a delayed list of films or any of the basic and not yet implemented funcionalities for the customer API

## LANGUAGES USED: 
	* Java 8 and minor versions
	* Spring Boot
	* Maven
	* Hibernate (JPA)
	* H2
	* Mockito and PowerMock	
	
## FOCUS ON
	* Basically on having a good application architecture regarding package structure 
	* Requirements
	* Exception handling 
	* Integration Tests
	* Swagger API documentation
	* Boilerplate code avoidance with Lombok 
	* Lombok Builder pattern and factory for testing purposes DTO creation

## NOT FOCUS ON
	* Not on Security layer
	* Not on API JavaDoc
	* Docker-compose

## DESIGN DECISIONS

#### on Rent action: 	
	* OLD films initial payment for 5 days is 3€; days extra are 1€/day;
	* REGULAR films initial payment for 3 days is 3€; days extra are 1€/day;
		
#### on Return movies action:
	* On a returning of videos action, the list of movies must be exactly the same (regarding order too) as in the former rented action
	* A customer may have decided to set a renting days number less than 3
	* The id of the rentalEntity created on rent action must be provided along with all the videos to be returned. 
	  Althought this might not be a realistic behaviour, design of the datamodel and time limitation to correct it were the main reasons to 
	  decide the need to provide the ID (reference) of the rental when returning a list of movies.
	
## HOW TO RUN THE APPLICATION:
	* Spring boot aplication based on Maven

#### Requirements
	* Uses [lombok](https://projectlombok.org/) code generator used and required to compile in your IDE 
	* Created with STS 4-4.4.1.RELEASE (Eclipse)
	
	
