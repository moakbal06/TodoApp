package com.example.todoapp.payload;


import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;


@ToString
public class JwtResponse {
    @ApiModelProperty(notes = "Token",name="token")
    private String token;
    @ApiModelProperty(notes = "Bearer",name="type")
    private String type = "Bearer";
    @ApiModelProperty(notes = "User Id",name="id")
    private String id;
    @ApiModelProperty(notes = "User Name",name="username")
    private String username;
    @ApiModelProperty(notes = "User email",name="email")
    private String email;

    public JwtResponse(String accessToken, String id, String username, String email) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public String getAccessToken() {
        return token;
    }

    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
