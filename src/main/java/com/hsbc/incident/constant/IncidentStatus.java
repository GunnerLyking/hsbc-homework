package com.hsbc.incident.constant;

/**
 * 事件状态枚举类
 */
public enum IncidentStatus {
    PENDING("Pending"),
    IN_PROGRESS("In Progress"),
    RESOLVED("Resolved"),
    CLOSED("Closed");

    private final String value;

    IncidentStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static boolean valid(String value) {
        for (IncidentStatus status : IncidentStatus.values()) {
            if (status.toString().equals(value)) {
                return true;
            }
        }
        return false;
    }
}

