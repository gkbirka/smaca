package gr.smaca.user;

import gr.smaca.common.state.State;

public class UserState implements State {
    private User user;

    UserState() {
    }

    public User getUser() {
        return user;
    }

    void setUser(User user) {
        this.user = user;
    }
}