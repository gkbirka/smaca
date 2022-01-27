package gr.smaca.user;

public class User {
    private final String epc;
    private final String firstName;
    private final String lastName;

    public User(String epc, String firstName, String lastName) {
        this.epc = epc;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getEpc() {
        return epc;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}