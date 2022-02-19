package gr.smaca.reader;

public class Tag {
    private final String epc;

    Tag(String epc) {
        this.epc = epc;
    }

    public String getEpc() {
        return epc;
    }
}