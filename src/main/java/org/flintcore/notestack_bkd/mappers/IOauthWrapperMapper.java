package org.flintcore.notestack_bkd.mappers;

import jakarta.annotation.Nullable;
import org.flintcore.notestack_bkd.entities.EAuthProvider;
import org.flintcore.notestack_bkd.entities.auths.OAuthProvider;
import org.flintcore.notestack_bkd.entities.wrappers.GithubOAuthUser;
import org.flintcore.notestack_bkd.entities.wrappers.GoogleAuthWrapper;
import org.flintcore.notestack_bkd.entities.wrappers.OauthWrapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface IOauthWrapperMapper {

    @Nullable
    default OauthWrapper fromObject(EAuthProvider provider, OAuth2User user) {
        return switch (provider) {
            case GOOGLE -> toGoogle(user);
            case GITHUB -> toGithub(user);
            default -> null;
        };
    }

    GoogleAuthWrapper toGoogle(OAuth2User user);
    GithubOAuthUser toGithub(OAuth2User user);

//    @Mappings({
//            @Mapping(source = "providerName", target = "providerName"),
//            @Mapping(source = "email", target = "providerName"),
//    })
    OAuthProvider toEntity(OauthWrapper wrapper);
}
