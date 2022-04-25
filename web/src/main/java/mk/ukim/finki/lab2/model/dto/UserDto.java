package mk.ukim.finki.lab2.model.dto;

import lombok.Data;

@Data
public class UserDto {
    private String username;
    private String password;

    public void User(){

    }

    public void User(String username, String password){
        this.username = username;
        this.password = password;
    }
}