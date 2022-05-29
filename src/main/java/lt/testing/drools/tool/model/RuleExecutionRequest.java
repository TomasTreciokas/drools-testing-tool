package lt.testing.drools.tool.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public class RuleExecutionRequest {

    private final String packageName;
    private final DroolsData droolsData;

    @JsonCreator
    public RuleExecutionRequest(String packageName, DroolsData droolsData) {
        this.packageName = packageName;
        this.droolsData = droolsData;
    }

    public String getPackageName() {
        return packageName;
    }

    public DroolsData getDroolsData() {
        return droolsData;
    }

    @Override
    public String toString() {
        return "RuleExecutionRequest{" +
                "packageName='" + packageName + '\'' +
                ", droolsData=" + droolsData +
                '}';
    }
}
