package de.breuer.bateen.model;

import lombok.Data;

@Data
public class Officer {
    private String id;
    private String officerId;
    private String firstname;
    private String lastname;
    private String controlUnit;
    private Appearance appearance;
    private String token;

    @Data
    public static class Appearance {
        private String colorScheme;

    }

}
