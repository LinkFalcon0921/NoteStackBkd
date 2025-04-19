package org.flintcore.notestack_bkd.repositories;

import org.flintcore.notestack_bkd.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IUserRepository extends JpaRepository<User, String> {
    List<User> findByUsernameContaining(String username);
}
