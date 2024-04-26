package hexlet.code.schemas;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import hexlet.code.Validator;

public final class NumberSchemaTest {
    private Validator validator;

    @BeforeEach
    public void setUp() {
        validator = new Validator();
    }

    @Test
    @DisplayName("'required' rule works correctly")
    public void testRequired() {
        NumberSchema schema = validator.number();
        assertThat(schema.isValid(null)).isTrue();
        assertThat(schema.isValid(1)).isTrue();

        schema.required();

        assertThat(schema.isValid(null)).isFalse();
        assertThat(schema.isValid(1)).isTrue();
    }

    @Test
    @DisplayName("'positive' rule works correctly")
    public void testPositive() {
        NumberSchema schema = validator.number();
        final int testNum1 = -10;
        final int testNum2 = 10;

        assertThat(schema.isValid(testNum1)).isTrue();
        assertThat(schema.isValid(testNum2)).isTrue();

        schema.positive();

        assertThat(schema.isValid(testNum1)).isFalse();
        assertThat(schema.isValid(testNum2)).isTrue();
    }

    @Test
    @DisplayName("'range' rule works correctly")
    public void testRange() {
        NumberSchema schema = validator.number();
        final int begin1 = 2;
        final int end1 = 5;
        final int begin2 = 4;
        final int end2 = 10;

        final int testNum1 = 3;
        final int testNum2 = 7;

        schema.required().range(begin2, end2).range(begin1, end1);

        assertThat(schema.isValid(testNum1)).isTrue();
        assertThat(schema.isValid(testNum2)).isFalse();

        schema.required().range(begin1, end1).range(begin2, end2);

        assertThat(schema.isValid(testNum1)).isFalse();
        assertThat(schema.isValid(testNum2)).isTrue();
    }

    @Test
    @DisplayName("Rules in different order return the same result")
    public void testRuleComposition() {
        NumberSchema schema1 = validator.number();
        NumberSchema schema2 = validator.number();
        NumberSchema schema3 = validator.number();
        final int begin = 4;
        final int end = 10;

        final int testNum1 = 7;
        final int testNum2 = -2;

        schema1.required().positive().range(begin, end);
        schema2.positive().required().range(begin, end);
        schema3.positive().range(begin, end).required();

        // When true
        assertThat(schema1.isValid(testNum1)).isEqualTo(schema2.isValid(testNum1));
        assertThat(schema2.isValid(testNum1)).isEqualTo(schema3.isValid(testNum1));

        // When false
        assertThat(schema1.isValid(testNum2)).isEqualTo(schema2.isValid(testNum2));
        assertThat(schema2.isValid(testNum2)).isEqualTo(schema3.isValid(testNum2));
    }
}
