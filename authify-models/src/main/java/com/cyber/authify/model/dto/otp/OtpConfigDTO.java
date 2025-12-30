package com.cyber.authify.model.dto.otp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OtpConfigDTO {

    private byte resetOtpLength;

    private byte verifyOtpLength;
}
