package org.flintcore.notestack_bkd;

import org.flintcore.notestack_bkd.configurations.properties.SpringProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Objects;

@SpringBootApplication
public class NoteStackBkdApplication {

    public static void main(String[] args) {
        var context = SpringApplication.run(NoteStackBkdApplication.class, args);

        var springProperties = context.getBean(SpringProperties.class);

        assert Objects.nonNull(springProperties.application());
    }

}
