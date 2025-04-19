package org.flintcore.notestack_bkd.mappers;

import org.flintcore.notestack_bkd.dtos.GroupDTO;
import org.flintcore.notestack_bkd.entities.Group;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN,
        typeConversionPolicy = ReportingPolicy.WARN
)
public interface IGroupMapper {

    @Mapping(source = "owner.id", target = "ownerId")
    GroupDTO toDTO(Group group);

    @Mapping(source = "ownerId", target = "owner.id")
    @Mapping(target = "members", ignore = true)
    @Mapping(target = "notes", ignore = true)
    Group toEntity(GroupDTO groupDTO);
}
