package hexlet.code.schemas;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import hexlet.code.Validator;

public final class StringSchemaTest {
    private Validator validator;

    @BeforeEach
    public void setUp() {
        validator = new Validator();
    }

    @Test
    @DisplayName("'required' rule works correctly")
    public void testRequired() {
        StringSchema schema = validator.string();
        assertThat(schema.isValid(null)).isTrue();
        assertThat(schema.isValid("")).isTrue();
        assertThat(schema.isValid("abc")).isTrue();

        schema.required();

        assertThat(schema.isValid(null)).isFalse();
        assertThat(schema.isValid("")).isFalse();
        assertThat(schema.isValid("abc")).isTrue();
    }

    @Test
    @DisplayName("'contains' rule works correctly")
    public void testContains() {
        StringSchema schema = validator.string();
        String text = "what does the fox say";

        schema.contains("wha").contains("");

        assertThat(schema.isValid("")).isTrue();
        assertThat(schema.isValid(text)).isTrue();

        schema.contains("").contains("wha");

        assertThat(schema.isValid("")).isFalse();
        assertThat(schema.isValid(text)).isTrue();
    }

    @Test
    @DisplayName("'minLength' rule works correctly")
    public void testMinLength() {
        StringSchema schema = validator.string();
        String text = "what does the fox say";
        final int testLen1 = -10;
        final int testLen2 = 10;

        schema.minLength(testLen2).minLength(testLen1);

        assertThat(schema.isValid("")).isTrue();
        assertThat(schema.isValid(text)).isTrue();

        schema.minLength(testLen1).minLength(testLen2);

        assertThat(schema.isValid("")).isFalse();
        assertThat(schema.isValid(text)).isTrue();
    }

    @Test
    @DisplayName("Rules in different order return the same result")
    public void testRuleComposition() {
        StringSchema schema1 = validator.string();
        StringSchema schema2 = validator.string();
        StringSchema schema3 = validator.string();
        String text1 = "what does the fox say";
        String text2 = "fox say";
        final int testLen = 9;


        schema1.required().minLength(testLen).contains("fox");
        schema2.minLength(testLen).required().contains("fox");
        schema3.minLength(testLen).contains("fox").required();

        // When true
        assertThat(schema1.isValid(text1)).isEqualTo(schema2.isValid(text1));
        assertThat(schema2.isValid(text1)).isEqualTo(schema3.isValid(text1));

        // When false
        assertThat(schema1.isValid(text2)).isEqualTo(schema2.isValid(text2));
        assertThat(schema2.isValid(text2)).isEqualTo(schema3.isValid(text2));
    }
}
