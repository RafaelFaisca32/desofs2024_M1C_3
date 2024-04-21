# Threat Modeling Process

## Trust Levels

| ID | Name | Description |
|:----:|:------|:---------|
| 1 | Anonymous Web User | A user who has connected to TruckMotion but has not provided valid credentials. |
| 2 | User with Invalid Credentials | A user who has connected to TruckMotion and is attempting to log in using invalid login credentials. |
| 3 | Manager | Managers can register customers and drivers, approve or reject created jobs by the customers and assign jobs for the drivers. |
| 4 | Customer | Customers can create jobs requests and see all the information directly involved to their jobs. |
| 5 | Drivers | Drivers can see all the jobs assigned to them, start and finish those jobs. |
| 6 | Application FE Administrator | The Administrator FE can configure the application front-end. |
| 7 | Application BE Administrator | The Administrator BE can configure the application back-end. |
| 8 | Backend User Process | This is the process/user that the backend server executes code as and authenticates itself against the database server as. |
| 9 | Database Read User | The database user account used to access the database for read access. |
| 10 | Database Read/Write User | The database user account used to access the database for read and write access. |
| 11 | Database Server Administrator | The database server administrator has read and write permissions and can configure the the database |
| 12 | FileSystem Administrator | The file system administrator has read and write permissions and can configure the filesystem server |
| 13 | FileSystem Read User | The file system user used to access the filesystem with read permissions |
| 14 | FileSystem Read/Write User | The file system user/write used to access the filesystem with read and write permissions |
| 15 | Logging System Read User | The logging system user account used to access loggings for read access. |
| 16 | Logging System Read/Write User | The logging system user account used to access loggings for read and write access. |
| 17 | Logging System Administrator | The logging system server administrator has read and write permissions and can configure the the server |

## Entry Points

|  ID  | Name                                                | Description                                                                                                                                              | Trust Levels |
|:----:|:----------------------------------------------------|:---------------------------------------------------------------------------------------------------------------------------------------------------------|:-----|
|  1   | HTTPS Port                                          | The TruckMotion website will be only be accessible via TLS. All pages within this website are layered on this entry point.                               | 1, 2, 3, 4, 5 |
| 1.1  | Login Page                                          | The login page for all the users of the system.                                                                                                          | 1, 2, 3, 4, 5 |
| 1.2  | Login Function                                      | The login function accepts user supplied credentials and compares them with those in the database.                                                       | 2, 3, 4, 5 |
| 1.3  | Customer Job Requests Status List Page              | The customer job requests status list page lists all the requests that the customer have request, without any interaction and only pagination            | 3, 4 |
| 1.4  | Driver Job Requests Assigned List Page              | The driver job requests assigned list page lists all the drivers job requests with the information needed. This page also does not need user interaction | 3, 5 |
| 1.5  | Customer Adds Locations to his Account              | This page allows user to add Locations to his Account.                                                                                                   | 4 |
| 1.6  | Customer sees the progress of his Requested Service | This page shows the progress of the User's requested services.                                                                                           | 3, 4 |
| 1.7  | Customer request a delivery service.                | This page allows the user to do a delivery request.                                                                                                      | 4 |
| 1.8 | Driver signals the start and end of a delivery      | This page allows the user to signal the start or end of a delivery |3, 5|
| 1.9 | Driver sends a photo or a screenshot to confirm delivery is done | This page allows the user to upload a photo to confirm delivery completion | 3, 5 |
| 1.10  | Manager approves or rejects a service request.      | This page allows the user to approve or reject a service request.                                                                                        | 3 |
| 1.11  | Manager dispatch services to drivers.               | This page allows the user to dispatch one or more services to a certain driver.                                                                          | 3 |
| 1.12 | Manager register drivers.                           | This page allows the user to register drivers.                                                                                                           | 3 |
| 1.13 | Manager register Customers.                         | This page allows the user to register customers.                                                                                                         | 3 |


## External Dependencies

