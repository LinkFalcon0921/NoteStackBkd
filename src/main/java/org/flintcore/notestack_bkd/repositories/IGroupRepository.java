package org.flintcore.notestack_bkd.repositories;

import org.flintcore.notestack_bkd.entities.Group;
import org.flintcore.notestack_bkd.entities.User;
import org.flintcore.notestack_bkd.entities.wrappers.GroupDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IGroupRepository extends JpaRepository<Group, String> {

    String DEFAULT_GROUP_NAME = "My notes";

    // Grupos de un usuario con conteo de notas
    @Query("SELECT g, COUNT(g.notes) FROM Group g JOIN g.members m " +
            "WHERE m.id = :userId GROUP BY g.id")
    Iterable<GroupDetails> findGroupsWithNoteCountByUser(@Param("userId") String userId);

    @Query("SELECT g FROM Group g WHERE g.owner.id = :userId AND g.name = :groupName")
    Optional<Group> getGroupByName(
            @Param("userId") String userId,
            @Param("groupName") String groupName
    );

    default Optional<Group> getDefaultGroupOf(String userId) {
        return getGroupByName(userId, DEFAULT_GROUP_NAME);
    }

    // Buscar miembros no agregados
    // TODO Manejar a futuro el uso amistades
    @Query("""
            SELECT u FROM User u WHERE u NOT IN (
            SELECT m.user FROM Group g JOIN g.members m WHERE g.id = :groupId)
    """)
    Iterable<User> findNonMembers(@Param("groupId") String groupId);
}
