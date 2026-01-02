## **System Properties**

This document provides detailed information of all custom system properties used in the Authify system.

| Property                                    | Description                                                                       | Env Variable                          | Default Value                      |
|---------------------------------------------|-----------------------------------------------------------------------------------|---------------------------------------|------------------------------------|
| `authify.version`                           | Application version from Maven.                                                   | None                                  | `@project.version@`                |
| `authify.shutdown-token`                    | Token used for secure application shutdown. Must be set via environment variable. | `SHUTDOWN_TOKEN`                      | **No default, required**           |
| `authify.core.jwt.issuer`                   | JWT issuer.                                                                       | None                                  | `authify`                          |
| `authify.core.jwt.key-alias`                | Alias for JWT key in keystore.                                                    | `JWT_KEYSTORE_ALIAS`                  | `authify`                          |
| `authify.core.jwt.key-store-path`           | Path to JWT keystore file.                                                        | `JWT_KEYSTORE_PATH`                   | `keys/jwt-keystore.p12`            |
| `authify.core.jwt.key-store-type`           | Keystore type.                                                                    | `JWT_KEYSTORE_TYPE`                   | `PKCS12`                           |
| `authify.core.jwt.key-store-password`       | Password for keystore.                                                            | `JWT_KEYSTORE_PASSWORD`               | **No default, required**           |
| `authify.core.jwt.private-key-passphrase`   | Private key passphrase.                                                           | `JWT_KEYSTORE_PRIVATE_KEY_PASSPHRASE` | **No default, required**           |
| `authify.core.jwt.access-token-expiration`  | JWT access token expiration.                                                      | `JWT_ACCESS_TOKEN_EXPIRATION_TIME`    | `15m`                              |
| `authify.core.jwt.refresh-token-expiration` | JWT refresh token expiration.                                                     | `JWT_REFRESH_TOKEN_EXPIRATION_TIME`   | `7d`                               |
| `authify.core.otp.reset.length`             | Length of OTP for reset.                                                          | None                                  | `6`                                |
| `authify.core.otp.reset.expiration`         | OTP expiration for reset.                                                         | None                                  | `15m`                              |
| `authify.core.otp.verify.length`            | Length of OTP for verification.                                                   | None                                  | `6`                                |
| `authify.core.otp.verify.expiration`        | OTP expiration for verification.                                                  | None                                  | `1d`                               |
| `authify.core.cors.allowed-origins`         | Allowed origins for CORS requests. Can be customized per environment.             | None                                  | `localhost:3000`, `localhost:5173` |
| `authify.core.openapi.title`                | OpenAPI documentation title displayed in Swagger UI.                              | None                                  | `Authify API Documentation`        |
| `authify.core.openapi.description`          | Detailed OpenAPI description explaining API purpose, features, and usage.         | None                                  | API description text               |
| `authify.core.openapi.contact.name`         | Contact name shown in OpenAPI documentation.                                      | None                                  | `Authify Team`                     |
| `authify.core.openapi.contact.email`        | Contact email shown in OpenAPI documentation.                                     | None                                  | `support@authify.com`              |
| `authify.core.openapi.contact.url`          | Contact URL shown in OpenAPI documentation.                                       | None                                  | `https://authify.com`              |
