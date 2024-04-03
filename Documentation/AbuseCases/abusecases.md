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


