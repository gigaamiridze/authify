CREATE TABLE IF NOT EXISTS system_users (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    is_email_verified BOOLEAN DEFAULT FALSE,
    verify_otp VARCHAR(6),
    verify_otp_expires_at TIMESTAMP,
    reset_otp VARCHAR(6),
    reset_otp_expires_at TIMESTAMP,

    -- Auditable fields
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    created_by VARCHAR(255) NOT NULL,
    updated_by VARCHAR(255)
);

CREATE UNIQUE INDEX IF NOT EXISTS email_index ON system_users(email);