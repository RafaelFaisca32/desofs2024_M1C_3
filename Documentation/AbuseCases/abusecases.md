# UC1-As a Customer, I want to be able to register delivery services so that I can have the parcel delivered to my company/address/place.

## Location Spoofing: 

### Description:
Attackers could manipulate the location data provided during the registration process to redirect deliveries to a different address, potentially their own. This could result in theft or tampering of packages.

### CVSS Risk Rating: 8.5 (High) 

### Justification: 
Location spoofing can redirect deliveries to unauthorized addresses, leading to theft or tampering of packages.
### Countermeasures: 
Implement geolocation validation during registration to detect anomalies, use secure communication protocols to prevent location manipulation, and provide customers with delivery tracking and verification mechanisms.

## Man-in-the-Middle Attack: 
### Description:
During the registration process, if the communication channel between the customer and the delivery service provider is compromised, attackers could intercept the registration data, modify it, and redirect deliveries to an unauthorized location.

### CVSS Risk Rating: 8.0 (High)
### Justification: 
MitM attacks during registration can intercept and modify registration data, leading to unauthorized redirection of deliveries.
### Countermeasures: 
Implement HTTPS and certificate pinning to secure communication channels, regularly update security protocols to mitigate known vulnerabilities, and educate users about the risks of using unsecured networks.

## Cross-Site Scripting (XSS): 

### Description : 
Attackers may inject malicious scripts into the delivery service registration page, potentially compromising the security of other users' data or redirecting deliveries to unintended locations.

### CVSS Risk Rating: 7.5 (High)

### Justification: 
XSS attacks can compromise the security of delivery service registration pages, potentially redirecting deliveries or compromising user data.
### Countermeasures: 
Implement input sanitization to prevent XSS vulnerabilities, employ web application firewalls (WAFs) to detect and block malicious scripts, and regularly update web application frameworks and libraries.

## Denial of Service (DoS): 

### Description
Attackers may flood the delivery service provider's registration system with a high volume of fake registration requests, causing service disruption or preventing legitimate customers from registering delivery services.

### CVSS Risk Rating: 7.0 (High)
### Justification: 
DoS attacks can disrupt delivery service registration systems, preventing legitimate customers from registering or modifying delivery services.
### Countermeasures: 
Implement rate limiting and traffic filtering to mitigate DoS attacks, use scalable infrastructure to handle increased traffic loads, and employ DDoS mitigation services for additional protection.

## Unauthorized Access to Delivery Records: 
Attackers may attempt to gain unauthorized access to the delivery service provider's database or systems to retrieve sensitive information about past deliveries, including customer addresses and delivery contents, for malicious purposes such as identity theft or targeted theft of valuable goods.

### CVSS Risk Rating: 8.5 (High)

### Justification: 
Unauthorized access to delivery records can lead to identity theft or targeted theft of valuable goods by revealing sensitive information about past deliveries.
### Countermeasures: 
Implement strict access controls and encryption for delivery records, regularly monitor access logs for suspicious activity, and comply with data protection regulations to safeguard customer information.

# UC3-As a customer, I want to be able to add Locations associated to me so that I can choose one of them in the Service.

## Data Manipulation

### Description:
As an attacker, I modify the details of existing locations associated to customers, such as changing addresses or altering coordinates, leading to incorrect information for the drivers who will conduct the Transport.

### CVSS Risk Rating: 8.1 (High) CVSS:3.0/AV:N/AC:L/PR:L/UI:N/S:U/C:H/I:H/A:N

### Justification: 
Data Manipulation can result in customer's services being stolen or not being successfully delivered, that will cause constraints in the Driver's work and Customer's life.
### Countermeasures: 
If any information about Customer's locations are changed, a notification is sent. This way, the Customer will know and will have time to report the situation. Also, when delivering the order a code must be shown to the driver in order to validate if it is the correct person to deliver.

## Injection

### Description:
As an attacker, I insert malicious sentences causing Injection or SQL queries into input areas, potentially leading to data breaches or system compromise.

### CVSS Risk Rating: 9.0 (High) CVSS:3.0/AV:N/AC:H/PR:N/UI:N/S:C/C:H/I:H/A:H

