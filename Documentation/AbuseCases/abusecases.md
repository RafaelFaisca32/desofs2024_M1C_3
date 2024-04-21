# Abuse Cases

## UC1. As a Customer, I want to be able to register delivery services so that I can have the parcel delivered to my company/address/place

1. Attackers could manipulate the location data provided during the registration process to redirect deliveries to a different address, potentially their own. This could result in theft or tampering of packages.
   1. **CVSS Risk Rating:**
      - 7.3 (High) CVSS:3.0/AV:N/AC:H/PR:H/UI:R/S:C/C:H/I:H/A:N
   2. **Justification:**
      - Location spoofing can redirect deliveries to unauthorized addresses, leading to theft or tampering of packages.
   3. **Kind of Abuse:**
      - Business
   4. **Countermeasures:**
      - Implement geolocation validation during registration to detect anomalies.
      - Use secure communication protocols to prevent location manipulation.
      - Provide customers with delivery tracking and verification mechanisms.

2. During the registration process, if the communication channel between the customer and the delivery service provider is compromised, attackers could intercept the registration data, modify it, and redirect deliveries to an unauthorized location.
   1. **CVSS Risk Rating:**
      - 8.7 (High) CVSS:3.0/AV:N/AC:H/PR:N/UI:N/S:C/C:H/I:H/A:N
   2. **Justification:**
      - Man in the Middle attacks during registration can intercept and modify registration data, leading to unauthorized redirection of deliveries.
   3. **Kind of Abuse:**
      - Business
   4. **Countermeasures:**
      - Implement HTTPS and certificate pinning to secure communication channels.
      - Regularly update security protocols to mitigate known vulnerabilities.
      - Educate users about the risks of using unsecured networks.

3. Attackers may inject malicious scripts into the delivery service registration page, potentially compromising the security of other users' data, redirecting deliveries to unintended locations or trigger denial of service (DoS).
   1. **CVSS Risk Rating:**
      - 7.3 (High) CVSS:3.0/AV:N/AC:H/PR:H/UI:R/S:C/C:H/I:H/A:N
   2. **Justification:**
      - XSS attacks can compromise the security of delivery service registration pages, potentially redirecting deliveries or compromising user data.
   3. **Kind of Abuse:** 
      - Technical and Business
   4. **Countermeasures:**
      - Implement input sanitization to prevent XSS vulnerabilities
      - Employ web application firewalls (WAFs) to detect and block malicious scripts
      - Regularly update web application frameworks and libraries.

4. Attackers may flood the delivery service provider's registration system with a high volume of fake registration requests, causing service disruption or preventing legitimate customers from registering delivery services.
   1. **CVSS Risk Rating:**
      - 5.9 (Medium)CVSS:3.0/AV:N/AC:H/PR:N/UI:N/S:U/C:N/I:N/A:H
   2. **Justification:**
      - DoS attacks can disrupt delivery service registration systems, preventing legitimate customers from registering or modifying delivery services.
   3. **Kind of Abuse:**
      - Technical
   4. **Countermeasures:**
      - Implement rate limiting and traffic filtering to mitigate DoS attacks
      - Use scalable infrastructure to handle increased traffic loads
      - Employ DDoS mitigation services for additional protection.

5. Attackers may attempt to gain unauthorized access to the delivery service provider's database or systems to retrieve sensitive information about past deliveries, including customer addresses and delivery contents, for malicious purposes such as identity theft or targeted theft of valuable goods.
   1. **CVSS Risk Rating:**
      - 8.7 (High) CVSS:3.0/AV:N/AC:H/PR:N/UI:N/S:C/C:H/I:H/A:N
   2. **Justification:**
      - Unauthorized access to delivery records can lead to identity theft or targeted theft of valuable goods by revealing sensitive information about past deliveries.
   3. **Kind of Abuse:**
      - Business
   4. **Countermeasures:**
      - Implement strict access controls and encryption for delivery records
      - Regularly monitor access logs for suspicious activity
      - Comply with data protection regulations to safeguard customer information.

## UC2. As a Customer, I want to be able to see the status of my requested Transport (Pending/Accepted/Rejected/In progress) so that I can have control of it

1. As an attacker, I bypass access control checks by modifying the URL, internal application state, or the HTML page, or simply using a custom API attack tool so that I can access the privilege information about the transport status.
   1. **CVSS V3 risk rating:**
      - 5.7 CVSS:3.0/AV:N/AC:L/PR:L/UI:R/S:U/C:H/I:N/A:N
   2. **Kind of Abuse:**
      - Business
   3. **Countermeasures:**
      - Add verification for authorization inside the data access.

2. As an attacker and/or malicious user, I can manipulate the primary key so that I can access the transport status of other users.
   1. **CVSS V3 risk rating:**
      - 5.7 CVSS:3.0/AV:N/AC:L/PR:L/UI:R/S:U/C:H/I:N/A:N
   2. **Kind of Abuse:**
      - Business
   3. **Countermeasures:**
      - Add verification for authorization so that only verified users access the data defined for them.

