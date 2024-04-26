package hexlet.code.schemas;

import java.util.Map;

public final class MapSchema extends BaseSchema<Map<?, ?>> {

    public MapSchema() {
        super();
    }

    public MapSchema required() {
        setNotNullable();
        return this;
    }

    public MapSchema sizeof(int size) {
        addRule("sizeOf", map -> map.size() == size);
        return this;
    }
}
