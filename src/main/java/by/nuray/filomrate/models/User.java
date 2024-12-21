package by.nuray.filomrate.models;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class User {

    private int id;


    @NotEmpty(message = "Name should not be empty")
    @Email
    private String email;

    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z0-9_]+$",message = "Login can only contain letters, numbers, and underscores")
    private String login;

    private String name;

    @Past(message = "Date of birth cannot be in the future")
    private LocalDate birthday;


    public User() {
    }

    public User(int id, String email, String login, String name, LocalDate birthday) {
        this.id = id;
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        if (name == null) {
            return login;
        }
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
}