3. As an attacker, I manipulate sessions, access tokens, or other access controls in the application to act as a user without being logged in, so that I can get access to the information about the transport status.
   1. **CVSS V3 risk rating:**
      - 5.3 CVSS:3.0/AV:N/AC:H/PR:N/UI:R/S:U/C:H/I:N/A:N;
   2. **Kind of Abuse:**
      - Business
   3. **Countermeasures:**
      - Make session and access tokens secrets and hardly unaccessible for outside users. Always check users tokens for data, not only their role.

4. As an attacker, I force browsing to transport status page without being authenticated, gaining access to privilege the information about the transport status.
   1. **CVSS V3 risk rating:**
      - 5.3 CVSS:3.0/AV:N/AC:H/PR:N/UI:R/S:U/C:H/I:N/A:N
   2. **Kind of Abuse:**
      - Business
   3. **Countermeasures:**
      - Always verify authentication inside the transport status page.

## UC3. As a customer, I want to be able to add Locations associated to me so that I can choose one of them in the Service

1. As an attacker, I modify the details of existing locations associated to customers, such as changing addresses or altering coordinates, leading to incorrect information for the drivers who will conduct the Transport.
   1. **CVSS Risk Rating:**
      - 8.1 (High) CVSS:3.0/AV:N/AC:L/PR:L/UI:N/S:U/C:H/I:H/A:N
   2. **Justification:**
      - Data Manipulation can result in customer's services being stolen or not being successfully delivered, that will cause constraints in the Driver's work and Customer's life.
   3. **Kind of Abuse:**
      - Business
   4. **Countermeasures:**
      - If any information about Customer's locations are changed, a notification is sent. This way, the Customer will know and will have time to report the situation.
      - When delivering the order a code must be shown to the driver in order to validate if it is the correct person to deliver.

2. As an attacker, I insert malicious sentences causing Injection or SQL queries into input areas, potentially leading to data breaches or system compromise.
   1. **CVSS Risk Rating:** 9.0 (Critical) CVSS:3.0/AV:N/AC:H/PR:N/UI:N/S:C/C:H/I:H/A:H
   2. **Justification:**
      - Injection can result in many things, such as manipulation or visualization of anything in the Database by SQL Injection. It can make the application execute unintended commands compromising the entire system.
   3. **Kind of Abuse:**
      - Technical and Business.
   4. **Countermeasures:**
      - It must be done validations to any input done by Users to check if it is a valid one.

3. As an attacker, I use automated scripts to create many locations to multiple customer accounts simultaneously, potentially overloading the system or causing performance issues.
   1. **CVSS Risk Rating:**
      - 5.9 (Medium)CVSS:3.0/AV:N/AC:H/PR:N/UI:N/S:U/C:N/I:N/A:H
   2. **Justification:**
      - Making the system unavailable will cause contraints to the Customers that might be expecting deliveries and won't get them because Driver will not have access to the destiny. Also, won't be able to ask for more Delivers.
   3. **Kind of Abuse:**
      - Technical
   4. **Countermeasures:**
      - Implement rate limiting and traffic filtering to mitigate DoS attacks
      - Use redundant systems to maintain service availability
      - Employ DDoS mitigation services as a proactive measure.

4. As an attacker, I gain unauthorized access to the software and add locations to a customer's account without their consent, potentially causing confusion or inconvenience to the customer.
   1. **CVSS Risk Rating:**
      - 8.1 (High) CVSS:3.0/AV:N/AC:L/PR:L/UI:N/S:U/C:H/I:H/A:N
   2. **Justification:**
      - Gaining access to adding Locations to a Customer's account besides causing confusion and inconveniences to him, might aswell provide access to the Attacker to other confidential information or systems functionalities.
   3. **Kind of Abuse:**
      - Business
   4. **Countermeasures:**
      - Implement strict access controls and encryption for user's login information
      - Regularly monitor access logs for suspicious activity
      - Comply with data protection regulations to safeguard customer information.

## UC4. As a Customer, I want to be able to see the progress of my accepted Service so that I have full control of it

1. As an Attacker, I manipulate the system providing false or misleading live location updates for the transport associated with their service, causing confusion or leading to incorrect decision-making by the customer.
   1. **CVSS Risk Rating:**
      - 5.4 (Medium) CVSS:3.0/AV:N/AC:L/PR:L/UI:R/S:C/C:L/I:L/A:N
   2. **Justification:**
      - Providing false information about live locations of Transports can mislead the customer to make wrong decisions.
   3. **Kind of Abuse:**
      - Business
   4. **Countermeasures:**
      - Automatically, notify the customer an estimated time to get his delivery and when it is about 1 hour or 30 minutes away notify aswell. This way, Customer will know that live location is incorrect.

2. As an attacker, I get unauthorized access to the system and intercept the live location of the transport, compromising the privacy and security of the service and endangering the transport.
   1. **CVSS Risk Rating:**
      - 8.5 (High) CVSS:3.0/AV:N/AC:L/PR:L/UI:N/S:C/C:H/I:L/A:N
   2. **Justification:**
      - Having access to the live location of a Transport can result in stolen merchandise or break the confidentiality of the Customer's delivery. It can also lead to Ransomware or Extortion situation for the Customer or owners of the application.
   3. **Kind of Abuse:**
      - Business
   4. **Countermeasures:**
      - Notify the customer whenever his live locations deliveries are asked.
      - Restrict live location of User's deliveries that is logged by asking for a code that only the Customer knows, for example sending this code in SMS for his phone.

