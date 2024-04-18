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
| 1.8  | Manager approves or rejects a service request.      | This page allows the user to approve or reject a service request.                                                                                        | 3 |
| 1.9  | Manager dispatch services to drivers.               | This page allows the user to dispatch one or more services to a certain driver.                                                                          | 3 |
| 1.10 | Manager register drivers.                           | This page allows the user to register drivers.                                                                                                           | 3 |
| 1.11 | Manager register Customers.                         | This page allows the user to register customers.                                                                                                         | 3 |

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

## Abuse Cases

### UC3: As a customer, I want to be able to add Locations associated to me so that I can choose one of them in the Service

#### Data Manipulation

##### Description

As an attacker, I modify the details of existing locations associated to customers, such as changing addresses or altering coordinates, leading to incorrect information for the drivers who will conduct the Transport.

##### CVSS Risk Rating

8.1 (High) CVSS:3.0/AV:N/AC:L/PR:L/UI:N/S:U/C:H/I:H/A:N

##### Justification

Data Manipulation can result in customer's services being stolen or not being successfully delivered, that will cause constraints in the Driver's work and Customer's life.

##### Countermeasures

If any information about Customer's locations are changed, a notification is sent. This way, the Customer will know and will have time to report the situation. Also, when delivering the order a code must be shown to the driver in order to validate if it is the correct person to deliver. Another countermeasure could be adding two step confirmation for the changes, for example by Email or SMS.

#### Injection

##### Description

As an attacker, I insert malicious sentences causing Injection or SQL queries into input areas, potentially leading to data breaches or system compromise.

##### CVSS Risk Rating

9.0 (High) CVSS:3.0/AV:N/AC:H/PR:N/UI:N/S:C/C:H/I:H/A:H

##### Justification

Injection can result in many things, such as manipulation or visualization of anything in the Database by SQL Injection. It can make the application execute unintended commands compromising the entire system.

##### Countermeasures

It must be done validations to any input done by Users to check if it is a valid one.

#### Denial of Service (DoS)

##### Description

As an attacker, I use automated scripts to create many locations to multiple customer accounts simultaneously, potentially overloading the system or causing performance issues.

##### CVSS Risk Rating

6.8 (Medium) CVSS:3.0/AV:N/AC:H/PR:N/UI:N/S:C/C:N/I:N/A:H

##### Justification

Making the system unavailable will cause contraints to the Customers that might be expecting deliveries and won't get them because Driver will not have access to the destiny. Also, won't be able to ask for more Delivers.

##### Countermeasures

Implement rate limiting and traffic filtering to mitigate DoS attacks, use redundant systems to maintain service availability, and employ DDoS mitigation services as a proactive measure.

#### Unauthorized Access

##### Description

As an attacker, I gain unauthorized access to the software and add locations to a customer's account without their consent, potentially causing confusion or inconvenience to the customer.

##### CVSS Risk Rating

8.1 (High) CVSS:3.0/AV:N/AC:L/PR:L/UI:N/S:U/C:H/I:H/A:N

##### Justification

Gaining access to adding Locations to a Customer's account besides causing confusion and inconveniences to him, might aswell provide access to the Attacker to other confidential information or systems functionalities.

##### Countermeasures

Implement strict access controls and encryption for user's login information, regularly monitor access logs for suspicious activity, and comply with data protection regulations to safeguard customer information.

### UC4: As a Customer, I want to be able to see the progress of my accepted Service so that I have full control of it

#### Location Spoofing

##### Description

As an Attacker, I manipulate the system providing false or misleading live location updates for the transport associated with their service, causing confusion or leading to incorrect decision-making by the customer.

##### CVSS Risk Rating

5.4 (Medium)

##### Justification

Providing false information about live locations of Transports can mislead the customer to make wrong decisions.

##### Countermeasures

Automatically, notify the customer an estimated time to get his delivery and when it is about 1 hour or 30 minutes away notify aswell. This way, Customer will know that live location is incorrect.

#### Unauthorized Access

##### Justification

As an attacker, I get unauthorized access to the system and intercept the live location of the transport, compromising the privacy and security of the service and endangering the transport.

##### CVSS Risk Rating

8.5 (High) CVSS:3.0/AV:N/AC:L/PR:L/UI:N/S:C/C:H/I:L/A:N

##### Justification

Having access to the live location of a Transport can result in stolen merchandise or break the confidentiality of the Customer's delivery. It can also lead to Ransomware or Extortion situation for the Customer or owners of the application.

##### Countermeasures

Notify the customer whenever his live locations deliveries are asked. Restrict live location of User's deliveries that is logged by asking for a code that only the Customer knows, for example sending this code in SMS for his phone.

#### Denial of Service (DoS)

##### Justification

As an attacker, I flood the system with a high volume of requests for live location updates, overwhelming the system's resources and causing it to become unavailable for legitimate customers who wants to track their services' progress.

##### CVSS Risk Rating