### Justification: 
Injection can result in many things, such as manipulation or visualization of anything in the Database by SQL Injection. It can make the application execute unintended commands compromising the entire system.
### Countermeasures: 
It must be done validations to any input done by Users to check if it is a valid one.

## Denial of Service (DoS)

### Description
As an attacker, I use automated scripts to create many locations to multiple customer accounts simultaneously, potentially overloading the system or causing performance issues.

### CVSS Risk Rating: 6.8 (Medium) CVSS:3.0/AV:N/AC:H/PR:N/UI:N/S:C/C:N/I:N/A:H

### Justification:
Making the system unavailable will cause contraints to the Customers that might be expecting deliveries and won't get them because Driver will not have access to the destiny. Also, won't be able to ask for more Delivers.
### Countermeasures:
Implement rate limiting and traffic filtering to mitigate DoS attacks, use redundant systems to maintain service availability, and employ DDoS mitigation services as a proactive measure.

## Unauthorized Access

### Justification:
As an attacker, I gain unauthorized access to the software and add locations to a customer's account without their consent, potentially causing confusion or inconvenience to the customer.

### CVSS Risk Rating: 8.1 (High) CVSS:3.0/AV:N/AC:L/PR:L/UI:N/S:U/C:H/I:H/A:N

### Justification:
Gaining access to adding Locations to a Customer's account besides causing confusion and inconveniences to him, might aswell provide access to the Attacker to other confidential information or systems functionalities.
### Countermeasures:
Implement strict access controls and encryption for user's login information, regularly monitor access logs for suspicious activity, and comply with data protection regulations to safeguard customer information.

# UC4- As a Customer, I want to be able to see the progress of my accepted Service so that I have full control of it.

## Location Spoofing

### Description
As an Attacker, I manipulate the system providing false or misleading live location updates for the transport associated with their service, causing confusion or leading to incorrect decision-making by the customer.

### CVSS Risk Rating: 5.4 (Medium) 

### Justification:
Providing false information about live locations of Transports can mislead the customer to make wrong decisions.
### Countermeasures:
Automatically, notify the customer an estimated time to get his delivery and when it is about 1 hour or 30 minutes away notify aswell. This way, Customer will know that live location is incorrect.

## Unauthorized Access

### Justification:
As an attacker, I get unauthorized access to the system and intercept the live location of the transport, compromising the privacy and security of the service and endangering the transport.

### CVSS Risk Rating: 8.5 (High) CVSS:3.0/AV:N/AC:L/PR:L/UI:N/S:C/C:H/I:L/A:N

### Justification:
Having access to the live location of a Transport can result in stolen merchandise or break the confidentiality of the Customer's delivery. It can also lead to Ransomware or Extortion situation for the Customer or owners of the application.
### Countermeasures:
Notify the customer whenever his live locations deliveries are asked. Restrict live location of User's deliveries that is logged by asking for a code that only the Customer knows, for example sending this code in SMS for his phone.

## Denial of Service (DoS)

### Justification:
As an attacker, I flood the system with a high volume of requests for live location updates, overwhelming the system's resources and causing it to become unavailable for legitimate customers who wants to track their services' progress.

### CVSS Risk Rating: 6.3 (Medium) CVSS:3.0/AV:N/AC:H/PR:L/UI:N/S:C/C:N/I:N/A:H

### Justification:
Making the system unavailable will cause contraints to the Customers because they won't be able to see how much time their deliveries will take.
### Countermeasures:
Implement rate limiting and traffic filtering to mitigate DoS attacks, use redundant systems to maintain service availability, and employ DDoS mitigation services as a proactive measure.

## Phishing Attacks

### Justification:
As an attacker, I send fraudulent messages or emails to customers, with malicious websites or applications designed to steal their credentials or exploit their devices, under the guise of providing access to live location updates.

### CVSS Risk Rating: 8.3 (High) CVSS:3.0/AV:N/AC:L/PR:L/UI:N/S:U/C:H/I:N/A:N

