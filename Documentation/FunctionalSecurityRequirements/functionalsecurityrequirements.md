# Functional Security Requirements

## Authentication and Authorization

- Users (clients, managers, drivers) must authenticate before accessing the system.
- Clients should have the authority to create requests.
- Managers should have the authority to assign requests to drivers.
- Drivers should only be able to access requests assigned to them.
- No default passwords in use for the application framework or any components used by the application.
- Usage of JWT Token for Authentication managed by Server Side Code.
- Invalidate Token when user Logouts.
- Whenever the JWT Token expires, disable every transaction or data modifications.
- The password policy should follow:
  1. Must be at least 12 characters long, after multiple spaces are combined.
  2. Must not be more that 128 characters.
  3. Must not be truncated, only multiple concatenated spaces should by replaced ny a single space.
  4. Must allow any printable unicode character such as emojis.
  5. Must not be any requirement for upper or lowe case or numbers or special chartacters.
  6. "Paste" functionality should be allowed.
- The e-mail/sms push-up notification should not contain any sensitive information.
- Any password generated (such as for drivers and clients) should be at least 6 characters long, may contain letters and numbers and expire after a short period of time. It should be securely randomly generated.
- No Secrets Questions used.
- Credential recovery must not reveal the current password.
- No default accounts should be present (admin, root or sa).
- Any authentication factor replaced or changed should be notified.
- Lookup secrets should not be predictable.

## Data Encryption

- User credentials and sensitive data like location information should be encrypted during transmission and storage.
- Encryption should adhere to industry-standard protocols to prevent unauthorized access.

## Secure Communication

- Secure communication channels (HTTPS) should be used between clients, managers, drivers, and the server to prevent data interception.

## Access Control

- Role-based access control (RBAC) should be implemented to restrict access to specific features based on user roles.
- Managers should have access to administrative functions such as assigning requests, drivers should only have access to request details and navigation features and clients should access the create request page.

## Audit Trails

- Log and monitor user activities to create an audit trail.
- The system should log login attempts, access to sensitive data, and any actions performed by users.