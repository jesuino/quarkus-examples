# Quarkus + Gradle 

Simple Rock Band REST API using Gradle and Quarkus.

## Requirements 

* Java JDK 11

## Developing

Import the project in your favorite IDE. 
In the project root dir use `./gradlew quarkusDev` to start quarkus in dev mode. Start developing...

In development mode you can access and invoke endpoints using `localhost:8080/swagger-ui`

## Building

Run `./gradle quarkusBuild` and the built JARs will be found in `build` directory.

## Learning

This project has three classes:

* Band: Hold Bands information
* BandStorage: In memory storage for bands information
* BandResource: REST Endpoints

#### Band class

This class hold information about the band. It was build in a conservative way, using get/set for all attributes, but you could use public attributes because it would also work with Panache (service to map and store objects in a database)

#### BandStorage

A class that uses a Map structure to store bands by ID. It also generate IDs for all bands added to the storage. 

Notice that the class has an annotation `@ApplicationScoped`. This special annotation tells Quarkus that the class should be created (instance) once. After using this annotation Quarkus will be aware of this class and will make easy to use it in other classes.

Since the class is controlled by Quarkus, we can ask it to run some methods. This is what happens on method *init*. It has the annotation `@PostConstruct`, which tells quarkus that it should run this method after it creates the class instance. In this method we create some initial band data. 

The most complex methods are searchByName and searchByYear, which uses the private filterBands methods to filter all stored bands by a Predicate, which is a way to filter bands. It also uses Java Lambda.


#### BandResource

This is where we expose the storage funtionality to be accessed outside our computer, via HTTP specifically. HTTP means that we could have a client application running in a browser using Javascript to access the app functionalities. 

To expose the apps functionalities we use JAX-RS, which is an API that help us to write Java code that will later be mapped to HTTP verbs and URIs. For example, the annotation `@Path` describes which URI (for example in *google.com/search* - *search* is the URI) all the methods of the class will be invoked. `@GET` is the HTTP verb, which means that the method *all* is invoked when we do a GET to `localhost:8080/band` in our application. 

Notice also the annotations `@Produces` and `@Consumes` to tell which format of data is used to exchange information between the server and the client. In our case we use the popular JSON format.

The class has a single attribute storage. Notice the annotation `@Inject` on top of it. This annotation is used by Quarkus so it knows that our class should be created by it and then injected inside BandResource.

In method *remove* we use the verb `@DELETE` and it requires a parameter. So to invoke it you must pass some number in the URL (*/band/123*). This number is passed as a parameter to the method and Quarkus knows that it should take it from the URL because we use the annotation `@PathParam` before the parameter. This is also done in `searchByName` and `searchByYear` methods. 

