package hexlet.code.schemas;

public final class StringSchema extends BaseSchema<String> {

    public StringSchema() {
        super();
    }

    public StringSchema required() {
        setNotNullable();
        addRule("required", str -> !str.isEmpty());
        return this;
    }

    public StringSchema contains(String subStr) {
        addRule("contains", str -> str.contains(subStr));
        return this;
    }

    public StringSchema minLength(int length) {
        addRule("minLength", str -> str.length() >= length);
        return this;
    }
}
