package de.breuer.bateen.controller;

import de.breuer.bateen.config.BateenConfig;
import de.breuer.bateen.model.Officer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ConfigController {

    static BateenConfig bateenConfig;

    @Autowired
    public ConfigController(BateenConfig bateenConfig) {
        ConfigController.bateenConfig = bateenConfig;
    }

    public static String getUrl() {
        return bateenConfig != null ? bateenConfig.getUrl() : "";
    }

    public static void setUrl(String url) {
        bateenConfig.setUrl(String.format("http://%s", url));
        setGraphqlUrl(url);
    }

    public static String getGraphqlUrl() {
        return bateenConfig != null ? bateenConfig.getGraphqlUrl() : "";
    }

    public static void setGraphqlUrl(String url) {
        bateenConfig.setGraphqlUrl(String.format("http://%s/graphql", url));
    }

    public static List<Officer> getOfficers() {
        return bateenConfig.getOfficers();
    }

    public static void addOfficer(Officer officer) {
        if (bateenConfig.getOfficers() == null) {
            bateenConfig.setOfficers(List.of(officer));
        } else {
            List<Officer> officers = new ArrayList<>(bateenConfig.getOfficers());
            officers.add(officer);
            bateenConfig.setOfficers(officers);
        }
    }

    public static Officer removeOfficerAndGetRemainOfficerOrNull(String officerId) {
        if (bateenConfig.getOfficers() != null) {
            List<Officer> officers = new ArrayList<>(bateenConfig.getOfficers());
            Officer deletedOfficer = officers.stream().filter(officer -> officerId.equals(officer.getOfficerId())).findFirst().orElse(null);
            officers.remove(deletedOfficer);
            bateenConfig.setOfficers(officers);
            if (officers.size() == 1) {
                return officers.getFirst();
            } else return null;
        }
        return null;
    }
}
