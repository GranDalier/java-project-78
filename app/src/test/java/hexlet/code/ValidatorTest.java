package hexlet.code;

import static org.assertj.core.api.Assertions.assertThat;

import hexlet.code.schemas.MapSchema;
import hexlet.code.schemas.NumberSchema;
import hexlet.code.schemas.StringSchema;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public final class ValidatorTest {

    @Test
    @DisplayName("Validator works correctly")
    public void testValidator() {
        var v = new Validator();
        assertThat(v.string()).isInstanceOf(StringSchema.class);
        assertThat(v.number()).isInstanceOf(NumberSchema.class);
        assertThat(v.map()).isInstanceOf(MapSchema.class);
    }
}
