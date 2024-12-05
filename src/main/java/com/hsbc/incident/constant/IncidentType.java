package com.hsbc.incident.constant;

/**
 * 事件类型枚举类
 */
public enum IncidentType {
    WARNING("Warning"),
    ERROR("Error"),
    INFO("Info");

    private final String value;

    IncidentType(String value) {
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
        for (IncidentType type : IncidentType.values()) {
            if (type.toString().equals(value)) {
                return true;
            }
        }
        return false;
    }
}
