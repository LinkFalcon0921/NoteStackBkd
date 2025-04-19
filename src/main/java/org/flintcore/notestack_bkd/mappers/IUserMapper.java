package org.flintcore.notestack_bkd.mappers;

import org.flintcore.notestack_bkd.dtos.UserDTO;
import org.flintcore.notestack_bkd.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface IUserMapper {

    // Mapeo básico de User a UserDTO
    UserDTO toDTO(User user);
    User toEntity(UserDTO user);
}
