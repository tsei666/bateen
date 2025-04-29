package de.breuer.bateen;

import de.breuer.bateen.config.VmConfig;

import java.util.Collections;
import java.util.List;

public class VmRepository {

    private static final List<VmConfig> VMS = List.of(
            new VmConfig("Local", "localhost:50000"),
            new VmConfig("BREUER Crafter", "10.212.134.252:50000"),
            new VmConfig("BREUER TAP", "192.168.235.100:50000")
    );

    public static List<VmConfig> getAvailableVms() {
        return Collections.unmodifiableList(VMS);
    }
}
