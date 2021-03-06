# Soap WS using a wsdl

In this article, we will provide a simple example of how you can build a soap web service using spring.


## Overview

In this example we will create a simple spring app to expose some data about some countries. This example was taken from :

- https://spring.io/guides/gs/producing-web-service/

- https://www.baeldung.com/spring-boot-soap-web-service

In this example we will use the wsdl approach, which means that we have already a wsdl (a description file) of our service. 
We are using the jaxb plugin (check pom.xml) which gonna parse this wsdl and generate java classes.
We gonna use these classes in our source code to manipulate the response of our service.

You can find the wsdl file in the resources. The file name is `countries.xsd`.

You can find more examples in my spring boot examples repo :

- https://github.com/amdouni-mohamed-ali/spring-boot-examples/tree/master/web/soap/producer

### Running the application

It's a spring boot app, you have to run the main method in the `SpringBootStandAloneApp.java` class. Or you can use the spring boot maven plugin :

```shell script
$ mvn spring-boot:run
```

And now you can check if the exposed ws works fine. The wsdl will be at this endpoint :

-  [http://localhost:8080/ws/countries.wsdl](http://localhost:8080/ws/countries.wsdl)

To make calls to the web service, you can use a toot like soap ui which will build all requests for you from the wsdl endpoint.

Otherwise, you can a command line tool like curl. In the command line below, we gonna make a call to get using spain as parameter.

From the root directory of this project, run this command :

```shell script
curl --header "content-type: text/xml" -d @src/test/resources/request.xml http://localhost:8080/ws
```

As a result, you should see the following response:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
   <SOAP-ENV:Header />
   <SOAP-ENV:Body>
      <ns2:getCountryResponse xmlns:ns2="http://spring.io/guides/gs-producing-web-service">
         <ns2:country>
            <ns2:name>Spain</ns2:name>
            <ns2:population>46704314</ns2:population>
            <ns2:capital>Madrid</ns2:capital>
            <ns2:currency>EUR</ns2:currency>
         </ns2:country>
      </ns2:getCountryResponse>
   </SOAP-ENV:Body>
</SOAP-ENV:Envelope>
```