3. As an attacker, I flood the system with a high volume of requests for live location updates, overwhelming the system's resources and causing it to become unavailable for legitimate customers who wants to track their services' progress.
   1. **CVSS Risk Rating:**
      - 5.9 (Medium)CVSS:3.0/AV:N/AC:H/PR:N/UI:N/S:U/C:N/I:N/A:H
   2. **Justification:**
      - Making the system unavailable will cause contraints to the Customers because they won't be able to see how much time their deliveries will take.
   3. **Kind of Abuse:**
      - Technical
   4. **Countermeasures:**
      - Implement rate limiting and traffic filtering to mitigate DoS attacks.
      - Use redundant systems to maintain service availability.
      - Employ DDoS mitigation services as a proactive measure.

4. As an attacker, I send fraudulent messages or emails to customers, with malicious websites or applications designed to steal their credentials or exploit their devices, under the guise of providing access to live location updates.
   1. **CVSS Risk Rating:**
      - 8.3 (High) CVSS:3.0/AV:N/AC:L/PR:L/UI:N/S:U/C:H/I:N/A:N
   2. **Justification:**
      - Sending malicious information to the Customer can influence him to access it and allow the Attacker to have his Login Credentials compromising his account. If this Customer have privilege accesses to other Components, all this components will be compromised aswell. Besides the Login credentials, it can give access for the attacker to the application depending on the Malicious malware.
   3. **Kind of Abuse:**
      - Business
   4. **Countermeasures:**
      - Specify how the information will be given to the customer in order for him to know if something is wrong.
      - Previously, educate users about identifying phishing attempts.

## UC5. As the Driver, I want to visualize the Transports that have been assigned to me so that I can have control of my work

1. As an attacker, I bypass access control checks by modifying the URL, internal application state, or the HTML page, or simply using a custom API attack tool so that I can access the privilege information about the transports that have been assigned.
   1. **CVSS V3 risk rating:**
      - 5.7 CVSS:3.0/AV:N/AC:L/PR:L/UI:R/S:U/C:H/I:N/A:N
   2. **Kind of Abuse:**
      - Business
   3. **Countermeasures:**
      - Add verification for authorization inside the data access.

2. As an attacker and/or malicious user, I can manipulate the primary key so that I can access the transport services assigned for other drivers.
   1. **CVSS V3 risk rating:**
      - 5.7 CVSS:3.0/AV:N/AC:L/PR:L/UI:R/S:U/C:H/I:N/A:N
   2. **Kind of Abuse:**
      - Business
   3. **Countermeasures:**
      - Add verification for authorization so that only verified users access the data defined for them.

3. As an attacker, I manipulate sessions, access tokens, or other access controls in the application to act as a user without being logged in, so that I can get access to the information about all the drivers transport jobs assigned.
   1. **CVSS V3 risk rating:**
      - 5.3 CVSS:3.0/AV:N/AC:H/PR:N/UI:R/S:U/C:H/I:N/A:N
   2. **Kind of Abuse:**
      - Business
   3. **Countermeasures:**
      - Make session and access tokens secrets and hardly unaccessible for outside users.
      - Always check users tokens for data, not only their role.

4. As an attacker, I force browsing to transport status page without being authenticated, gaining access to privilege the information about the transport jobs assigned to the drivers.
   1. **CVSS V3 risk rating:**
      - 5.3 CVSS:3.0/AV:N/AC:H/PR:N/UI:R/S:U/C:H/I:N/A:N
   2. **Kind of Abuse:**
      - Business
   3. **Countermeasres:**
      - Always verify authentication inside the transport jobs page.

## UC6. As the Driver, I want to signal the start and end of a Transport that has been provided to me so that I can finish my Service

1. The driver signals the start of a transport service, but instead of actually starting the service, they deceive the system by activating the signal without intending to provide the service.
   1. **CVSS Risk Rating:**
      - 5.7 (Medium) CVSS:3.0/AV:N/AC:L/PR:L/UI:R/S:U/C:N/I:H/A:N
   2. **Justification:**
      - This could be done to manipulate the system for personal gain, such as receiving compensation for services not rendered or to create a false record of activity.
   3. **Kind of Abuse:**
      - Business
   4. **Countermeasures:**
      - This could be done to manipulate the system for personal gain, such as receiving compensation for services not rendered or to create a false record of activity.

2. An attacker impersonates a driver to fake signaling.
   1. **CVSS Risk Rating:**
      - 7.3 (High) CVSS:3.0/AV:N/AC:H/PR:H/UI:R/S:C/C:H/I:H/A:N
   2. **Justification:**
      - Impersonation can lead to unauthorized signaling creating problems for deliveries.
   3. **Kind of Abuse:**
      - Business
   4. **Countermeasures:**
      - Implement strong authentication mechanisms for drivers.
      - Conduct regular verification checks for all drivers.

