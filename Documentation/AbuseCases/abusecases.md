## Use Case 10: Register Drivers

### Security Abuse Cases:

#### Fake Driver Registration

- **Description:** An attacker registers fake drivers in the application, possibly using stolen identities, to gain access to the system.
- **CVSS Risk Rating:** 7.7 (High) CVSS:3.0/AV:N/AC:H/PR:H/UI:N/S:C/C:H/I:H/A:N
- **Justification:** Fake driver registrations can lead to unauthorized access and misuse of the system.
- **Countermeasures:**
    - Implement identity verification checks for driver registration.
    - Use CAPTCHA or similar mechanisms to prevent automated registrations.
    - Conduct regular audits of registered drivers for anomalies.

#### SQL Injection

- **Description:** An attacker injects malicious SQL queries into the registration form, gaining unauthorized access to the database or causing data loss.
- **CVSS Risk Rating:** 7.7 (Critical) CVSS:3.0/AV:N/AC:H/PR:H/UI:N/S:C/C:H/I:H/A:N
- **Justification:** SQL injection can lead to unauthorized access to sensitive data and compromise the integrity of the system.
- **Countermeasures:**
    - Implement input validation and parameterized queries to prevent SQL injection.
    - Conduct regular security audits of the registration system.
    - Use secure coding practices to mitigate injection vulnerabilities.

#### Unauthorized Access to Driver Information

- **Description:** A malicious insider or hacker gains access to sensitive driver information stored in the system, such as personal details or driving history.
- **CVSS Risk Rating:** 7.7 (High) CVSS:3.0/AV:N/AC:H/PR:H/UI:N/S:C/C:H/I:H/A:N
- **Justification:** Unauthorized access to driver information can lead to privacy violations and misuse of personal data.
- **Countermeasures:**
    - Implement strict access controls and least privilege principles.
    - Encrypt sensitive driver information at rest and in transit.
    - Monitor access logs for suspicious activity and unauthorized access attempts.

#### Denial of Service (DoS)

- **Description:** An attacker floods the registration system with automated requests, overwhelming it and preventing legitimate drivers from registering.
- **CVSS Risk Rating:** 9.0 (Critical) CVSS:3.0/AV:N/AC:H/PR:N/UI:N/S:C/C:H/I:H/A:H
- **Justification:** DoS attacks can disrupt the registration process, preventing legitimate drivers from accessing the system.
- **Countermeasures:**
    - Implement rate limiting and captcha mechanisms to mitigate DoS attacks.
    - Use scalable infrastructure to handle increased registration traffic.
    - Employ intrusion detection systems to detect and block suspicious activities.

#### Credential Stuffing

- **Description:** An attacker uses automated scripts to try known username and password combinations (obtained from previous data breaches) to gain unauthorized access to driver accounts.
- **CVSS Risk Rating:** 6.1 (Medium) CVSS:3.0/AV:N/AC:H/PR:N/UI:R/S:C/C:H/I:N/A:N
- **Justification:** Credential stuffing exploits weak passwords and can lead to unauthorized access to driver accounts.
- **Countermeasures:**
    - Enforce strong password policies for driver accounts.
- Implement multi-factor authentication to mitigate credential stuffing.
- Educate drivers on creating and using strong, unique passwords.

#### Insecure Authentication

- **Description:** Weak authentication mechanisms allow an attacker to easily bypass the login process and gain access to driver accounts.
- **CVSS Risk Rating:** 7.7 (High) CVSS:3.0/AV:N/AC:H/PR:L/UI:R/S:C/C:H/I:H/A:N
- **Justification:** Insecure authentication can lead to unauthorized access and compromise the security of driver accounts.
- **Countermeasures:**
    - Implement strong authentication protocols such as OAuth 2.0 or OpenID Connect.
    - Use secure token-based authentication for API access.
    - Conduct regular security assessments to identify and address authentication vulnerabilities.

#### Data Breach

