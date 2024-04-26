package hexlet.code;

import static org.assertj.core.api.Assertions.assertThat;

import hexlet.code.schemas.StringSchema;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public final class ValidatorTest {

    @Test
    @DisplayName("Validator works correctly")
    public void testStringValidator() {
        var v = new Validator();
        StringSchema strSchema = v.string();

        // Testing .isValid(String str) method before .required()
        assertThat(strSchema.isValid("")).isTrue();
        assertThat(strSchema.isValid(null)).isTrue();

        // Testing .isValid(String str) method after .required()
        strSchema.required();

        assertThat(strSchema.isValid("")).isFalse();
        assertThat(strSchema.isValid(null)).isFalse();
        assertThat(strSchema.isValid("hexlet")).isTrue();

        // Testing method .contains(String str)
        String text = "what does the fox say";
        boolean actualContainsEmpty = strSchema.contains("").isValid(text);
        assertThat(actualContainsEmpty).isTrue();

        boolean actualContains1 = strSchema.contains("wh").isValid(text);
        assertThat(actualContains1).isTrue();

        boolean actualContains2 = strSchema.contains("what").isValid(text);
        assertThat(actualContains2).isTrue();

        boolean actualContains3 = strSchema.contains("whatthe").isValid(text);
        assertThat(actualContains3).isFalse();

        // Testing if recent rules are persist
        boolean actualPersist = strSchema.isValid("what does the fox say");
        assertThat(actualPersist).isFalse();

        // Testing method .minLength(int length)
        // Testing rule chain
        final int testLen1 = 5;
        StringSchema schema2 = v.string().required().minLength(testLen1).contains("hex");
        assertThat(schema2.isValid("hexlet")).isTrue();

        // Testing if same rule chain uses the most recent one in the chain
        StringSchema schema1 = v.string();
        final int testLen2 = 10;
        final int testLen3 = 4;
        boolean actualRewrites = schema1.minLength(testLen2).minLength(testLen3).isValid("Hexlet");
        assertThat(actualRewrites).isTrue();
    }
}
