package hexlet.code;

import static org.assertj.core.api.Assertions.assertThat;

import hexlet.code.schemas.NumberSchema;
import hexlet.code.schemas.StringSchema;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public final class ValidatorTest {

    @Test
    @DisplayName("String validator works correctly")
    public void testStringValidator() {
        var v = new Validator();
        StringSchema strSchema = v.string();

        // Testing .isValid(String item) method before .required()
        assertThat(strSchema.isValid("")).isTrue();
        assertThat(strSchema.isValid(null)).isTrue();

        // Testing .isValid(String item) method after .required()
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
        assertThat(schema2.isValid("hexl")).isFalse();

        // Testing if same rule chain uses the most recent one in the chain
        StringSchema schema1 = v.string();
        final int testLen2 = 10;
        final int testLen3 = 4;
        boolean actualRewrites = schema1.minLength(testLen2).minLength(testLen3).isValid("Hexlet");
        assertThat(actualRewrites).isTrue();

        // Testing negative value length in method .minLength(int length)
        StringSchema negativeSchema = v.string();
        final int testLen4 = -1;
        boolean actual1 = negativeSchema.minLength(testLen4).isValid("");
        assertThat(actual1).isTrue();

        final int testLen5 = -10;
        boolean actual2 = negativeSchema.required().minLength(testLen5).isValid("");
        assertThat(actual2).isFalse();
    }

    @Test
    @DisplayName("Number validator works correctly")
    public void testNumberValidator() {
        var v = new Validator();
        NumberSchema numSchema = v.number();

        // Testing .isValid(Integer item) method before .required()
        assertThat(numSchema.isValid(null)).isTrue();
        assertThat(numSchema.positive().isValid(null)).isTrue();

        // Testing .isValid(Integer item) method after .required()
        // Testing .positive()
        // Testing if recent rules are persist
        numSchema.required();

        assertThat(numSchema.isValid(null)).isFalse();
        final int testNum1 = 10;
        assertThat(numSchema.isValid(testNum1)).isTrue();
        final int testNum2 = -10;
        assertThat(numSchema.isValid(testNum2)).isFalse();
        final int testNum3 = 0;
        assertThat(numSchema.isValid(testNum3)).isFalse();

        // Testing method .range(int begin, int end)
        final int lowerBound1 = 5;
        final int upperBound1 = 10;
        numSchema.range(lowerBound1, upperBound1);

        final int testNum4 = 5;
        assertThat(numSchema.isValid(testNum4)).isTrue();

        final int testNum5 = 10;
        assertThat(numSchema.isValid(testNum5)).isTrue();

        final int testNum6 = 4;
        assertThat(numSchema.isValid(testNum6)).isFalse();

        final int testNum7 = 11;
        assertThat(numSchema.isValid(testNum7)).isFalse();

        // Testing rule chain
        final int lowerBound2 = -15;
        final int upperBound2 = 15;
        NumberSchema schema2 = v.number().required().positive().range(lowerBound2, upperBound2);
        final int testNum8 = 7;
        assertThat(schema2.isValid(testNum8)).isTrue();
        final int testNum9 = -11;
        assertThat(schema2.isValid(testNum9)).isFalse();
        final int testNum10 = 21;
        assertThat(schema2.isValid(testNum10)).isFalse();

        // Testing if same rule chain uses the most recent one in the chain
        NumberSchema schema1 = v.number();
        final int testNum11 = -5;
        boolean actualRewrites = schema1
                .range(lowerBound1, upperBound1).range(lowerBound2, upperBound2).isValid(testNum11);
        assertThat(actualRewrites).isTrue();
    }
}