3. An attacker/driver spoofs the location of his truck, making it seem like they are in a different location than they actually are. This could lead to misallocation of resources or fraudulent activity.
   1. **CVSS Risk Rating:**
      - 6.2 (Medium) CVSS:3.0/AV:N/AC:H/PR:H/UI:R/S:C/C:L/I:H/A:N
   2. **Justification:**
      - Spoofed location data can result in incorrect resource allocation or fraudulent activities.
   3. **Kind of Abuse:**
      - Business
   4. **Countermeasures:**
      - Use secure geolocation services with validation checks.
      - Employ GPS tracking with tamper-proof mechanisms.
      - Educate drivers on the importance of verifying their assigned locations.

4. Attackers flood the signaling system with a high volume of fake signals requests, causing service disruption.
   1. **CVSS Risk Rating:**
      - 5.9 (Medium)CVSS:3.0/AV:N/AC:H/PR:N/UI:N/S:U/C:N/I:N/A:H
   2. **Justification:**
      - DoS attacks can disrupt the signaling system, preventing the manager to actually know when a transport started/ended.
   3. **Kind of Abuse:**
      - Technical
   4. **Countermeasures:**
      - Implement rate limiting and traffic filtering to mitigate DoS attacks.
      - Use redundant systems to maintain service availability.
      - Employ DDoS mitigation services as a proactive measure.

## UC7. As a driver, I want to be able to prove my delivery with a photo or a screenshot of the maps so that my job execution is accepted

1. The driver could use pre-existing photos from their gallery or the internet and pass them off as proof of delivery or instead of genuinely delivering the package or completing the service, they may provide a photo taken at a different location.
   1. **CVSS Risk Rating:**
      - 5.7 (Medium) CVSS:3.0/AV:N/AC:L/PR:L/UI:R/S:U/C:N/I:H/A:N
   2. **Justification:**
      - By fabricating proof of delivery, the driver can save time that would have been spent on actually completing the delivery. This time can then be used for personal activities or for taking on additional delivery jobs, thereby increasing potential earnings.
   3. **Kind of Abuse:**
      - Business
   4. **Countermeasures:**
      - Providing training and awareness programs for drivers about the importance of accurate reporting and the consequences of providing false proof of delivery can deter misconduct.
      - Implementing strict disciplinary measures and consequences for drivers caught providing false proof of delivery.
      - Implementing geofencing technology can help enforce delivery boundaries, ensuring that deliveries are made within designated areas.

2. An attacker impersonates a driver to fake photo sending.
   1. **CVSS Risk Rating:**
      - 7.3 (High) CVSS:3.0/AV:N/AC:H/PR:H/UI:R/S:C/C:H/I:H/A:N
   2. **Justification:**
      - Impersonation can lead to unauthorized signaling creating problems for deliveries.
   3. **Kind of abuse:**
      - Business
   4. **Countermeasures:**
      - Implement strong authentication mechanisms for drivers.
      - Conduct regular verification checks for all drivers.

3. Attackers flood the photo sending system with a high volume of photo requests, causing service disruption.
   1. **CVSS Risk Rating:**
      - 5.9 (Medium)CVSS:3.0/AV:N/AC:H/PR:N/UI:N/S:U/C:N/I:N/A:H
   2. **Justification:**
      - DoS attacks can disrupt the signaling system, preventing the manager to actually know when a transport started/ended.
   3. **Kind of Abuse:**
      - Technical
   4. **Countermeasures:**
      - Implement rate limiting and traffic filtering to mitigate DoS attacks.
      - Use redundant systems to maintain service availability.
      - Employ DDoS mitigation services as a proactive measure.

## UC8. As a manager, I want to be able to approve or reject a job so that I can control the services

1. An attacker gains access to the approval system and forges approvals for jobs that were not actually reviewed by the manager. This could result in unauthorized services being provided or resources being allocated improperly.
   1. **CVSS Risk Rating:**
      - 7.3 (High) CVSS:3.0/AV:N/AC:H/PR:H/UI:R/S:C/C:H/I:H/A:N
   2. **Justification:**
      - Forged approvals can result in unauthorized services being provided or resources being allocated improperly.
   3. **Kind of Abuse:**
      - Business
   4. **Countermeasures:**
      - Implement approval workflows with multiple layers of verification.
      - Provide training on recognizing fraudulent requests.
      - Implement auditing mechanisms to detect suspicious activity.

2. Attackers flood the approval system with a high volume of fake job approval requests, causing service disruption or preventing legitimate job approvals from being processed.
   1. **CVSS Risk Rating:**
      - 5.9 (Medium) CVSS:3.0/AV:N/AC:H/PR:N/UI:N/S:U/C:N/I:N/A:H
   2. **Justification:**
      - DoS attacks can disrupt the job approval system, preventing legitimate approvals from being processed.
   3. **Kind of Abuse:**
      - Technical
   4. **Countermeasures:**
      - Implement rate limiting and traffic filtering to mitigate DoS attacks
      - Use redundant systems to maintain service availability
      - Employ DDoS mitigation services as a proactive measure.

