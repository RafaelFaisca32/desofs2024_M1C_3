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
| 14 | FileSystem Read/Wrote User | The file system user/write used to access the filesystem with read and write permissions |

## Entry Points

| ID | Name | Description | Trust Levels |
|:----:|:-------|:-----|:-----|
| 1 | HTTPS Port | The TruckMotion website will be only be accessible via TLS. All pages within this website are layered on this entry point. | 1, 2, 3, 4, 5 |
| 1.1 | Login Page | The login page for all the users of the system. | 1, 2, 3, 4, 5 |
| 1.2 | Login Function | The login function accepts user supplied credentials and compares them with those in the database. | 2, 3, 4, 5 |
| 1.3 | Customer Job Requests Status List Page | The customer job requests status list page lists all the requests that the customer have request, without any interaction and only pagination | 3, 4 |
| 1.4 | Driver Job Requests Assigned List Page | The driver job requests assigned list page lists all the drivers job requests with the information needed. This page also does not need user interaction | 3, 5 |

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
|2.5 | Availability to Read and Write Files in FileSystem | This is the ability to read and write files in the filesystem and this have read and write access to any information stored inside the filesystem | 12, 14 |
| **3** | **Application** | **Assets relating to the Application of TruckMotion** | |
| 3.1| Login Session | This is the login session of a user of the TruckMotion application, this User could be a Driver, Customer or Manager | 2,3,4 |
| 3.2 | Access to the Database Server | Access to the database server allows you to administer the database, giving you full access to the database users and all data contained within the database. | 11 |
| 3.3 | Ability to check information about the Customer Requested Services | The ability to check the data about the customer requested services | 3, 4, 6, 7 |
| 3.4 | Ability to check information about the Driver Assigned Services | The ability to check the data related to the driver assigned services | 3, 4, 6, 7 |
| 3.5 | Access to Audit Data | The audit data shows all audit-able events that occurred within the TruckMotion Application by Managers, Drivers and Customers | 6, 7 |
