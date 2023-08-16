package net.rainbowcreation.core.datamanager;

public class Service {
    private boolean yml;
    private boolean mysql;
    private boolean redis;
    public Service() {

    }

    public boolean get(String service) {
        switch (service.toLowerCase()) {
            case ("yml") -> {
                return yml;
            }
            case ("mysql") -> {
                return mysql;
            }
            case ("redis") -> {
                return redis;
            }
        }
        return false;
    }
}
