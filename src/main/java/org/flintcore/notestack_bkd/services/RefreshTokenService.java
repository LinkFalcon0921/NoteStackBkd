package org.flintcore.notestack_bkd.services;

import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.flintcore.notestack_bkd.dtos.UserDTO;
import org.flintcore.notestack_bkd.entities.auths.RefreshToken;
import org.flintcore.notestack_bkd.mappers.IUserMapper;
import org.flintcore.notestack_bkd.repositories.IRefreshTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final IRefreshTokenRepository refreshTokenRepository;

    private final IUserMapper userMapper;

    public Optional<RefreshToken> getRefreshToken(@Email String email) {
        return refreshTokenRepository.findTokenByUser(email);
    }

    @Transactional(readOnly = true)
    public Optional<UserDTO> getUserByToken(String token) {
        return refreshTokenRepository.findTokenByToken(token)
                .map(RefreshToken::getUser)
                .map(userMapper::toDTO);
    }

    public boolean existsTokenBy(UserDTO user) {
        return refreshTokenRepository.existsTokenByUser(
                userMapper.toEntity(user)
        );
    }

    public boolean existsToken(String token) {
        return refreshTokenRepository.existsTokenByToken(token);
    }
}