### Justification:
Sending malicious information to the Customer can influence him to access it and allow the Attacker to have his Login Credentials compromising his account. If this Customer have privilege accesses to other Components, all this components will be compromised aswell. Besides the Login credentials, it can give access for the attacker to the application depending on the Malicious malware.
### Countermeasures:
Specify how the information will be given to the customer in order for him to know if something is wrong. Previously, educate users about identifying phishing attempts.

# UC6 - As the Driver, I want to signal the start and end of a Transport that has been provided by me so that I can finish my Service.

## False Start Signal:

### Description:
The driver signals the start of a transport service, but instead of actually starting the service, they deceive the system by activating the signal without intending to provide the service.

### CVSS Risk Rating: 5.7 (Medium) CVSS:3.0/AV:N/AC:L/PR:L/UI:R/S:U/C:N/I:H/A:N

### Justification:
This could be done to manipulate the system for personal gain, such as receiving compensation for services not rendered or to create a false record of activity.

### Countermeasures:
Providing training and awareness programs so that drivers don't do this issue.
Be restrictive when employing new drivers.
If the gps tracker shows that the truck has been stopped for a long time, make the driver provide an explanation for that specific situation.

# UC7 - As a driver, I want to be able to prove my delivery with a photo or a screenshot of the maps so that my job execution is accepted.

## Photo manipulation

### Description:
The driver could use pre-existing photos from their gallery or the internet and pass them off as proof of delivery or instead of genuinely delivering the package or completing the service, they may provide a photo taken at a different location.

### CVSS Risk Rating: 5.7 (Medium) CVSS:3.0/AV:N/AC:L/PR:L/UI:R/S:U/C:N/I:H/A:N

### Justification:
By fabricating proof of delivery, the driver can save time that would have been spent on actually completing the delivery. This time can then be used for personal activities or for taking on additional delivery jobs, thereby increasing potential earnings.

### Countermeasures:
Providing training and awareness programs for drivers about the importance of accurate reporting and the consequences of providing false proof of delivery can deter misconduct.
Implementing strict disciplinary measures and consequences for drivers caught providing false proof of delivery.
Implementing geofencing technology can help enforce delivery boundaries, ensuring that deliveries are made within designated areas.

# UC8- As a manager, I want to be able to approve or reject a job so that I can control the services.

## Forged Approvals: 

### Description: 
An attacker gains access to the approval system and forges approvals for jobs that were not actually reviewed by the manager. This could result in unauthorized services being provided or resources being allocated improperly.

### CVSS Risk Rating: 8.0 (High)

### Justification: 
Forged approvals can result in unauthorized services being provided or resources being allocated improperly.
### Countermeasures: 
Implement approval workflows with multiple layers of verification, provide training on recognizing fraudulent requests, and implement auditing mechanisms to detect suspicious activity.

## Denial of Service (DoS): 

### Description:
Attackers flood the approval system with a high volume of fake job approval requests, causing service disruption or preventing legitimate job approvals from being processed.

### CVSS Risk Rating: 7.0 (High)

### Justification: 
DoS attacks can disrupt the job approval system, preventing legitimate approvals from being processed.
### Countermeasures: 
Implement rate limiting and traffic filtering to mitigate DoS attacks, use redundant systems to maintain service availability, and employ DDoS mitigation services as a proactive measure.

## Malware Attack: 

### Description: 
Malware infects the manager's device, allowing attackers to manipulate job approvals or steal sensitive information related to job details or clients.

### CVSS Risk Rating: 8.0 (High)

### Justification: 
Malware infections can compromise the manager's device, allowing attackers to manipulate job approvals or steal sensitive information.
### Countermeasures: 
Implement endpoint security solutions, conduct regular malware scans and updates, and provide training on recognizing and avoiding malware threats.

## Man-in-the-Middle (MitM) Attack:

### Description: 
Attackers intercept communication between the manager and the approval system, allowing them to manipulate job approval requests or intercept sensitive information.

### CVSS Risk Rating: 8.0 (High)
### Justification: 
MitM attacks can intercept and manipulate job approval requests, leading to unauthorized changes in service delivery.
### Countermeasures: 
Implement encryption protocols like TLS/SSL to secure communication channels, use digital signatures to verify the integrity of approval requests, and educate users about the risks of unsecured networks.

