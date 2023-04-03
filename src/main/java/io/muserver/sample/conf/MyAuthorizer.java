package io.muserver.sample.conf;

import io.muserver.rest.Authorizer;

import java.security.Principal;

public class MyAuthorizer implements Authorizer {
    @Override
    public boolean isInRole(Principal principal, String role) {
        if (principal == null) {
            return false;
        }
        MyUser user = (MyUser)principal;
        return user.isInRole(role);
    }
}