| ID | Description |
| -- | ------------|
| 1 | The TruckMotion application will run on a free Hosting Service at first when it's still a prototype. However, in the future it is intended to deploy the application on a private machine of ours. |
| 2 | The database will be PostGreSQL and will run in a Linux Server. This server will include the installation of the latest operating system and application security patches. |
| 3 | The connection between the application and the database server will be over a private network. |
| 4 | The Backend and Frontend applications will communicate through HTTP requests on a private network. |
| 5 | The application will communicate with an External API for the Geographicals locations. |
| 6 | The application will communicate with the File System. |

## Assets

| ID | Name | Description | Trust Levels |
|:-:|--|--|--|
| **1** |**Users of the System** | **Assets relating to Managers, Drivers and Customers**| |
| 1.1 |Manager Login Details | The login credentials that the managers, drivers and customers will use to log into the TruckMotion application | 3, 8, 9, 10, 11 |
| 1.2 |Driver Login Details | The login credentials that the managers, drivers and customers will use to log into the TruckMotion application | 4, 8, 9, 10, 11 |
| 1.3 |Customer Login Details | The login credentials that the managers, drivers and customers will use to log into the TruckMotion application | 5, 8, 9, 10, 11 |
|1.4| Personal Data |TruckMotion will store personal information relating to Managers, Drivers and customers | 3, 4, 5, 6, 7, 8 , 9, 10, 11 |
| **2** | **System** | **Assets relating to the underlying system** | |
|2.1| Availabitily of TruckMotion Application| The TruckMotion Application should be available 24 hours a day and can be accessed by all managers, drivers and customers | 6, 7, 11 |
|2.2| Availability to Execute Code as a Backend Server | This is the ability to execute source code on the backend server as backend server user | 7, 8 |
|2.3|Availability to Execute SQL as Database Read User | This is the ability to execute SQL select queries on the database, and thus retrieve any information stored within the Truck Motion Database |9, 10, 11 |
|2.3|Availability to Execute SQL as Database Read/Write User | This is the ability to execute SQL select, insert and update queries on the database, and thus have read and write access to any information stored within the TruckMotion database | 10, 11 |
|2.4|Availabiliy to Read Files in FileSystem | This is the ability to read files in the filesystem and thus have read access to files stored inside this server | 12, 13, 14 |
|2.5 | Availability to Read and Write Files in FileSystem | This is the ability to read and write files in the filesystem and thus have read and write access to any information stored inside the filesystem | 12, 14 |
|2.6| Availability to Read logging in the logging system | This is the ability to read loggings in the logging system and thus have read access to this system | 15, 16, 17|
|2.7| Availabilit to Read and Write Loggings | This is the ability to read and write loggins in the logging system and this have read and write access to this system | 16, 17|
| **3** | **Application** | **Assets relating to the Application of TruckMotion** | |
| 3.1| Login Session | This is the login session of a user of the TruckMotion application, this User could be a Driver, Customer or Manager | 2,3,4 |
| 3.2 | Access to the Database Server | Access to the database server allows you to administer the database, giving you full access to the database users and all data contained within the database. | 11 |
| 3.3 | Ability to check information about the Customer Requested Services | The ability to check the data about the customer requested services | 3, 4, 6, 7 |
| 3.4 | Ability to check information about the Driver Assigned Services | The ability to check the data related to the driver assigned services | 3, 4, 6, 7 |
| 3.5 | Access to Audit Data | The audit data shows all audit-able events that occurred within the TruckMotion Application by Managers, Drivers and Customers | 6, 7 |
| 3.6 | Ability to ask for a delivery service | The ability to request that a certain product reaches a certain destination. | 4,6,7 |
| 3.7 | Ability to approve/reject a delivery request | The ability to approve or reject a certain delivery request that dictates the flow of it. | 3,6,7 |
| 3.8 | Ability to to add Locations | The ability to add Locations so that it can be chosen on a service. | 4,6,7 |
| 3.9 | Ability to see the progress of an accepted Service | The ability to see the progress of an accepted Service to have full control of it | 3,6,7 |
| 3.10 | Ability to signal the start and end of a Transport | The ability to signal the start and end of a Transport that has been provided to in order to finish a service | 3,5,6,7 |
| 3.11 | Ability to send a photo or a screenshot of the maps | The ability to send a photo or a screenshot of the maps to confirm a delivery was made | 3,5,6,7 |
| 3.12 | Ability to register Drivers | The ability to register Drivers so that the Transports can be executed by them. | 3,6,7 |
| 3.13 | Ability to register Customers | The ability to register Customers in the application so that new Services can be created. | 3,6,7 |
| 3.14 | Ability to login | The ability to login into the application so that application features are accessible | 3,4,5,6,7 |
| 3.15 | Ability to change a user password | Thea ability to change a password of an user | 3,4,5,6,7 |
    

