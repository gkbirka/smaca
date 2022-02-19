package gr.smaca.config;

import gr.smaca.common.state.State;

public class ConfigState implements State {
    private Config config;

    public Config getConfig() {
        return config;
    }

    void setConfig(Config config) {
        this.config = config;
    }
}