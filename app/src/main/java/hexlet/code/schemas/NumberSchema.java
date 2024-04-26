package hexlet.code.schemas;

public final class NumberSchema extends BaseSchema<Integer> {

    public NumberSchema() {
        super();
    }

    public NumberSchema required() {
        setNotNullable();
        return this;
    }

    public NumberSchema range(int begin, int end) {
        addRule("range", num -> num >= begin && num <= end);
        return this;
    }

    public NumberSchema positive() {
        addRule("positive", num -> num > 0);
        return this;
    }

}
