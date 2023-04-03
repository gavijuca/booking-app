package io.muserver.sample.conf;

import io.muserver.rest.UserPassAuthenticator;

import java.security.Principal;
import java.util.List;
import java.util.Map;

public class MyUserPassAuthenticator implements UserPassAuthenticator {
    private final Map<String, Map<String, List<String>>> usersToPasswordToRoles;

    public MyUserPassAuthenticator(Map<String, Map<String, List<String>>> usersToPasswordToRoles) {
        this.usersToPasswordToRoles = usersToPasswordToRoles;
    }

    @Override
    public Principal authenticate(String username, String password) {
        Principal principal = null;
        Map<String, List<String>> user = usersToPasswordToRoles.get(username);
        if (user != null) {
            List<String> roles = user.get(password);
            if (roles != null) {
                principal = new MyUser(username, roles);
            }
        }
        return principal;
    }
}