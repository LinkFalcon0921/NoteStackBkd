package org.flintcore.notestack_bkd.services;

import org.flintcore.notestack_bkd.SpringIntegrationTest;
import org.flintcore.notestack_bkd.configurations.GeneralPropertiesConfig;
import org.flintcore.notestack_bkd.configurations.properties.SpringProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * This is the config to append Class beans
 * <code>@(Spring auto configure)</code> annotation + @ExtendWith(SpringExtension.class) + @ContextConfiguration
 * */
/*
@ExtendWith(SpringExtension.class)
@ContextConfiguration(
        classes = GeneralPropertiesConfig.class
)
*/

@SpringIntegrationTest({ GeneralPropertiesConfig.class })
@DataJpaTest
// @TestPropertySource("classpath:application-test.properties")
public class ConfigTest {

    @Autowired
    private SpringProperties properties;

    @Test
    void name() {
        assertNotNull(properties, "Properties should not be null");
        System.out.printf("\n\nproperties() = %s\n", properties);

        assertNotNull(properties.application(), "Properties application should not be null");
        System.out.printf("\n\nproperties.application() = %s\n", properties.application());
    }
}
