# Addresses

## Goal

Implement a microservice that manages an address book.

## Requirements 

1. The application itself should manage information such as, first name, surname, phone number, address and email of an individual.
2. It should allow consumers of the API to create, read, update and delete any of these entries.

## Limitation(s)

* We can assume access to the local file system.

## Question(s)

* Are we allowed to use a database?
* Do we have to use the local file system?

## Scope

We can extend the requirements beyond the basic requirements, so no scope is defined, therefore, we may deliver additional features.

## Model(s)

An address book is essentially a list of contacts, each containing a first name and surname, along with other pieces of information, such as  mobile number and email address, but a required address.

```java

public class Contact {
	private Person person;
	private Mobile mobile;
	private Address address;
	private String email;
}

public class Person {
	private String firstname;
	private String surname;
}

public class Address {
	private String street;
	private String locality;
	private String county;
	private String country;
}

public class Mobile {
	private String code;
	private String network;
	private String digits;
}

```

We'll assume the character set used will be Java's default UTF-8 for each of the fields represented as a String.


## System Interface

The application will provide the following methods to access contacts within an address book.

* POST /v1/contacts
```java
public void create(Contact contact);
```

* PUT /v1/contacts
```java
public void update(Contact contact);
```

* GET /v1/contacts/{email}
```java
public Contact get(long email);
```

* DELETE /v1/contacts/{email}
```java
public Contact delete(long email);
```


## High-Level Design

The use of the email address here would be an ideal candidate for a primary key. We could have people with the same first name and/or surname, so using this combination as the primary key would not be advisable. An alternative to the email address is using an auto-generated id, however, using the email address here allows for a more simplified approach.

```java

public class Contact {
	private long id;
	private Person person;
	private Mobile mobile;
	private Address address;
	private Email email;
}

```

Given we are limited to using the local file system, we should store these contacts on local disk to ensure some sort of persistence. Using the email address will make use of the operating systems natural ordering of files. Below is an example of how the file system layout would look when this simple principle would be employed.


```bash
krooney@hellh0und13:/tmp/storage$ pwd
/tmp/storage
krooney@hellh0und13:/tmp/storage$ ll
total 16
drwxrwxr-x  2 krooney krooney  4096 Mar 20 20:35 ./
drwxrwxrwt 17 root    root    12288 Mar 20 20:35 ../
-rw-rw-r--  1 krooney krooney     0 Mar 20 20:35 keith.rooney@gmail.com
-rw-rw-r--  1 krooney krooney     0 Mar 20 20:35 wolverine@xmen.org
```

Although it was defined within the scope to use the local file system, we should implement a DAO to interact with a database. The preferable choose here would be some form of a NoSQL database because, 1) it will allow for a fast mock up, 2) a NoSQL database would be well suited to this type of application's requirements.


## Getting Started

### Prequisites

The following dependencies are required to build and start the application.

* [Maven](https://maven.apache.org/)
* [Java](https://openjdk.java.net/) 

Note: The application itself was developed on Ubuntu 16.04 LTS.

### Setup

The setup of this application is minimal, it comes ready out of the box with sensible defaults, so you can skip right to the "Build" section below.

However, if you're interested, there are alternatives to the default choose of storage, which is persisting to the local filesystem.

```
contacts.data.filesystem.type=local
```

The first alternative for storage is persisting to a shared network file system which can be used in production, uat or development.

```
contacts.data.filesystem.type=distributed
```

For more information on the above file system properties, please refer to [FileSystemProperties](src/main/java/org/contacts/data/filesystem/FileSystemProperties.java)

MongoDB is a second alternative choose and can be used within all environments, for more properties related to this please refer to [MongoProperties](src/main/java/org/contacts/data/mongodb/MongoProperties.java)

Modify the application properties, adding one of the alternatives, if preferred and continue onto the next section, "Build".

### Build

Once you've configured everything, build the project, our choose of build tools here is [Maven](https://maven.apache.org/).

```bash
mvn clean install
```

After the project has been built, a target directory will be created containing the executable jar.

### Run

In order to run the application, change into the target directory created in the build step(s) above.

```bash
cd target

```

Once inside this directory, you can execute the jar, addresses.

```bash
java -jar addresses-1.0.0-SNAPSHOT.jar
```

The service will start on port 8080, for details on the API, please refer to the API section below.


## API

### GET /v1/contacts/{email}

| Path       | /v1/contacts/{email}                                            | 
|------------|-----------------------------------------------------------------|
| Method     | GET                                                             |
| Parameters | email - The email of the contact to get.                        |
| Example    | curl "http://localhost:8080/v1/contacts/keith.rooney@gmail.com" |

### DELETE /v1/contacts/{email}

| Path       | /v1/contacts/{email}                                                      | 
|------------|---------------------------------------------------------------------------|
| Method     | DELETE                                                                    |
| Parameters | email - The email of the contact to delete.                               |
| Example    | curl -X DELETE "http://localhost:8080/v1/contacts/keith.rooney@gmail.com" |


### PUT /v1/contacts

| Path    | /v1/contacts                                                                                                                                                              | 
|---------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Method  | PUT                                                                                                                                                                       |
| Body    | Please refer to [Contacts](src/main/java/org/contacts/model/Contact.java).                                                                                                |
| Example | curl -X PUT --header "Content-Type: application/json" --data '{"email":"keithrooney@yapstone.ie", "address": {"country": "Ireland"}}' "http://localhost:8080/v1/contacts" |


### PUT /v1/contacts

| Path    | /v1/contacts                                                                                                                                                               | 
|---------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Method  | PUT                                                                                                                                                                        |
| Body    | Please refer to [Contacts](src/main/java/org/contacts/model/Contact.java).                                                                                                 |
| Example | curl -X POST --header "Content-Type: application/json" --data '{"email":"keithrooney@yapstone.ie", "address": {"country": "Ireland"}}' "http://localhost:8080/v1/contacts" |

