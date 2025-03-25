package de.breuer.bateen.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.breuer.bateen.model.Officer;
import lombok.Data;

@Data
public class OfficerResponseWrapper {
    @JsonProperty("data")
    private DataContainer data;

    @Data
    public static class DataContainer {
        @JsonProperty("login")
        private Officer officer;
    }
}
