## **Environment Variables**

This document provides detailed information of all environment variables used in the Authify system.

| Variable Name                         | Description                                                   | Default Value            |
|---------------------------------------|---------------------------------------------------------------|--------------------------|
| `DATABASE_HOST`                       | Hostname or IP address of the PostgreSQL database server.     | `localhost`              |
| `DATABASE_PORT`                       | Port on which the PostgreSQL database is listening.           | `5432`                   |
| `DATABASE_NAME`                       | Name of the PostgreSQL database to connect to.                | `authify`                |
| `DATABASE_USERNAME`                   | Username for authenticating with the PostgreSQL database.     | **No default, required** |
| `DATABASE_PASSWORD`                   | Password for authenticating with the PostgreSQL database.     | **No default, required** |
| `SMTP_MAIL_HOST`                      | Hostname of the SMTP server used for sending emails.          | `smtp.gmail.com`         |
| `SMTP_MAIL_PORT`                      | Port of the SMTP server.                                      | `587`                    |
| `SMTP_MAIL_USERNAME`                  | Username for authenticating with the SMTP server.             | **No default, required** |
| `SMTP_MAIL_PASSWORD`                  | Password for authenticating with the SMTP server.             | **No default, required** |
| `SHUTDOWN_TOKEN`                      | Token required to securely trigger application shutdown.      | **No default, required** |
| `JWT_KEYSTORE_ALIAS`                  | Alias of the key in the keystore used for signing JWT tokens. | `authify`                |
| `JWT_KEYSTORE_PATH`                   | File path to the keystore containing the JWT signing key.     | `keys/jwt-keystore.p12`  |
| `JWT_KEYSTORE_TYPE`                   | Type of keystore (e.g., PKCS12, JKS).                         | `PKCS12`                 |
| `JWT_KEYSTORE_PASSWORD`               | Password for accessing the keystore file.                     | **No default, required** |
| `JWT_KEYSTORE_PRIVATE_KEY_PASSPHRASE` | Passphrase for accessing the private key in the keystore.     | **No default, required** |
| `JWT_ACCESS_TOKEN_EXPIRATION_TIME`    | Expiration time for JWT access tokens.                        | `15m`                    |
| `JWT_REFRESH_TOKEN_EXPIRATION_TIME`   | Expiration time for JWT refresh tokens.                       | `7d`                     |
