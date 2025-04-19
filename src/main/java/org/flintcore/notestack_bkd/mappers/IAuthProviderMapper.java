package org.flintcore.notestack_bkd.mappers;

import org.flintcore.notestack_bkd.dtos.LocalProviderDTO;
import org.flintcore.notestack_bkd.dtos.OAuthProviderDTO;
import org.flintcore.notestack_bkd.entities.auths.LocalAuthProvider;
import org.flintcore.notestack_bkd.entities.auths.OAuthProvider;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = {IUserMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IAuthProviderMapper {

    @Mapping(source = "providerId", target = "accessToken")
    @Mapping(source = "providerName", target = "providerName")
    OAuthProviderDTO toDto(OAuthProvider entity);

    @Mapping(source = "password", target = "password")
    @Mapping(source = "email", target = "email")
    LocalProviderDTO toDto(LocalAuthProvider entity);

    OAuthProvider toEntity(OAuthProviderDTO dto);

    LocalAuthProvider toEntity(LocalProviderDTO dto);
}
