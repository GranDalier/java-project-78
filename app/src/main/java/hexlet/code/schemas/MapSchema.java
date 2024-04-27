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
        addRule("sizeof", map -> map.size() == size);
        return this;
    }

    public MapSchema shape(Map<?, BaseSchema<String>> schemas) {
        addRule("shape", map -> isDeepValid(map, schemas));
        return this;
    }

    private boolean isDeepValid(Map<?, ?> map, Map<?, BaseSchema<String>> schemas) {
        return schemas.entrySet().stream()
                .allMatch(entry -> {
                    var requiredKey = entry.getKey();
                    var mapValue = map.get(requiredKey) instanceof String ? (String) map.get(requiredKey) : null;
                    var schema = entry.getValue();
                    return map.containsKey(requiredKey) && schema.isValid(mapValue);
                });
    }

}