3. Malware infects the manager's device, allowing attackers to manipulate job approvals or steal sensitive information related to job details or clients.
   1. **CVSS Risk Rating:**
      - 7.3 (High) CVSS:3.0/AV:N/AC:H/PR:H/UI:R/S:C/C:H/I:H/A:N
   2. **Justification:**
      - Malware infections can compromise the manager's device, allowing attackers to manipulate job approvals or steal sensitive information.
   3. **Kind of Abuse:**
      - Business
   4. **Countermeasures:**
      - Implement endpoint security solutions
      - Conduct regular malware scans and updates
      - Provide training on recognizing and avoiding malware threats.

4. Attackers intercept communication between the manager and the approval system, allowing them to manipulate job approval requests or intercept sensitive information.
   1. **CVSS Risk Rating:**
      - 8.7 (High) CVSS:3.0/AV:N/AC:H/PR:N/UI:N/S:C/C:H/I:H/A:N
   2. **Justification:**
      - MitM attacks can intercept and manipulate job approval requests, leading to unauthorized changes in service delivery.
   3. **Kind of Abuse:**
      - Business
   4. **Countermeasures:**
      - Implement encryption protocols like TLS/SSL to secure communication channels.
      - Use digital signatures to verify the integrity of approval requests.
      - Educate users about the risks of unsecured networks.

5. Attackers exploit vulnerabilities in the approval system's database to manipulate job approval records or gain unauthorized access to sensitive information.
   1. **CVSS Risk Rating:**
      - 9.0 (Critical) CVSS:3.0/AV:N/AC:H/PR:N/UI:N/S:C/C:H/I:H/A:H
   2. **Justification:**
      - SQL injection attacks can manipulate job approval records or gain unauthorized access to sensitive information.
   3. **Kind of Abuse:**
      - Business
   4. **Countermeasures:**
      - Implement input validation and parameterized queries to prevent SQL injection.
      - conduct regular security assessments of the approval system.
      - Enforce strict database access controls.

## UC9. As a manager, I want to be able to dispatch services to drivers so that the Services are executed. I should only dispatch services if the requests are approved

1. An attacker gains unauthorized access to the dispatch system and dispatches fake or malicious services to drivers.
   1. **CVSS Risk Rating:**
      - 7.3 (High) CVSS:3.0/AV:N/AC:H/PR:H/UI:R/S:C/C:H/I:H/A:N
   2. **Justification:**
      - Unauthorized access can lead to the dispatch of fake services, causing confusion and potentially leading to wasted resources.
   3. **Kind of Abuse:**
      - Business
   4. **Countermeasures:**
      - Implement strict access controls with multi-factor authentication.
      - Regularly monitor access logs for suspicious activity.
      - Conduct security training to educate employees on the risks of unauthorized access.

2. An attacker floods the dispatch system with bogus service requests, overwhelming it and preventing legitimate services from being dispatched.
   1. **CVSS Risk Rating:**
      - 8.7 (High) CVSS:3.0/AV:N/AC:H/PR:N/UI:N/S:C/C:N/I:H/A:H
   2. **Justification:**
      - DoS attacks can disrupt operations and prevent critical services from being dispatched.
   3. **Kind of Abuse:**
      - Technical
   4. **Countermeasures:**
      - Implement rate limiting and traffic filtering to mitigate DoS attacks.
      - Use redundant systems to maintain service availability.
      - Employ DDoS mitigation services as a proactive measure.

3. An attacker impersonates a manager or dispatcher to dispatch services, potentially causing confusion or miscommunication among drivers.
   1. **CVSS Risk Rating:**
      - 7.3 (High) CVSS:3.0/AV:N/AC:H/PR:H/UI:R/S:C/C:H/I:H/A:N
   2. **Justification:**
      - Impersonation can lead to unauthorized dispatches and miscommunication among drivers.
   3. **Kind of Abuse:**
      - Business
   4. **Countermeasures:**
      - Implement strong authentication mechanisms for dispatchers.
      - Conduct regular verification checks for dispatched services.
      - Educate drivers on verifying the authenticity of dispatch messages.

4. An attacker spoofs the location data of services to drivers, making it seem like they are in a different location than they actually are. This could lead to misallocation of resources or fraudulent activity.
   1. **CVSS Risk Rating:**
      - 6.2 (Medium) CVSS:3.0/AV:N/AC:H/PR:H/UI:R/S:C/C:L/I:H/A:N
   2. **Justification:**
      - Spoofed location data can result in incorrect resource allocation or fraudulent activities.
   3. **Kind of Abuse:**
      - Business
   4. **Countermeasures:**
      - Use secure geolocation services with validation checks.
      - Employ GPS tracking with tamper-proof mechanisms.
      - Educate drivers on the importance of verifying their assigned locations.

