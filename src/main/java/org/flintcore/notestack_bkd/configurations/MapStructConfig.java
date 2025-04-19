package org.flintcore.notestack_bkd.configurations;

import org.flintcore.notestack_bkd.mappers.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapStructConfig {

    @Bean
    public INoteMapper noteMapper() {
        return new INoteMapperImpl();
    }

    @Bean
    public IGroupMapper groupMapper() {
        return new IGroupMapperImpl();
    }

    @Bean
    public IUserMapper userMapper() {
        return new IUserMapperImpl();
    }

    @Bean
    public IAuthProviderMapper authProviderMapper() {
        return new IAuthProviderMapperImpl();
    }

    @Bean
    public IReminderMapper reminderMapper() {
        return new IReminderMapperImpl();
    }

    @Bean
    public IOauthWrapperMapper oauthWrapperMapper() {
        return new IOauthWrapperMapperImpl();
    }

    @Bean
    public IRefreshTokenMapper refreshTokenMapper() {
        return new IRefreshTokenMapperImpl();
    }
}
