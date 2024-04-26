package hexlet.code.schemas;

import static java.util.Objects.isNull;

import java.util.function.Predicate;

import java.util.Map;
import java.util.HashMap;

public class BaseSchema<T> {
    private final Map<String, Predicate<T>> schema;
    private boolean isNullable;

    protected BaseSchema() {
        schema = new HashMap<>();
        isNullable = true;
    }

    public final boolean isValid(T value) {
        if (isNull(value)) {
            return isNullable;
        }
        for (Predicate<T> rule : schema.values()) {
            if (!rule.test(value)) {
                return false;
            }
        }
        return true;
    }

    protected final void addRule(String label, Predicate<T> rule) {
        schema.put(label, rule);
    }

    protected final void setNotNullable() {
        isNullable = false;
    }
}