5. An attacker intercepts service dispatch messages and modifies them to change service details, such as pickup/delivery locations or cargo information, leading to confusion or potential theft.
   1. **CVSS Risk Rating:**
      - 8.2 (High) CVSS:3.0/AV:N/AC:H/PR:L/UI:N/S:C/C:H/I:H/A:N
   2. **Justification:**
      - Modified service details can lead to confusion, delays, or potential theft of goods.
   3. **Kind of Abuse:**
      - Business
   4. **Countermeasures:**
      - Implement end-to-end encryption for dispatch messages.
      - Use digital signatures to ensure message integrity.
      - Conduct regular audits and verification checks for dispatched services.

6. A disgruntled employee with access to the dispatch system intentionally disrupts operations by canceling valid services or sending drivers on unnecessary routes.
   1. **CVSS Risk Rating:**
      - 8.1 (High) CVSS:3.0/AV:N/AC:L/PR:H/UI:R/S:C/C:H/I:H/A:N
   2. **Justification:**
      - Insider threats can lead to operational disruptions and financial losses.
   3. **Kind of Abuse:**
      - Business
   4. **Countermeasures:**
      - Implement least privilege access controls.
      - Monitor employee activities and behavior.
      - Provide channels for reporting suspicious behavior.

7. An attacker intercepts communication between the manager and drivers, potentially gaining access to sensitive information or altering instructions.
   1. **CVSS Risk Rating:**
      - 7.7 (High) CVSS:3.0/AV:N/AC:H/PR:H/UI:N/S:C/C:H/I:H/A:N
   2. **Justification:**
      - MitM attacks can lead to unauthorized access to sensitive information or alteration of critical instructions.
   3. **Kind of Abuse:**
      - Business
   4. **Countermeasures:**
      - Implement encryption protocols like TLS/SSL for communication.
      - Use digital signatures to verify message integrity.
      - Educate users on detecting and reporting suspicious activities.

## UC10. As a manager, I want to be able to register Drivers in the application so that the Transports can be executed by them.

1. An attacker registers fake drivers in the application, possibly using stolen identities, to gain access to the system.
   1. **CVSS Risk Rating:** 
      - 7.7 (High) CVSS:3.0/AV:N/AC:H/PR:H/UI:N/S:C/C:H/I:H/A:N
   2. **Justification:** 
      - Fake driver registrations can lead to unauthorized access and misuse of the system.
   3. **Kind of Abuse:**
      - Business.
   4. **Countermeasures:**
      - Implement identity verification checks for driver registration.
      - Use CAPTCHA or similar mechanisms to prevent automated registrations.
      - Conduct regular audits of registered drivers for anomalies.

2. An attacker injects malicious SQL queries into the registration form, gaining unauthorized access to the database or causing data loss.
   1. **CVSS Risk Rating:** 
      - 7.7 (Critical) CVSS:3.0/AV:N/AC:H/PR:H/UI:N/S:C/C:H/I:H/A:N
   2. **Justification:** 
      - SQL injection can lead to unauthorized access to sensitive data and compromise the integrity of the system.
   3. **Kind of Abuse:**
      - Technical and Business. 
   4. **Countermeasures:**
      - Implement input validation and parameterized queries to prevent SQL injection.
      - Conduct regular security audits of the registration system.
      - Use secure coding practices to mitigate injection vulnerabilities.

3. A malicious insider or hacker gains access to sensitive driver information stored in the system, such as personal details or driving history.
   1. **CVSS Risk Rating:** 
      - 7.7 (High) CVSS:3.0/AV:N/AC:H/PR:H/UI:N/S:C/C:H/I:H/A:N
   2. **Justification:** 
      - Unauthorized access to driver information can lead to privacy violations and misuse of personal data.
   3. **Kind of Abuse:**
      - Business.   
   4. **Countermeasures:**
      - Implement strict access controls and least privilege principles.
      - Encrypt sensitive driver information at rest and in transit.
      - Monitor access logs for suspicious activity and unauthorized access attempts.

4. An attacker floods the registration system with automated requests, overwhelming it and preventing legitimate drivers from registering.
   1. **CVSS Risk Rating:**
      - 9.0 (Critical) CVSS:3.0/AV:N/AC:H/PR:N/UI:N/S:C/C:H/I:H/A:H
   2. **Justification:** 
      - DoS attacks can disrupt the registration process, preventing legitimate drivers from accessing the system.
   3. **Kind of Abuse:**
      - Technical.    
   4. **Countermeasures:**
      - Implement rate limiting and captcha mechanisms to mitigate DoS attacks.
      - Use scalable infrastructure to handle increased registration traffic.
      - Employ intrusion detection systems to detect and block suspicious activities.

5. An attacker uses automated scripts to try known username and password combinations (obtained from previous data breaches) to gain unauthorized access to driver accounts.
   1. **CVSS Risk Rating:** 
      - 6.1 (Medium) CVSS:3.0/AV:N/AC:H/PR:N/UI:R/S:C/C:H/I:N/A:N
   2. **Justification:** 
      - Credential stuffing exploits weak passwords and can lead to unauthorized access to driver accounts.
   3. **Kind of Abuse:**
      - Business.   
   4. **Countermeasures:**
      - Enforce strong password policies for driver accounts.
      - Implement multi-factor authentication to mitigate credential stuffing.
      - Educate drivers on creating and using strong, unique passwords.

