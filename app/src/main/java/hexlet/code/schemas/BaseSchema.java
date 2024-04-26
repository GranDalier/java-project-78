package hexlet.code.schemas;

public interface BaseSchema<T> {
    boolean isValid(T item);
}
