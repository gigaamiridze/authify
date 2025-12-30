package com.cyber.authify.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
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
public class Origin {

    @NotBlank
    @Pattern(regexp = "http|https", message = "Scheme must be http or https")
    private String scheme;

    @NotBlank
    private String host;

    @PositiveOrZero
    private int port;

    public String buildOrigin() {
        int defaultPort = getDefaultPortForScheme(scheme);
        String portPart = (port == defaultPort) ? "" : ":" + port;
        return scheme + "://" + host + portPart;
    }

    private int getDefaultPortForScheme(String scheme) {
        return scheme.equalsIgnoreCase("http") ? 80 :
               scheme.equalsIgnoreCase("https") ? 443 : -1;
    }
}
