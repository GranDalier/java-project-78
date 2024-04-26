package hexlet.code.schemas;

public final class NumberSchema implements BaseSchema<Integer> {
    private int lowerBound;
    private int upperBound;
    private boolean isRequired;
    private boolean isPositive;

    public NumberSchema() {
        lowerBound = Integer.MIN_VALUE;
        upperBound = Integer.MAX_VALUE;
        isRequired = false;
        isPositive = false;
    }

    @Override
    public boolean isValid(Integer item) {
        if (isRequired && item == null) {
            return false;
        }
        return item == null || item >= lowerBound && item <= upperBound;
    }

    public NumberSchema range(int begin, int end) {
        lowerBound = (isPositive && begin < 1) ? 1 : begin;
        upperBound = end;
        return this;
    }

    public NumberSchema required() {
        isRequired = true;
        return this;
    }

    public NumberSchema positive() {
        isPositive = true;
        lowerBound = 1;
        return this;
    }

}
