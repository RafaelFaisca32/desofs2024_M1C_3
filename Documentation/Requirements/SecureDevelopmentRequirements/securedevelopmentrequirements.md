# Secure Development Requirements

## Secure Design

- Conduct threat modeling to identify potential security threats and vulnerabilities early in the design phase.
- Design the application with security in mind, following security best practices and principles such as the principle of least privilege and defense-in-depth.

## Secure Coding Practices

- Follow secure coding guidelines and standards such as OWASP Top 10 and CWE/SANS Top 25.
- Use secure coding practices to prevent common vulnerabilities such as injection attacks (SQL injection, XSS), insecure direct object references, and buffer overflows.
- Do not use unsupported, inscure or deprecate client-side technologies.
- Avoid the usage of eval() or other similar dynamic coding.
- Code Integrity and malicious code search will be analysed by tools such as Sonarqube and Dependency track. If possible with integration of quality gates.
- ZAP to test the application security.

## Input Validation

- Implement input validation to prevent injection attacks and ensure that user input is properly sanitized and validated before processing.
- Validate input data types, length, format, and range to mitigate the risk of malicious input.
- Data images should be served by their octet stream.
  
- Protection against OS Command Injection.
- Verify that sign, range, and input validation techniques are used to prevent integer overflows.

## Output Encoding

- Encode output data to prevent cross-site scripting (XSS) attacks.
- Use output encoding techniques such as HTML entity encoding or escaping to neutralize user-controlled data before rendering it in the browser.
- Output encoding preserves the user's chosen character set and locale.
- Guarantee that output encoding is not vulnerable against injection attacks.

## Secure Configuration

- Configure the application and underlying components securely, following vendor recommendations and security best practices.
- Disable unnecessary services, ports, and functionalities to reduce the attack surface.
- Never reveal Session Tokens in URL parameters
- Never use client-side secrets, such as symmetric keys, passwords or API tokens to protect or access sensitive data.

## Secure Authentication and Session Management

- Use strong and adaptive authentication mechanisms, such as multi-factor authentication (MFA), to verify the identity of users.
- Implement secure session management practices, including session expiration, session token regeneration, and secure cookie attributes.
- Never reveal Session Tokens in URL parameters.

## Error Handling and Logging

- Implement robust error handling mechanisms to provide meaningful error messages to users without exposing sensitive information.
- Log security-related events, errors, and exceptions to facilitate incident response and forensic analysis.
- When an unexpected or security sensitive error occurs, show a generic message, potentially with a unique ID.

## Secure Communication

- Use secure communication protocols (e.g., TLS/SSL) to encrypt data transmitted between clients and servers.
- Verify server certificates and use strong cipher suites to prevent man-in-the-middle attacks.
- The communication with Backend must be protected against JSON injection attacks.
- Will run the application on port 443, is the most common port used for encrypted HTTPS traffic.

## Secure Data Storage

- Encrypt sensitive data at rest using strong encryption algorithms and key management practices.
- Implement access controls and authorization mechanisms to restrict access to sensitive data based on user roles and permissions.
- Cron job that does a database backup every 24 hours.

## Security Testing

- Conduct regular security testing throughout the development lifecycle, including static code analysis, dynamic application security testing (DAST), and penetration testing.
- Perform security reviews and code reviews to identify and remediate security vulnerabilities before deployment.