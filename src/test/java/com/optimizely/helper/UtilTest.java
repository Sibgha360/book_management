package com.optimizely.helper;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UtilTest {
    @Test
    public void isParameterValid_WhenNull_ShouldReturnFalse() {
        //ACT
        boolean b = Util.validateParam(null);

        // ASSERT
        assertThat(b).isFalse();
    }


    @ParameterizedTest
    @CsvSource({"2sd", "3456", "sdf", "!"})
    public void isParameterValid_WhenNotNullNotEmpty_ShouldReturnFalse(String param) {
        //ACT
        boolean b = Util.validateParam(param);

        // ASSERT
        assertThat(b).isTrue();
    }

    @Test
    public void isParameterValid_WhenEmpty_ShouldReturnFalse() {
        //ACT
        boolean b = Util.validateParam(new String());

        // ASSERT
        assertThat(b).isFalse();
    }
}
