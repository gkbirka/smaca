package gr.smaca.user;

public class User {
    private final String firstName;
    private final String lastName;
    private final String epc;

    public User(String firstName, String lastName, String epc) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.epc = epc;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEpc() {
        return epc;
    }
}