package io.muserver.sample.conf;

import java.security.Principal;
import java.util.List;

public class MyUser implements Principal {
    private final String name;
    private final List<String> roles;
    public MyUser(String name, List<String> roles) {
        this.name = name;
        this.roles = roles;
    }
    @Override
    public String getName() {
        return name;
    }
    public boolean isInRole(String role) {
        return roles.contains(role);
    }
}