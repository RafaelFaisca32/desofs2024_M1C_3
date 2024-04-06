# Functional Security Requirements:

## Authentication and Authorization:

- Users (clients, managers, drivers) must authenticate before accessing the system.
- Clients should have the authority to create requests.
- Managers should have the authority to assign requests to drivers.
- Drivers should only be able to access requests assigned to them.

## Data Encryption:

- User credentials and sensitive data like location information should be encrypted during transmission and storage.
- Encryption should adhere to industry-standard protocols to prevent unauthorized access.

## Secure Communication:

- Secure communication channels (HTTPS) should be used between clients, managers, drivers, and the server to prevent data interception.

## Access Control:

- Role-based access control (RBAC) should be implemented to restrict access to specific features based on user roles.
- Managers should have access to administrative functions such as assigning requests, drivers should only have access to request details and navigation features and clients should access the create request page.

## Audit Trails:

- Log and monitor user activities to create an audit trail.
- The system should log login attempts, access to sensitive data, and any actions performed by users.