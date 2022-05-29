package lt.testing.drools.tool.model;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Map;

public class DroolsData {

    private final Map<String, Object> fields;

    @JsonCreator
    public DroolsData(Map<String, Object> fields) {
        this.fields = fields;
    }

    public Map<String, Object> getFields() {
        return fields;
    }
}
