package hexlet.code.schemas;

import java.util.Optional;

public final class StringSchema implements BaseSchema<String> {
    private String expectedContains;
    private int minLength;
    private boolean isRequired;

    public StringSchema() {
        expectedContains = null;
        minLength = 0;
        isRequired = false;
    }

    public boolean isValid(String item) {
        // Turns null value into empty string
        String safeItem = Optional.ofNullable(item).orElse("");

        if (isRequired && safeItem.isEmpty()) {
            return false;
        }
        if (safeItem.length() < minLength) {
            return false;
        }
        return expectedContains == null || safeItem.contains(expectedContains);
    }

    public StringSchema required() {
        isRequired = true;
        minLength = 1;
        return this;
    }

    public StringSchema contains(String str) {
        expectedContains = str;
        return this;
    }

    public StringSchema minLength(int length) {
        if (isRequired && length <= 0) {
            minLength = 1;
            return this;
        }
        if (length < 0) {
            minLength = 0;
            return this;
        }
        minLength = length;
        return this;
    }
}
