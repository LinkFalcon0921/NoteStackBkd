package org.flintcore.notestack_bkd.entities.wrappers;

import org.flintcore.notestack_bkd.entities.EAuthProvider;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

public sealed abstract class OauthWrapper permits GithubOAuthUser, GoogleAuthWrapper {

    public static final String NAME_KEY = "name",
            EMAIL_KEY = "email";

    protected final OAuth2User oauth2User;
    protected final Map<String, Object> attributes;

    protected OauthWrapper(OAuth2User oauth2User) {
            this.oauth2User = oauth2User;
            this.attributes = oauth2User.getAttributes();
    }

    public abstract String getProviderId();

        /**
         * Represent
         * */
    public String getName() {
        return (String) attributes.getOrDefault(NAME_KEY, attributes.get("login"));
    }

    public String getEmail() {
        // GitHub no siempre devuelve el email en los atributos principales
        return (String) attributes.get(EMAIL_KEY);
    }

    // Factory method para crear instancias según el proveedor
    public static OauthWrapper from(EAuthProvider provider, OAuth2User oAuth2User) {
        return switch (provider) {
            case GOOGLE -> new GoogleAuthWrapper(oAuth2User);
            case GITHUB -> new GithubOAuthUser(oAuth2User);
            default -> throw new IllegalArgumentException("Proveedor no soportado: " + provider);
        };
    }
}
