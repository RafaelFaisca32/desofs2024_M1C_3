# Secure Development Requirements

## Secure Design

- Conduct threat modeling to identify potential security threats and vulnerabilities early in the design phase.
- Design the application with security in mind, following security best practices and principles such as the principle of least privilege and defense-in-depth.

## Secure Coding Practices

- Follow secure coding guidelines and standards such as OWASP Top 10 and CWE/SANS Top 25.
- Use secure coding practices to prevent common vulnerabilities such as injection attacks (SQL injection, XSS), insecure direct object references, and buffer overflows.
- Do not use unsupported, insecure or deprecate client-side technologies.
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
- The application will have CI/CD automation, automated configuration management and automated deployment scripts.
- The application will have the compiler flags so that all available buffer overflow protections and warnings are active.
- The server configuration will be hardened in order to follow the recommendations of the application server and frameworks in use.
- The application will be able to be re-deployed using automated deployment scripts.
- Only Admin level users will be able to verify the integrity of all security-relevant configurations to detect tampering.
- Dependency checker will be used during build or compile time to see if all components are up to date.
- Clean up of unnecessary feature, documentation and configuration will be done.
- All application assets will be hosted externally on a content delivery network or external provider.
- All third party components will come from pre-defined, trusted and continually maintained repositories.
- All third party libraries in use will only be exposed only for the required behavior of the application to reduce attack surface.
- Debug mode of the application will be disabled on the web application to avoid possible security disclosures and debug features.
- System components information will be removed from the HTTP headers/response.
- HTTP response will contain a Content-Type header.
- All API responses will have a Content-Disposition: attachment; filename="api.json" header.
- A Content Security Policy (CSP) will exist in order to avoid possible XSS attacks.
- All API responses will have  a X-Content-Type-Options: nosniff header.
- A Strict-Transport-Header will be used in all responses and for all subdomains.
- A Referrer-Policy header will be used to avoid exposing sensitive information in the URL to untrusted parties.
- Origin header will not be used for authentication or access control decisions, since it can be easily changed by an attacker.
- CORS (Cross-Origin Resource Sharing) will use a strict allow list of trusted domains and subdomains.

## Secure Authentication and Session Management

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