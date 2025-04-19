package org.flintcore.notestack_bkd.entities.wrappers;

import org.springframework.security.oauth2.core.user.OAuth2User;

public final class GoogleAuthWrapper extends OauthWrapper{
        public GoogleAuthWrapper(OAuth2User oAuth2User) {
            super(oAuth2User);
        }

        @Override
        public String getProviderId() {
            return (String) attributes.get("sub"); // ID único de Google
        }

        // Campos específicos de Google
        public String getProfilePicture() {
            return (String) attributes.get("picture");
        }
}