6. Weak authentication mechanisms allow an attacker to easily bypass the login process and gain access to driver accounts.
   1. **CVSS Risk Rating:** 
      - 7.7 (High) CVSS:3.0/AV:N/AC:H/PR:L/UI:R/S:C/C:H/I:H/A:N
   2. **Justification:** 
      - Insecure authentication can lead to unauthorized access and compromise the security of driver accounts.
   3. **Kind of Abuse:**
      - Business.   
   4. **Countermeasures:**
      - Implement strong authentication protocols such as OAuth 2.0 or OpenID Connect.
      - Use secure token-based authentication for API access.
      - Conduct regular security assessments to identify and address authentication vulnerabilities.

7. Hackers gain access to the database storing driver information, compromising sensitive data such as driver licenses, insurance details, or personal addresses.
   1. **CVSS Risk Rating:** 
      - 7.3 (High) CVSS:3.0/AV:N/AC:H/PR:H/UI:R/S:C/C:H/I:H/A:N
   2. **Justification:** 
      - Data breaches can lead to severe privacy violations and financial losses for both drivers and the organization.
   3. **Kind of Abuse:**
      - Business.   
   4. **Countermeasures:**
      - Encrypt sensitive data at rest and in transit.
      - Implement intrusion detection and prevention systems.
      - Comply with data protection regulations such as GDPR or CCPA.

8. An attacker gains access to a legitimate driver's account by stealing their credentials through phishing or other means, and then misuses this access for malicious purposes.
   1. **CVSS Risk Rating:** 
      - 7.7 (High) CVSS:3.0/AV:N/AC:H/PR:L/UI:R/S:C/C:H/I:H/A:N
   2. **Justification:** 
      - Account takeovers can lead to fraudulent activities and misuse of driver accounts.
   3. **Kind of Abuse:**
      - Business.   
   3. **Countermeasures:**
      - Educate drivers on phishing awareness and safe account practices.
      - Implement anomaly detection for unusual account activities.
      - Provide a mechanism for drivers to report suspicious account access.

## UC11. As a manager, I want to be able to register Customers in the application so that I have the possibility of new Services be asked.

1. An attacker registers fake customers in the application to place fraudulent service requests or obtain sensitive information.
   1. **CVSS Risk Rating:** 
      - 7.7 (High) CVSS:3.0/AV:N/AC:H/PR:H/UI:N/S:C/C:H/I:H/A:N
   2. **Justification:** 
      - Fake customer registrations can lead to fraudulent transactions and misuse of the system.
   3. **Kind of Abuse:**
      - Technical and Business.   
   4. **Countermeasures:**
      - Implement identity verification checks for customer registration.
      - Use CAPTCHA or similar mechanisms to prevent automated registrations.
      - Conduct regular audits of registered customers for anomalies.

2. A rogue employee or unauthorized user accesses and misuses customer data for personal gain or malicious purposes.
   1. **CVSS Risk Rating:** 
      - 7.7 (High) CVSS:3.0/AV:N/AC:H/PR:H/UI:N/S:C/C:H/I:H/A:N
   2. **Justification:** 
      - Privacy violations can lead to legal repercussions and loss of trust from customers.
   3. **Kind of Abuse:**
      - Business.   
   4. **Countermeasures:**
      - Implement strict access controls and data encryption for customer data.
      - Conduct background checks on employees with access to sensitive data.
      - Monitor access logs for unauthorized access attempts.

3. An attacker alters customer details in the system, such as contact information or billing details, leading to confusion or financial loss.
   1. **CVSS Risk Rating:** 
      - 7.3 (High) CVSS:3.0/AV:N/AC:H/PR:H/UI:R/S:C/C:H/I:H/A:N
   2. **Justification:** 
      - Altered customer details can lead to service disruptions and financial harm.
   3. **Kind of Abuse:**
      - Business.   
   4. **Countermeasures:**
      - Implement data integrity checks and validation rules.
      - Encrypt sensitive customer information to prevent tampering.
      - Provide customers with the ability to review and verify their information.

4. An attacker takes over a legitimate customer account, changes their contact information, and places orders under their name, leading to confusion and potential financial harm.
   1. **CVSS Risk Rating:**
      - 7.3 (High) CVSS:3.0/AV:N/AC:H/PR:H/UI:R/S:C/C:H/I:H/A:N
   2. **Justification:** 
      - Account hijacking can result in fraudulent transactions and damage to the customer's reputation.
   3. **Kind of Abuse:**
      - Business.   
   4. **Countermeasures:**
      - Implement multi-factor authentication for customer accounts.
      - Notify customers of any changes to their account information.
      - Provide a mechanism for customers to report suspicious account activities.

