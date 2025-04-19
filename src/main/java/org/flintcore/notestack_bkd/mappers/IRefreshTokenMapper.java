package org.flintcore.notestack_bkd.mappers;

import org.flintcore.notestack_bkd.entities.User;
import org.flintcore.notestack_bkd.entities.auths.RefreshToken;
import org.mapstruct.Mapper;

@Mapper
public interface IRefreshTokenMapper {
    RefreshToken with(String token, User user);
}
