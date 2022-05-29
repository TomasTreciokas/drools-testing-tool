package lt.testing.drools.tool.model;

import com.fasterxml.jackson.annotation.JsonCreator;


public class DroolPackageRequest {

    private final Long taskId;
    private final String name;
    private final String taskDescription;
    private final String taskStatus;

    @JsonCreator
    public DroolPackageRequest(Long taskId, String name, String taskDescription, String taskStatus) {
        this.taskId = taskId;
        this.name = name;
        this.taskDescription = taskDescription;
        this.taskStatus = taskStatus;
    }

    public Long getTaskId() {
        return taskId;
    }

    public String getName() {
        return name;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    @Override
    public String toString() {
        return "DroolPackageRequest{" +
                "taskId=" + taskId +
                ", name='" + name + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", taskStatus='" + taskStatus + '\'' +
                '}';
    }
}
