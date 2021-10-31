package mx.ralvarez20.udp.models;

public class MacAddress {

    private String name, mac_addr;

    public MacAddress(String name, String mac_addr) {
        this.name = name;
        this.mac_addr = mac_addr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMac_addr() {
        return mac_addr;
    }

    public void setMac_addr(String mac_addr) {
        this.mac_addr = mac_addr;
    }
}
