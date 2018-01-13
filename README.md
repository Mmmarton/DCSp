# Distributed Control Systems Project
### GPS mobile tracker system

## Project purpose
	The purpose of the project is to geather GPS data from the connected
	mobile devices, and to present them in a webpage. Any mobile device
	having the application installed may use the service, without
	authentication. The person accessing the webpage may select a device id,
	and a map will appear showing the route of the device with the given id.
	
## Requirements
	Implement a distributed system, for monitoring positions of android devices.
	The location should be fetched from GPS and sent to a server.
	
### The system shall be composed of the folowing:
#### Server
	Java application responsible for saving the position of the clients into a database.
	The server should also be able to provide the user with all the positions between a given time interval.
	The application must be configurable to run in tomcat 7.
	Only one instance of this application will run on a given machine.
#### Client
	A mobile application for Android or IOS.
	This application will read the position of the device and send it to the server.
	The data transmission can be either manual or automatic with a given time period.
	The user interface should contain a button, which if pressed, will send the position immediately.
	The system will consist of several such clients.
#### Monitor
	This should be a web application, implemented with any technology.
	It should enable the user to visualize the positions of a device.
	It will also have a login mechanism, but only for a single administrator account.
	Once the admin has logged in, he will be able to select a device ID, a starting date and a final date.
	The application then will display all the positions of the device on a Google Map.
	This application may or may not run in the same Tomcat instance as the Server application.
*The three applications will be decoupled. Communication between them will be made by REST calls.

## Technologies used
1. Server
	- implemented using Java Spring Boot
	- used MongoDB for database
	- used swagger for REST documentation
2. Client
	- built with [Android Studio](https://developer.android.com/studio/index.html)
3. Monitor
	- typescript with angular2
	- styling made with scss
	- implemented using angular-cli

## Diagrams can be found in [docs](docs/)

## Testing
	No special testing was made. There are no unit- or integration test whatsoever.
	While developing, the features were tested manually. No issues were found.
	If you wish, you can test it. If any bug found, please report it. Thank you :)
	
## Deployment
### The application can be found at [heroku](http://dcsp-server.herokuapp.com/)

Licensed under Apache License 2.0 [view](LICENSE)
