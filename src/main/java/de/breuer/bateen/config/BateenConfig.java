package de.breuer.bateen.config;

import de.breuer.bateen.model.Display;
import de.breuer.bateen.model.Officer;
import lombok.Data;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@Data
public class BateenConfig {
    private String url;
    private String graphqlUrl;
    private List<Officer> officers;
    private Display display;
}