## Abuse Cases

Abuse cases in detail for the threat model process can be checked in this [file](../AbuseCases/AbuseCases.md).

## STRIDE

### Context

It will be used STRIDE Model to identify the threats. It classifies threats in six categories:

- **S**poofing
- **T**ampering
- **R**epudiation
- **I**nformation Disclosure
- **D**enial of Service
- **E**levation of Privilege

### STRIDE Threat & Mitigation Techniques

| Type | Description | Security Control |
| ---- | ----------- | ---------------- |
| Spoofing | Threat action aimed at accessing and use of another user’s credentials, such as username and password. | <ul><li>Appropriate Authentication</li><li>Protect Secret Data</li><li>Geolocation validation</li></ul> |
| Tampering | Threat action intending to maliciously change or modify persistent data, such as records in a database, and the alteration of data in transit between two computers over an open network, such as the Internet. | <ul><li>Appropriate Authorization</li><li>Input Validations for Injection</li><li>Notify the owner if anything changed</li></ul> |
| Repudiation | Threat action aimed at performing prohibited operations in a system that lacks the ability to trace the operations. | <ul><li>Audit Trails</li></ul> |
| Information Disclosure | Threat action intending to read a file that one was not granted access to, or to read data in transit. | <ul><li>Authorization</li><li>Encryption</li><li>Protect secrets</li><li>Don’t store secrets</li><li>SMS Verification Code</li><li>Robust authentication and authorization verification process</li></ul> |
| Denial of Service | Threat action attempting to deny access to valid users, such as by making a web server temporarily unavailable or unusable. | <ul><li>Appropriate authentication</li><li>Appropriate authorization</li><li>Quality of service</li><li>Traffic Filtering</li><li>Rate Limiting</li></ul> |
| Elevation of privilege | Threat action intending to gain privileged access to resources in order to gain unauthorized access to information or to compromise a system. | <ul><li>Implement Least Privilege</li><li>Logging</li><li>Robust authentication and authorization verification process</li></ul> |

### Data Flow Diagram

The data flow diagram with STRIDE associated is accessible in this [PDF](./threatmodel.pdf).

#### Description DFD

Our application has two actors:
- External Geographical API
   - This is an external API that will be used for the coordinates management and live tracker for the user.
   - This actor is out of scope because it will not be managed by our application. It is not our priority to secure because is external.
- Browser
   - The user will use the browser to interact with our application.

It has two Processes:
- Frontend Application
   - This process contains all the interactions needed with the frontend application. It will render the UI and it will send REST Requests and receive REST Responses from another process, the Backend Application.
   - It reads the frontend application configuration.
- Backend Application
   - This process is responsible to handle requests from the frontend process, handle them as needed and answer them with responses. It will gather data from the file system and from the database to answer the requests. It also writes logging into the logging system for every action made.
   - This process will contain all the business logic. So, it will log all the actions performed by the users.

And finally, it has five Stores:
- Frontend Application Config
   - This store will store the configuration for the frontend process. It will only be read by this process.
   - It is out of scope but it has to be secure because it can contain secrets.
- Backend Application Config
   - This store will store the configuration for the backend process. It will only be read by this process.
   - It is out of scope but it has to be secure because it can contain secrets.
- File System
   - This store represents the file system and it contains files.
   - The backend process can upload files into it and download them from it.
- Database
   - This store contains all the data that the applications asks to persist.
   - The backend application will ask for the data in form of queries and the database will respond with the results.
- Logging System
   - This store contains all the logging of the backend application. It is only accessed by the backend process and it does not send any data to any process.

To see in detail the STRIDE Model Threat for each one of these components, you can read them in the [PDF](./threatmodel.pdf), including descriptions and mitigations.
