# CHALLENGE: VIDEO STORE API 
#### To the developer: The challenge is not fully implemented yet but feel free to contribute, e.g. A JUnit test for returning a delayed list of films.

## LANGUAGES USED: 
	* Java 8 and minor versions
	* Spring Boot with Maven and Lombok plugins
	* Maven
	* H2
	* Mockito and PowerMock	
	
### DESIGN ASSUMPTIONS

#### on Rent action: 
	
	* OLD films initial payment for 5 days is 3€; days extra are 1€/day;
	* REGULAR films initial payment for 3 days is 3€; days extra are 1€/day;
		
#### on Return movies action:

	* On a returning of videos action, the list of movies must be exactly the same (regarding order too) as in the former rented action
	* A customer may have decided to set a renting days number less than 3
	* The id of the rentalEntity created on rent action must be provided along with all the videos to be returned. 
	  Althought this might not be a realistic behaviour, design of the datamodel and time limitation to correct it were the main reasons to 
	  decide the need to provide the ID (reference) of the rental when returning a list of movies.
	
### HOW TO RUN THE APPLICATION:

#### Requirements

	* [lombok](https://projectlombok.org/) code generator used and required to compile in your IDE 
	* STS 4-4.4.1.RELEASE
	