- **Description:** Hackers gain access to the database storing driver information, compromising sensitive data such as driver licenses, insurance details, or personal addresses.
- **CVSS Risk Rating:** 7.3 (High) CVSS:3.0/AV:N/AC:H/PR:H/UI:R/S:C/C:H/I:H/A:N
- **Justification:** Data breaches can lead to severe privacy violations and financial losses for both drivers and the organization.
- **Countermeasures:**
    - Encrypt sensitive data at rest and in transit.
    - Implement intrusion detection and prevention systems.
    - Comply with data protection regulations such as GDPR or CCPA.

#### Account Takeover

- **Description:** An attacker gains access to a legitimate driver's account by stealing their credentials through phishing or other means, and then misuses this access for malicious purposes.
- **CVSS Risk Rating:** 7.7 (High) CVSS:3.0/AV:N/AC:H/PR:L/UI:R/S:C/C:H/I:H/A:N
- **Justification:** Account takeovers can lead to fraudulent activities and misuse of driver accounts.
- **Countermeasures:**
    - Educate drivers on phishing awareness and safe account practices.
    - Implement anomaly detection for unusual account activities.
    - Provide a mechanism for drivers to report suspicious account access.

## Use Case 11: Register Customers

### Security Abuse Cases:

#### Fake Customer Registration

- **Description:** An attacker registers fake customers in the application to place fraudulent service requests or obtain sensitive information.
- **CVSS Risk Rating:** 7.7 (High) CVSS:3.0/AV:N/AC:H/PR:H/UI:N/S:C/C:H/I:H/A:N
- **Justification:** Fake customer registrations can lead to fraudulent transactions and misuse of the system.
- **Countermeasures:**
    - Implement identity verification checks for customer registration.
    - Use CAPTCHA or similar mechanisms to prevent automated registrations.
    - Conduct regular audits of registered customers for anomalies.

#### Privacy Violation

- **Description:** A rogue employee or unauthorized user accesses and misuses customer data for personal gain or malicious purposes.
- **CVSS Risk Rating:** 7.7 (High) CVSS:3.0/AV:N/AC:H/PR:H/UI:N/S:C/C:H/I:H/A:N
- **Justification:** Privacy violations can lead to legal repercussions and loss of trust from customers.
- **Countermeasures:**
    - Implement strict access controls and data encryption for customer data.
    - Conduct background checks on employees with access to sensitive data.
    - Monitor access logs for unauthorized access attempts.

#### Data Manipulation

- **Description:** An attacker alters customer details in the system, such as contact information or billing details, leading to confusion or financial loss.
- **CVSS Risk Rating:** 7.3 (High) CVSS:3.0/AV:N/AC:H/PR:H/UI:R/S:C/C:H/I:H/A:N
- **Justification:** Altered customer details can lead to service disruptions and financial harm.
- **Countermeasures:**
    - Implement data integrity checks and validation rules.
    - Encrypt sensitive customer information to prevent tampering.
    - Provide customers with the ability to review and verify their information.


#### Account Hijacking

- **Description:** An attacker takes over a legitimate customer account, changes their contact information, and places orders under their name, leading to confusion and potential financial harm.
- **CVSS Risk Rating:** 7.3 (High) CVSS:3.0/AV:N/AC:H/PR:H/UI:R/S:C/C:H/I:H/A:N
- **Justification:** Account hijacking can result in fraudulent transactions and damage to the customer's reputation.
- **Countermeasures:**
    - Implement multi-factor authentication for customer accounts.
    - Notify customers of any changes to their account information.
    - Provide a mechanism for customers to report suspicious account activities.

#### API Abuse (DOS)

- **Description:** If the application has APIs for customer registration, an attacker could abuse these APIs to register fake customers in bulk, overwhelming the system and causing disruption.
- **CVSS Risk Rating:** 9.0 (Critical) CVSS:3.0/AV:N/AC:H/PR:N/UI:N/S:C/C:H/I:H/A:H
- **Justification:** API abuse can lead to system overload and service disruption.
- **Countermeasures:**
    - Implement API rate limiting and authentication.
    - Use API keys with proper access controls.
    - Monitor API usage for unusual patterns and behavior.

These Security Abuse Cases outline potential threats and vulnerabilities in the given use cases, along with corresponding risk ratings and recommended countermeasures to mitigate these risks.

# UC12 - As a user of the system, I want to be able to login into the application so that I can access the application features.

