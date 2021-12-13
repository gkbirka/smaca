package gr.smaca.user;

public class User {
    private String name;
    private String surname;
    private String epc;

    public User(String name, String surname, String epc) {
        this.name = name;
        this.surname = surname;
        this.epc = epc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEpc() {
        return epc;
    }

    public void setEpc(String epc) {
        this.epc = epc;
    }
}