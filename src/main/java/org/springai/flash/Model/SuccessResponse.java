package org.springai.flash.Model;

public class SuccessResponse {
    private int statusCode;
    private String message;
    private String token;
    private Users user;

    public SuccessResponse(String message, String token, int statusCode) {
        this.message = message;
        this.token = token;
        this.statusCode = statusCode;
    }

    public SuccessResponse(Users user,int statusCode){
        this.user = user;
        this.statusCode = statusCode;
    }
    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }


}
