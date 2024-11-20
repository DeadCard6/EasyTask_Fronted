package com.example.ucompensareasytaskas.api.model;

public class ApiResponse {
    private boolean success;
    private String message;
    private Long userId;

    // Constructor
    public ApiResponse(boolean success, String message, long userId) {
        this.success = success;
        this.message = message;
        this.userId= userId;
    }

    // Getters and setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
