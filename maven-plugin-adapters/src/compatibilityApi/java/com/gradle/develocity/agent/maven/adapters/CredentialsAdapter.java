package com.gradle.develocity.agent.maven.adapters;

public interface CredentialsAdapter {

    String getUsername();

    void setUsername(String username);

    String getPassword();

    void setPassword(String password);

}
