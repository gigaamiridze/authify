package com.cyber.authify.model.properties;

import com.cyber.authify.model.dto.Origin;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "authify.core.cors")
public class CorsProperties {

    @Valid
    private List<Origin> allowedOrigins;
}
