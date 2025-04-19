package org.flintcore.notestack_bkd.repositories;

import org.flintcore.notestack_bkd.entities.User;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Annotation to enable package containing Repositories and Entities.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@EnableJpaRepositories(basePackageClasses = IUserRepository.class)
@EntityScan(basePackageClasses = User.class)
@EnableTransactionManagement
@AutoConfigureTestEntityManager
public @interface EnableRepository {
}
