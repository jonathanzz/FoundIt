# FoundIt

FoundIt is a simple HR system, by using Maven based RESTFul Web Service and a corresponding web application. Using xml file and xslt 2.0 as database instead of traditional RDB. 
Representational State Transfer (REST) is a software architectural style that defines a set of constraints to be used for creating Web services. Other web application can access and manage their resouces by just using http request(GET POST PUT DELETE) to specific URL.

### Function detail
Company User Job HRteam Application Review JobAlert
ShortKey and SecurityKey at Header parameter of an http request to identify the authentication and different permissions based on roles.
Role(candidate, reviewer, manager)


## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

What things you need to install the software and how to install them
```
Give examples
```
##### Development Environment
```
Windows 10 Home 64bit
Visual Studio Professional 2017
Microsoft SQL Server 2017
Google Chrome Version 76.0
```

##### Environment Setup

Create new ASP.NET Web Application(.NET Framework) in Visual Studio, Choose MVC Template with No Authentition.
Install References through Nuget Package
```
Install-Package EntityFramework
```
Use EntityFramework Code First method to create Database tables through exsiting Model files
Add migrations to Database
```
Enable-Migrations
Add-Migration 0001
Update-Database -Verbose
```
Install Dynamic Linq package
```
Install-Package System.Linq.Dynamic
```
Install PagedList for MVC via NuGet
```
Install-Package PagedList.mvc
```
### Installing

A step by step series of examples that tell you how to get a development env running

Say what the step will be


End with an example of getting some data out of the system or using it for a little demo

## Deployment

Add additional notes about how to deploy this on a live system

## Built With

* [Dropwizard](http://www.dropwizard.io/1.0.2/docs/) - The web framework used
* [Maven](https://maven.apache.org/) - Dependency Management
* [ROME](https://rometools.github.io/rome/) - Used to generate RSS Feeds

## Contributing

Please read [CONTRIBUTING.md](https://gist.github.com/PurpleBooth/b24679402957c63ec426) for details on our code of conduct, and the process for submitting pull requests to us.

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/your/project/tags). 

## Authors

* **Billie Thompson** - *Initial work* - [PurpleBooth](https://github.com/PurpleBooth)

See also the list of [contributors](https://github.com/your/project/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

* Hat tip to anyone whose code was used
* Inspiration
* etc
