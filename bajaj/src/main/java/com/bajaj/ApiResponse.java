package com.bajaj;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApiResponse {
    @JsonProperty("is_success")
    private boolean isSuccess;
    
    @JsonProperty("official_email")
    private String officialEmail;
    
    private Object data;
    
    private String error;

    public ApiResponse(boolean isSuccess, String officialEmail, Object data) {
        this.isSuccess = isSuccess;
        this.officialEmail = officialEmail;
        this.data = data;
    }

    public ApiResponse(boolean isSuccess, String officialEmail, Object data, String error) {
        this.isSuccess = isSuccess;
        this.officialEmail = officialEmail;
        this.data = data;
        this.error = error;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getOfficialEmail() {
        return officialEmail;
    }

    public void setOfficialEmail(String officialEmail) {
        this.officialEmail = officialEmail;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
