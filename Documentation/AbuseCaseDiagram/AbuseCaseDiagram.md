# Abuse Cases

## 2. As a Customer, I want to be able to see the status of my requested Transport (Pending/Accepted/Rejected/In progress) so that I can have control of it

1. As an attacker, I bypass access control checks by modifying the URL, internal application state, or the HTML page, or simply using a custom API attack tool so that I can access the privilege information about the transport status.
   1. CVSS V3 risk rating: 5.7 CVSS:3.0/AV:N/AC:L/PR:L/UI:R/S:U/C:H/I:N/A:N
   2. Kind of Abuse: Technical
   3. Counter Measure: Add verification for authorization inside the data access.

2. As an attacker and/or malicious user, I can manipulate the primary key so that I can access the transport status of other users.
   1. CVSS V3 risk rating: 5.7 CVSS:3.0/AV:N/AC:L/PR:L/UI:R/S:U/C:H/I:N/A:N
   2. Kind of Abuse: Technical
   3. Counter Measure: Add verification for authorization so that only verified users access the data defined for them.

3. As an attacker, I manipulate sessions, access tokens, or other access controls in the application to act as a user without being logged in, so that I can get access to the information about the transport status.
   1. CVSS V3 risk rating: 5.3 CVSS:3.0/AV:N/AC:H/PR:N/UI:R/S:U/C:H/I:N/A:N;
   2. Kind of Abuse: Technical;
   3. Counter Measure: Make session and access tokens secrets and hardly unaccessible for outside users.

4. As an attacker, I force browsing to transport status page without being authenticated, gaining access to privilege the information about the transport status.
   1. CVSS V3 risk rating: 5.3 CVSS:3.0/AV:N/AC:H/PR:N/UI:R/S:U/C:H/I:N/A:N;
   2. Kind of Abuse: Technical;
   3. Counter Measue: Always verify authentication inside the transport status page.

## 5. As the Driver, I want to visualize the Transports that have been assigned to me so that I can have control of my work

1. As an attacker, I bypass access control checks by modifying the URL, internal application state, or the HTML page, or simply using a custom API attack tool so that I can access the privilege information about the transports that have been assigned.
   1. CVSS V3 risk rating: 5.7 CVSS:3.0/AV:N/AC:L/PR:L/UI:R/S:U/C:H/I:N/A:N
   2. Kind of Abuse: Technical
   3. Counter Measure: Add verification for authorization inside the data access.

2. As an attacker and/or malicious user, I can manipulate the primary key so that I can access the transport services assigned for other drivers.
   1. CVSS V3 risk rating: 5.7 CVSS:3.0/AV:N/AC:L/PR:L/UI:R/S:U/C:H/I:N/A:N
   2. Kind of Abuse: Technical
   3. Counter Measure: Add verification for authorization so that only verified users access the data defined for them.

3. As an attacker, I manipulate sessions, access tokens, or other access controls in the application to act as a user without being logged in, so that I can get access to the information about all the drivers transport jobs assigned.
   1. CVSS V3 risk rating: 5.3 CVSS:3.0/AV:N/AC:H/PR:N/UI:R/S:U/C:H/I:N/A:N;
   2. Kind of Abuse: Technical;
   3. Counter Measure: Make session and access tokens secrets and hardly unaccessible for outside users.

4. As an attacker, I force browsing to transport status page without being authenticated, gaining access to privilege the information about the transport jobs assigned to the drivers.
   1. CVSS V3 risk rating: 5.3 CVSS:3.0/AV:N/AC:H/PR:N/UI:R/S:U/C:H/I:N/A:N;
   2. Kind of Abuse: Technical;
   3. Counter Measue: Always verify authentication inside the transport jobs page.
