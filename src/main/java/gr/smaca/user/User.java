package gr.smaca.user;

public class User {
    private String firstName;
    private String lastName;
    private String epc;

    public User(String firstName, String lastName, String epc) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.epc = epc;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEpc() {
        return epc;
    }

    public void setEpc(String epc) {
        this.epc = epc;
    }
}