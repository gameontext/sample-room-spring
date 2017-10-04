# Game On! Microservices and Spring

[Game On!](https://gameontext.org/) is both a sample microservices app, and a throwback text adventure brought to you by the WASdev team at IBM. This app demonstrates how microservice architectures work from two points of view:

1. As a Player: navigate through a network/maze of rooms, and interact with other players and the items or actions available in each room.
2. As a Developer: extend the game by creating simple services that define rooms. Learn about microservice architectures and their supporting infrastructure as you build and scale your service.

You can learn more about Game On! at [http://gameontext.org/](http://gameontext.org/).

## Introduction

This walkthrough will guide you through creating and deploying a simple room (a microservice) to the running Game On! app. This microservice is written in Java as a web app deployed on Spring.

The microservice can be (a) deployed as a Cloud Foundry app or (b) built into a docker container.

Game On! communicates with this service (a room) over WebSockets using the [Game On! WebSocket protocol](https://book.gameontext.org/microservices/WebSocketProtocol.html). Consider this a stand-in for asynchronous messaging like MQTT, which requires a lot more setup than a simple WebSocket does.

## Requirements

- [Maven](https://maven.apache.org/install.html)
- Java 8: Any compliant JVM should work.
  * [Java 8 JDK from Oracle](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
  * [Java 8 JDK from IBM (AIX, Linux, z/OS, IBM i)](http://www.ibm.com/developerworks/java/jdk/)

## Let's get started!

1. Create your own fork of this repository ([what's a fork?](https://help.github.com/articles/fork-a-repo/))
2. Create a local clone of your fork ([Cloning a repository](https://help.github.com/articles/cloning-a-repository/))

## Build the service locally

1. `cd sample-room-spring`
2. `mvn spring-boot:run`

After running this, the server will be running locally at [http://localhost:8080/](http://localhost:8080/).
* Visiting this page provides a small form you can use to test the WebSocket endpoint in your service directly.
* A health URL is also defined by the service, at http://localhost:8080/health

You can also build a Docker image and run the app through there:

1. `mvn install dockerfile:build`
2. `docker run -p 8080:8080 -t sampleroomspring`

Then the server will be running locally at [http://localhost:8080/](http://localhost:8080/).

## Make your room public!

For Game On! to include your room, you need to tell it where the publicly reachable WebSocket endpoint is. This usually requires two steps:

* [hosting your service somewhere with a publicly reachable endpoint](https://book.gameontext.org/walkthroughs/deployRoom.html), and then
* [registering your room with the game](https://book.gameontext.org/walkthroughs/registerRoom.html).

## Build a docker container

Creating a Docker image is straight-up: `docker build .` right from the root menu.

A `docker-compose.yml` file is also there, which can be used to specify overlay volumes to allow local development without restarting the container. See the [Advanced Adventure for local development with Docker](https://book.gameontext.org/walkthroughs/local-docker.html) for a more detailed walkthrough.

## Ok. So this thing is running... Now what?

We know, this walkthrough was simple. You have a nice shiny service that has a REST API (/health),
and emulates async messaging behavior via a WebSocket. So?

The purpose of this text-based adventure is to help you grapple with microservices concepts and technologies
while building something other than what you do for your day job (it can be easier to learn new things
when not bogged down with old habits). This means that the simple service that should be humming along
merrily with your name on it is the beginning of your adventures, rather than the end.

Here is a small roadmap to this basic service, so you can go about making it your own:

* `app.RoomImplementation`
   This class contains the core elements that make your microservice unique from others.
   Custom commands and items can be added here (via the `app.RoomDescription`
   member variable). The imaginatively named `handleMessage` method, in particular, is called
   when new messages arrive.

* `app.SocketHandler`
   The WebSocket endpoint for the service.
   
* `app.HealthEndpoint` Defines the REST endpoint at `/health`.

* `src/test` -- Yes! There are tests!

Things you might try:

* Use RxJava to manage all of the connected WebSockets together as one event stream.
* Call out to another API (NodeRed integration, Watson API, Weather API) to perform actions in the room.
* Integrate this room with IFTTT, or Slack, or ...
* .. other [Advanced Adventures](https://book.gameontext.org/walkthroughs/createMore.html)!

Remember our https://gameontext.org/#/terms. Most importantly, there are kids around: make your parents proud.

## How the build works

This project is built using Maven and makes use of the [Bluemix Developer CLI plugin](https://console.bluemix.net/docs/cloudnative/dev_cli.html#developercli) to integrate with Spring and Bluemix. Visit this [blog post](https://www.ibm.com/blogs/bluemix/2017/09/creating-running-deploying-spring-microservices-5-minutes/) to see how you can use the Bluemix CLI to quickly generate and deploy a Spring microservice.
