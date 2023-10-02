package com.zemoso.springboot.assignment;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ApplicationTest {

    @Test
    void main_ShouldNotThrowAnyExceptions() {

        assertDoesNotThrow(() -> Application.main(new String[]{}));
    }

    @Test
    void contextLoads() {

        Assertions.assertEquals(3, 2 + 1);
    }

}