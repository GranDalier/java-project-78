package hexlet.code.schemas;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import hexlet.code.Validator;

import java.util.Map;
import java.util.HashMap;

public final class MapSchemaTest {
    private Validator validator;

    @BeforeEach
    public void setUp() {
        validator = new Validator();
    }

    @Test
    @DisplayName("'required' rule works correctly")
    public void testRequired() {
        MapSchema schema = validator.map();
        assertThat(schema.isValid(null)).isTrue();

        schema.required();

        assertThat(schema.isValid(null)).isFalse();
        assertThat(schema.isValid(new HashMap<>())).isTrue();
    }

    @Test
    @DisplayName("'sizeof' rule works correctly")
    public void testSizeof() {
        MapSchema schema = validator.map();
        final int testSize1 = 1;
        final int testSize2 = 2;


        Map<String, String> testMap1 = Map.of("key", "value");
        Map<String, String> testMap2 = Map.of(
                "key", "value",
                "another key", "another value"
        );

        schema.required().sizeof(testSize2).sizeof(testSize1);

        assertThat(schema.isValid(testMap1)).isTrue();
        assertThat(schema.isValid(testMap2)).isFalse();

        schema.required().sizeof(testSize1).sizeof(testSize2);

        assertThat(schema.isValid(testMap1)).isFalse();
        assertThat(schema.isValid(testMap2)).isTrue();
    }

    @Test
    @DisplayName("Rules in different order return the same result")
    public void testRuleComposition() {
        MapSchema schema1 = validator.map();
        MapSchema schema2 = validator.map();
        final int testSize = 2;

        schema1.required().sizeof(testSize);
        schema2.sizeof(testSize).required();

        Map<String, String> testMap1 = Map.of("key", "value");
        assertThat(schema1.isValid(testMap1))
                .isEqualTo(schema2.isValid(testMap1));

        Map<String, String> testMap2 = Map.of(
                "key", "value",
                "another key", "another value"
        );
        assertThat(schema1.isValid(testMap2))
                .isEqualTo(schema2.isValid(testMap2));
    }
}
