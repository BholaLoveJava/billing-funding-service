package com.world.web.utility;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
public class AssertionsTest {

    @Test
    void assertions_test() {
        Assertions.assertThat("The Lord of the Rings")
                .startsWith("The")
                .contains("Lord")
                .endsWith("Rings");
    }
}
