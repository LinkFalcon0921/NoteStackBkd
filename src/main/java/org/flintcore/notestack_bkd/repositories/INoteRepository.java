package org.flintcore.notestack_bkd.repositories;

import org.flintcore.notestack_bkd.entities.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface INoteRepository extends JpaRepository<Note, String> {

    // Notas de un usuario (paginadas)
    @Query("SELECT n FROM Note n WHERE n.owner.id = :userId ORDER BY n.updatedAt DESC")
    Page<Note> findByOwnerId(@Param("userId") String userId, Pageable pageable);

    // Notas recientes en grupos (para WebSocket)
    @Query("SELECT n FROM Note n WHERE n.group.id IN :groupIds " +
            "AND n.updatedAt > :since " +
            "ORDER BY n.updatedAt DESC")
    Page<Note> findRecentGroupNotes(
            @Param("groupIds") List<String> groupIds,
            @Param("since") LocalDateTime since,
            Pageable pageable
    );

    // Full-text search (Turso usa SQLite FTS5)
    @Query("SELECT n FROM Note n WHERE n.content LIKE CONCAT('%', :content, '%')")
    List<Note> fullTextSearch(@Param("content") String content, Pageable pageable);
}