## SQL Injection: 

### Description: 
Attackers exploit vulnerabilities in the approval system's database to manipulate job approval records or gain unauthorized access to sensitive information.

### CVSS Risk Rating: 8.5 (High)
### Justification: 
SQL injection attacks can manipulate job approval records or gain unauthorized access to sensitive information.
### Countermeasures: 
Implement input validation and parameterized queries to prevent SQL injection, conduct regular security assessments of the approval system, and enforce strict database access controls.



# UC12 - As a user of the system, I want to be able to login into the application so that I can access the application features.

## Brute Force Attacks:

### Description
An attacker attempts to gain unauthorized access to user accounts by repeatedly guessing usernames and passwords.
This could be done manually or using automated scripts or tools to systematically try different combinations until a valid login is found.

### CVSS Risk Rating: 7.5 (High)
### Justification: 
Brute force attacks can lead to unauthorized access to user accounts, potentially compromising sensitive information.
### Countermeasures: 
Implement account lockout mechanisms after a certain number of failed login attempts, enforce strong password policies, and employ CAPTCHA or multi-factor authentication to mitigate brute force attacks.

## Credential Stuffing:

### Description
Attackers use previously leaked username/password pairs from other breaches and attempt to log in with those credentials on your application.
Since users often reuse passwords across multiple services, this can lead to successful unauthorized access.

### CVSS Risk Rating: 8.0 (High)
### Justification: 
Credential stuffing exploits users' tendencies to reuse passwords across multiple services, potentially granting unauthorized access to accounts.
### Countermeasures: 
Encourage users to use unique passwords for each service, implement multi-factor authentication, and regularly monitor for suspicious login attempts or patterns.

## Phishing Attacks:

### Description
Attackers send deceptive emails or messages pretending to be from the application, tricking users into providing their login credentials on a fake login page.
Once the user submits their credentials, the attacker can use them to access the legitimate application.

### CVSS Risk Rating: 9.0 (Critical)

### Justification: 
Phishing attacks can deceive users into providing their login credentials, compromising their accounts and potentially leading to further exploitation.
### Countermeasures: 
Educate users about identifying phishing attempts, implement email filtering to detect and block phishing emails, and use domain validation techniques to verify legitimate login pages.

## Session Hijacking:

### Description
Attackers intercept or steal a valid session token or cookie after a user successfully logs in.
With the stolen session token, the attacker can then impersonate the logged-in user without needing to know their credentials.

### CVSS Risk Rating: 8.5 (High)


### Justification: 
Session hijacking can result in unauthorized access to user accounts and sensitive information without requiring knowledge of login credentials.
### Countermeasures: 
Implement secure session management techniques such as using HTTPS, using secure cookies with HttpOnly and Secure flags, and regularly rotating session tokens.

## Man-in-the-Middle (MitM) Attacks:

### Description
Attackers intercept communication between the user and the application during the login process.
They can eavesdrop on the login credentials being transmitted, potentially capturing sensitive information like usernames and passwords.

### CVSS Risk Rating: 8.0 (High)

### Justification: 
MitM attacks during login can intercept sensitive information like usernames and passwords, leading to unauthorized access to accounts.

### Countermeasures: 
Use encryption protocols like TLS/SSL to secure communication channels, implement certificate pinning, and educate users about connecting to trusted networks

## Cross-Site Scripting (XSS) via SQL Injection:

### Description
If the application displays database content retrieved via SQL injection in user-accessible pages without proper sanitization, it can lead to XSS vulnerabilities.
Attackers can inject malicious scripts into the database, which are then executed in the context of other users' sessions when the data is displayed in the application.

### CVSS Risk Rating: 9.0 (Critical)

### Justification: 
XSS via SQL injection can lead to the execution of malicious scripts in the context of other users' sessions, compromising the security of the application and user data.
### Countermeasures: 
Implement input validation and parameterized queries to prevent SQL injection, sanitize user input to mitigate XSS vulnerabilities, and regularly patch and update application frameworks and libraries.


