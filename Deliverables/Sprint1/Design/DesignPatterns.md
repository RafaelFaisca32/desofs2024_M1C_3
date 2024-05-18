# Design Patterns

## Exposure Minization

    - Least Privilege
      - Always give the user the minimum levesl of access (or permissions) needed to perform their actions.
    - Least Information
      - Do not proide unnecessary information. In implementatin level, the cache should be cleaned when no longer needed.
    - Secure by Default
      - The software must be secure in the default state and not insecure and treated after.
    - Allowlists over Blocklists
      - Define what is possible, not what is not possible.
    - Avoid Predictability
      - Any data (or behavior) that is predictable cannot be kept private.
    - Fail Securely
      - Whenever the system fails, it should fail in a secure state.

## Strong Enforcement

    - Complete Mediation
      - Securely check all accesses to a protected assets through the same authorization check.
    - Least Common Mechanism
      - Do not share system mechanisms among users or programs except when absolutely necessary.

## Redundancy

    - Defense in Depth
      - Make security layers with defensive mechanisms to protect an asset.
    - Separation of Privilege
      - Do not grant permission based on a single condition. Segregate parts of an IT environment based on its users and their roles.

## Trust and Responsibility

    - Reluctance to Trust
      - Verify the authenticity of the code before installing it, requiring a strong authentication before authorization. User input should not be trusted. Minimze trusted computing base.
    - Accept Security Responsibility
      - Have clear duty to take responsibility for security.
