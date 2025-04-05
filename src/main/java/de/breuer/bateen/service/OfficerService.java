package de.breuer.bateen.service;

import de.breuer.bateen.config.BateenConfig;
import de.breuer.bateen.model.Officer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OfficerService {

    BateenConfig bateenConfig;

    @Autowired
    public OfficerService(BateenConfig bateenConfig) {
        this.bateenConfig = bateenConfig;
    }

    public void addOfficer(Officer officer) {
        if (bateenConfig.getOfficers() == null) {
            bateenConfig.setOfficers(List.of(officer));
        } else {
            List<Officer> officers = new ArrayList<>(bateenConfig.getOfficers());
            officers.add(officer);
            bateenConfig.setOfficers(officers);
        }
    }

    public Officer removeOfficerAndGetRemainOfficerOrNull(String officerId) {
        if (bateenConfig.getOfficers() != null) {
            List<Officer> officers = new ArrayList<>(bateenConfig.getOfficers());
            Officer deletedOfficer = officers.stream().filter(officer -> officerId.equals(officer.getOfficerId())).findFirst().orElse(null);
            officers.remove(deletedOfficer);
            bateenConfig.setOfficers(officers);
            if (officers.size() == 1) {
                return officers.get(0);
            } else return null;
        }
        return null;
    }
}
