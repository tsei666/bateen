package de.breuer.bateen;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class BateenConfig {
    private String url = "test";
}