5. If the application has APIs for customer registration, an attacker could abuse these APIs to register fake customers in bulk, overwhelming the system and causing disruption.
   1. **CVSS Risk Rating:** 
      - 9.0 (Critical) CVSS:3.0/AV:N/AC:H/PR:N/UI:N/S:C/C:H/I:H/A:H
   2. **Justification:** 
      - API abuse can lead to system overload and service disruption.
   3. **Kind of Abuse:**
      - Technical.   
   4. **Countermeasures:**
      - Implement API rate limiting and authentication.
      - Use API keys with proper access controls.
      - Monitor API usage for unusual patterns and behavior.

## UC12. As a user of the system, I want to be able to login into the application so that I can access the application features.

1. An attacker attempts to gain unauthorized access to user accounts by repeatedly guessing usernames and passwords.
This could be done manually or using automated scripts or tools to systematically try different combinations until a valid login is found with the purpose of getting sensitive information.
   1. **CVSS Risk Rating:** 
      - 7.5 (High) CVSS:3.0/AV:N/AC:H/PR:N/UI:N/S:C/C:H/I:L/A:N
   2. **Justification:** 
      - Brute force attacks can lead to unauthorized access to user accounts, potentially compromising sensitive information.
   3. **Kind of Abuse:**
      - Business.   
   4. **Countermeasures:**
      - Implement account lockout mechanisms after a certain number of failed login attempts.
      - Enforce strong password policies.
      - Employ CAPTCHA or multi-factor authentication to mitigate brute force attacks.

2. Attackers use previously leaked username/password pairs from other breaches and attempt to log in with those credentials on your application with the purpose of stealing sensitive information.
Since users often reuse passwords across multiple services, this can lead to successful unauthorized access.
   1. **CVSS Risk Rating:** 
      - 6.9 (Medium) CVSS:3.0/AV:N/AC:H/PR:N/UI:R/S:C/C:H/I:L/A:N
   2. **Justification:** 
      - Credential stuffing exploits users' tendencies to reuse passwords across multiple services, potentially granting unauthorized access to accounts.
   3. **Kind of Abuse:**
      - Business.   
   4. **Countermeasures:**
      - Encourage users to use unique passwords for each service.
      - Implement multi-factor authentication.
      - Regularly monitor for suspicious login attempts or patterns.

3. Attackers send deceptive emails or messages pretending to be from the application, tricking users into providing their login credentials on a fake login page with the purpose of getting sensitive information.
Once the user submits their credentials, the attacker can use them to access the legitimate application.
   1. **CVSS Risk Rating:** 
      - 8.6 (High) CVSS:3.0/AV:N/AC:L/PR:N/UI:N/S:C/C:H/I:N/A:N
   2. **Justification:** 
      - Phishing attacks can deceive users into providing their login credentials, compromising their accounts and potentially leading to further exploitation.
   3. **Kind of Abuse:**
      - Business.   
   4. **Countermeasures:**
      - Educate users about identifying phishing attempts.
      - Implement email filtering to detect and block phishing emails.
      - Use domain validation techniques to verify legitimate login pages.

4. Attackers intercept or steal a valid session token or cookie after a user successfully logs in.
With the stolen session token, the attacker can then impersonate the logged-in user without needing to know their credentials.
   1. **CVSS Risk Rating:** 
      - 8.5 (High) CVSS:3.0/AV:N/AC:H/PR:N/UI:R/S:C/C:H/I:N/A:N
   2. **Justification:** 
      - Session hijacking can result in unauthorized access to user accounts and sensitive information without requiring knowledge of login credentials.
   3. **Kind of Abuse:**
      - Business.   
   4. **Countermeasures:**
      - Implement secure session management techniques such as using HTTPS.
      - Use secure cookies with HttpOnly and Secure flags.
      - Regularly rotating session tokens.

5. Attackers intercept communication between the user and the application during the login process.
They can eavesdrop on the login credentials being transmitted, potentially capturing sensitive information like usernames and passwords.
   1. **CVSS Risk Rating:** 
      - 8.7 (High) CVSS:3.0/AV:N/AC:H/PR:N/UI:N/S:C/C:H/I:H/A:N
   2. **Justification:** 
      - MitM attacks during login can intercept sensitive information like usernames and passwords, leading to unauthorized access to accounts.
   3. **Kind of Abuse:**
      - Business.   
   4. **Countermeasures:**
      - Use encryption protocols like TLS/SSL to secure communication channels.
      - Implement certificate pinning.
      - Educate users about connecting to trusted networks.

6. If the application displays database content retrieved via SQL injection in user-accessible pages without proper sanitization, it can lead to XSS vulnerabilities
Attackers can inject malicious scripts into the database, which are then executed in the context of other users' sessions when the data is displayed in the application.
   1. **CVSS Risk Rating:** 
      - 9.0 (Critical) CVSS:3.0/AV:N/AC:H/PR:N/UI:N/S:C/C:H/I:H/A:H
   2. **Justification:** 
      - XSS via SQL injection can lead to the execution of malicious scripts in the context of other users' sessions, compromising the security of the application and user data.
   3. **Kind of Abuse:**
      - Business.   
   4. **Countermeasures:**
      - Implement input validation and parameterized queries to prevent SQL injection.
      - Sanitize user input to mitigate XSS vulnerabilities.
      - Regularly patch and update application frameworks and libraries.