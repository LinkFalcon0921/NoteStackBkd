package org.flintcore.notestack_bkd.entities.wrappers;

import org.springframework.security.oauth2.core.user.OAuth2User;

public final class GithubOAuthUser extends OauthWrapper {


    private static final String ID_KEY = "id",
            AVATAR_URL = "avatar_url";

    public GithubOAuthUser(OAuth2User oAuth2User) {
            super(oAuth2User);
        }

        @Override
        public String getProviderId() {
            return String.valueOf(attributes.get(ID_KEY)); // ID único de GitHub
        }


        // Campos específicos de GitHub
        public String getAvatarUrl() {
            return (String) attributes.get(AVATAR_URL);
        }
}
