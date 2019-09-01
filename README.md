# FoundIt

FoundIt is a fully-functional HR system, by using Maven based RESTFul Web Service, and xml & xslt 2.0 as database instead of traditional RDB., as well as a corresponding client web application for connecting the web service interfaces to realize the functions of website. 
Representational State Transfer (REST) is a software architectural style that defines a set of constraints to be used for creating Web services. Other web application can access and manage their resouces by just using http request(GET POST PUT DELETE) to specific URL.

### Function detail
Company User Job HRteam Application Review JobAlert
ShortKey and SecurityKey at Header parameter of an http request to identify the authentication and different permissions based on roles.
Role(candidate, reviewer, manager)


## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

##### Development Environment
```
Windows 10 Home 64bit
Eclipse Mars
JDK 1.80
Apache Maven 3.41
Apache Tomcat 8.0
```

##### Environment Setup

Create new ASP.NET Web Application(.NET Framework) in Visual Studio, Choose MVC Template with No Authentition.
Install References through Nuget Package
```
Install-Package EntityFramework
```

### Installing
- Download and Install Apache Maven
- Install Java EE plugin into Eclipse
- Import FoundItRest and FoundItApp into Eclispe
- Add Maven dependency to two projects by 
  right click project->Properties->Deployment Assessment->Add->Java Path Entry->Maven dependency
- Install and setup Apache Tomcat
- Add two Servers with different port (e.g. 8080 and 8090)
- Edit REST_URI at FoundItApp controller files to meet correct URL of RESTFul service 
- Run two projects at different server

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
