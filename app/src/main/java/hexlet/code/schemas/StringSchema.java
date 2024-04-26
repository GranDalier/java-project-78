package hexlet.code.schemas;

import java.util.Optional;

public final class StringSchema {
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
            throw new IllegalArgumentException(
                    "When string is required, minimum length must be greater than 0 (was '%s')"
                            .formatted(length)
            );
        }
        if (length < 0) {
            throw new IllegalArgumentException(
                    "Minimum length must be positive number (was '%s')"
                            .formatted(length)
            );
        }
        minLength = length;
        return this;
    }
}