## Brute Force Attacks:

### Description
An attacker attempts to gain unauthorized access to user accounts by repeatedly guessing usernames and passwords.
This could be done manually or using automated scripts or tools to systematically try different combinations until a valid login is found.

### CVSS Risk Rating: 7.5 (High) CVSS:3.0/AV:N/AC:H/PR:N/UI:N/S:C/C:H/I:L/A:N
### Justification: 
Brute force attacks can lead to unauthorized access to user accounts, potentially compromising sensitive information.
### Countermeasures: 
Implement account lockout mechanisms after a certain number of failed login attempts, enforce strong password policies, and employ CAPTCHA or multi-factor authentication to mitigate brute force attacks.

## Credential Stuffing:

### Description
Attackers use previously leaked username/password pairs from other breaches and attempt to log in with those credentials on your application.
Since users often reuse passwords across multiple services, this can lead to successful unauthorized access.

### CVSS Risk Rating: 6.9 (Medium) CVSS:3.0/AV:N/AC:H/PR:N/UI:R/S:C/C:H/I:L/A:N

### Justification: 
Credential stuffing exploits users' tendencies to reuse passwords across multiple services, potentially granting unauthorized access to accounts.
### Countermeasures: 
Encourage users to use unique passwords for each service, implement multi-factor authentication, and regularly monitor for suspicious login attempts or patterns.

## Phishing Attacks:

### Description
Attackers send deceptive emails or messages pretending to be from the application, tricking users into providing their login credentials on a fake login page.
Once the user submits their credentials, the attacker can use them to access the legitimate application.

### CVSS Risk Rating: 8.6 (High) CVSS:3.0/AV:N/AC:L/PR:N/UI:N/S:C/C:H/I:N/A:N

### Justification: 
Phishing attacks can deceive users into providing their login credentials, compromising their accounts and potentially leading to further exploitation.
### Countermeasures: 
Educate users about identifying phishing attempts, implement email filtering to detect and block phishing emails, and use domain validation techniques to verify legitimate login pages.

## Session Hijacking:

### Description
Attackers intercept or steal a valid session token or cookie after a user successfully logs in.
With the stolen session token, the attacker can then impersonate the logged-in user without needing to know their credentials.

### CVSS Risk Rating: 8.5 (High) CVSS:3.0/AV:N/AC:H/PR:N/UI:R/S:C/C:H/I:N/A:N


### Justification: 
Session hijacking can result in unauthorized access to user accounts and sensitive information without requiring knowledge of login credentials.
### Countermeasures: 
Implement secure session management techniques such as using HTTPS, using secure cookies with HttpOnly and Secure flags, and regularly rotating session tokens.

## Man-in-the-Middle (MitM) Attacks:

### Description
Attackers intercept communication between the user and the application during the login process.
They can eavesdrop on the login credentials being transmitted, potentially capturing sensitive information like usernames and passwords.

### CVSS Risk Rating: 8.7 (High) CVSS:3.0/AV:N/AC:H/PR:N/UI:N/S:C/C:H/I:H/A:N

### Justification: 
MitM attacks during login can intercept sensitive information like usernames and passwords, leading to unauthorized access to accounts.

### Countermeasures: 
Use encryption protocols like TLS/SSL to secure communication channels, implement certificate pinning, and educate users about connecting to trusted networks

## Cross-Site Scripting (XSS) via SQL Injection:

### Description
If the application displays database content retrieved via SQL injection in user-accessible pages without proper sanitization, it can lead to XSS vulnerabilities.
Attackers can inject malicious scripts into the database, which are then executed in the context of other users' sessions when the data is displayed in the application.

### CVSS Risk Rating: 9.0 (Critical) CVSS:3.0/AV:N/AC:H/PR:N/UI:N/S:C/C:H/I:H/A:H

### Justification: 
XSS via SQL injection can lead to the execution of malicious scripts in the context of other users' sessions, compromising the security of the application and user data.
### Countermeasures: 
Implement input validation and parameterized queries to prevent SQL injection, sanitize user input to mitigate XSS vulnerabilities, and regularly patch and update application frameworks and libraries.


