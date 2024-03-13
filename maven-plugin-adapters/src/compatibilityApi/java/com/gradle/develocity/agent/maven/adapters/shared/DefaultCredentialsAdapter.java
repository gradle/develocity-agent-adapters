package com.gradle.develocity.agent.maven.adapters.shared;

import com.gradle.develocity.agent.maven.adapters.CredentialsAdapter;
import com.gradle.develocity.agent.maven.adapters.Property;

public class DefaultCredentialsAdapter implements CredentialsAdapter {

    private final Property<String> username;
    private final Property<String> password;

    public DefaultCredentialsAdapter(Property<String> username, Property<String> password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String getUsername() {
        return username.get();
    }

    @Override
    public void setUsername(String username) {
        this.username.set(username);
    }

    @Override
    public String getPassword() {
        return password.get();
    }

    @Override
    public void setPassword(String password) {
        this.password.set(password);
    }
}
