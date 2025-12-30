## **Error Codes**

This document provides detailed information of all error codes used in the Authify system.

| Error Code                 | Description                                                          | HTTP Status               |
|----------------------------|----------------------------------------------------------------------|---------------------------|
| `UNAUTHORIZED`             | The user is not authenticated or provided invalid credentials.       | 401 Unauthorized          |
| `ACCESS_DENIED`            | The user is authenticated but not authorized to access the resource. | 403 Forbidden             |
| `USER_NOT_FOUND`           | The specified user does not exist.                                   | 404 Not Found             |
| `EMAIL_ALREADY_EXISTS`     | The email provided is already registered in the system.              | 409 Conflict              |
| `EMAIL_ALREADY_VERIFIED`   | The email has already been verified.                                 | 409 Conflict              |
| `VALIDATION_FAILED`        | Input validation failed.                                             | 400 Bad Request           |
| `EXPIRED_OTP`              | The one-time password (OTP) has expired.                             | 400 Bad Request           |
| `INVALID_OTP`              | The one-time password (OTP) is invalid.                              | 400 Bad Request           |
| `INVALID_CREDENTIALS`      | Username or password is incorrect.                                   | 401 Unauthorized          |
| `INVALID_TOKEN_TYPE`       | Provided token type is invalid.                                      | 400 Bad Request           |
| `INVALID_OR_EXPIRED_TOKEN` | The token is invalid or has expired.                                 | 401 Unauthorized          |
| `EMAIL_SEND_FAILED`        | Failed to send email (e.g., verification email).                     | 500 Internal Server Error |
| `INTERNAL_SERVER_ERROR`    | A generic server error occurred.                                     | 500 Internal Server Error |
