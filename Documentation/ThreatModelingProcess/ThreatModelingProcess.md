# Threat Modeling Process

## Trust Levels

| ID | Name | Description |
|:----:|:------|:---------|
| 1 | Anonymous Web User | A user who has connected to TruckMotion but has not provided valid credentials. |
| 2 | User with Invalid Credentials | A user who has connected to TruckMotion and is attempting to log in using invalid login credentials. |
| 3 | Manager | Managers can register clients and drivers, approve or reject created jobs by the clients and assign jobs for the drivers. |
| 4 | Clients | Clients can create jobs requests and see all the information directly involved to their jobs. |
| 5 | Drivers | Drivers can see all the jobs assigned to them, start and finish those jobs. |
| 6 | Application FE Administrator | The Administrator FE can configure the application front-end. |
| 7 | Application BE Administrator | The Administrator BE can configure the application back-end. |
| 8 | Backend User Process | This is the process/user that the backend server executes code as and authenticates itself against the database server as. |
| 9 | Database Read User | The database user account used to access the database for read access. |
| 10 | Database Read/Write User | The database user account used to acces the database for read and write access. |

## Entry Points

| ID | Name | Description | Trust Levels |
|:----:|:-------|:-----|:-----|
| 1 | HTTPS Port | The TruckMotion website will be only be accessible via TLS. All pages within this website are layered on this entry point. | 1, 2, 3, 4, 5 |
| 1.1 | Login Page | The login page for all the users of the system. | 1, 2, 3, 4, 5 |
| 1.2 | Login Function | The login function accepts user supplied credentials and compares them with those in the database. | 2, 3, 4, 5 |
| 1.3 | Customer Job Requests Status List Page | The customer job requests status list page lists all the requests that the customer have request, without any interaction and only pagination | 4 |
| 1.4 | Driver Job Requests Assigned List Page | The driver job requests assigned list page lists all the drivers job requests with the information needed. This page also does not need user interaction | 5 |
