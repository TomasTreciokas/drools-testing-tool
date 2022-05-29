package lt.testing.drools.tool.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public class DroolsPackageResponse {

    private String name;

    private byte[] content;

    @JsonCreator
    public DroolsPackageResponse(String name, byte[] content) {
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