6.3 (Medium) CVSS:3.0/AV:N/AC:H/PR:L/UI:N/S:C/C:N/I:N/A:H

##### Justification

Making the system unavailable will cause contraints to the Customers because they won't be able to see how much time their deliveries will take.

##### Countermeasures

Implement rate limiting and traffic filtering to mitigate DoS attacks, use redundant systems to maintain service availability, and employ DDoS mitigation services as a proactive measure.

#### Phishing Attacks

##### Justification

As an attacker, I send fraudulent messages or emails to customers, with malicious websites or applications designed to steal their credentials or exploit their devices, under the guise of providing access to live location updates.

##### CVSS Risk Rating

8.3 (High) CVSS:3.0/AV:N/AC:L/PR:L/UI:N/S:U/C:H/I:N/A:N

##### Justification

Sending malicious information to the Customer can influence him to access it and allow the Attacker to have his Login Credentials compromising his account. If this Customer have privilege accesses to other Components, all this components will be compromised aswell. Besides the Login credentials, it can give access for the attacker to the application depending on the Malicious malware.

##### Countermeasures

Specify how the information will be given to the customer in order for him to know if something is wrong. Previously, educate users about identifying phishing attempts.

## UC2. As a Customer, I want to be able to see the status of my requested Transport (Pending/Accepted/Rejected/In progress) so that I can have control of it

1. As an attacker, I bypass access control checks by modifying the URL, internal application state, or the HTML page, or simply using a custom API attack tool so that I can access the privilege information about the transport status.
   1. CVSS V3 risk rating: 5.7 CVSS:3.0/AV:N/AC:L/PR:L/UI:R/S:U/C:H/I:N/A:N
   2. Kind of Abuse: Technical
   3. Counter Measure: Add verification for authorization inside the data access.

2. As an attacker and/or malicious user, I can manipulate the primary key so that I can access the transport status of other users.
   1. CVSS V3 risk rating: 5.7 CVSS:3.0/AV:N/AC:L/PR:L/UI:R/S:U/C:H/I:N/A:N
   2. Kind of Abuse: Technical
   3. Counter Measure: Add verification for authorization so that only verified users access the data defined for them.

3. As an attacker, I manipulate sessions, access tokens, or other access controls in the application to act as a user without being logged in, so that I can get access to the information about the transport status.
   1. CVSS V3 risk rating: 5.3 CVSS:3.0/AV:N/AC:H/PR:N/UI:R/S:U/C:H/I:N/A:N;
   2. Kind of Abuse: Technical;
   3. Counter Measure: Make session and access tokens secrets and hardly unaccessible for outside users.

4. As an attacker, I force browsing to transport status page without being authenticated, gaining access to privilege the information about the transport status.
   1. CVSS V3 risk rating: 5.3 CVSS:3.0/AV:N/AC:H/PR:N/UI:R/S:U/C:H/I:N/A:N;
   2. Kind of Abuse: Technical;
   3. Counter Measue: Always verify authentication inside the transport status page.

## UC5. As the Driver, I want to visualize the Transports that have been assigned to me so that I can have control of my work

1. As an attacker, I bypass access control checks by modifying the URL, internal application state, or the HTML page, or simply using a custom API attack tool so that I can access the privilege information about the transports that have been assigned.
   1. CVSS V3 risk rating: 5.7 CVSS:3.0/AV:N/AC:L/PR:L/UI:R/S:U/C:H/I:N/A:N
   2. Kind of Abuse: Technical
   3. Counter Measure: Add verification for authorization inside the data access.

2. As an attacker and/or malicious user, I can manipulate the primary key so that I can access the transport services assigned for other drivers.
   1. CVSS V3 risk rating: 5.7 CVSS:3.0/AV:N/AC:L/PR:L/UI:R/S:U/C:H/I:N/A:N
   2. Kind of Abuse: Technical
   3. Counter Measure: Add verification for authorization so that only verified users access the data defined for them.

3. As an attacker, I manipulate sessions, access tokens, or other access controls in the application to act as a user without being logged in, so that I can get access to the information about all the drivers transport jobs assigned.
   1. CVSS V3 risk rating: 5.3 CVSS:3.0/AV:N/AC:H/PR:N/UI:R/S:U/C:H/I:N/A:N;
   2. Kind of Abuse: Technical;
   3. Counter Measure: Make session and access tokens secrets and hardly unaccessible for outside users.

4. As an attacker, I force browsing to transport status page without being authenticated, gaining access to privilege the information about the transport jobs assigned to the drivers.
   1. CVSS V3 risk rating: 5.3 CVSS:3.0/AV:N/AC:H/PR:N/UI:R/S:U/C:H/I:N/A:N;
   2. Kind of Abuse: Technical;
   3. Counter Measue: Always verify authentication inside the transport jobs page.

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
   - This process contains all the interactions needed with the frontend application. It will have the renderization of the UI and it will send REST Requests and receive REST Responses from another process, the Backend Application.
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
