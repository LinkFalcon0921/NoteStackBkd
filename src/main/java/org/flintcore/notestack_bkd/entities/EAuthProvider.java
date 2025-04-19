package org.flintcore.notestack_bkd.entities;

public enum EAuthProvider {
    LOCAL, GOOGLE, GITHUB;

    /**
     * Parse the provider by a format valid for Spring Registration.
     * */
    public String asRegistrationId() {
        return name().toLowerCase();
    }
}
