package iiprwc_webshop.model.http;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class SignUpRequest {
    @NotNull @Email @Length(min = 5, max = 50)
    private String email;

    @NotNull @Length(min = 8, max = 64)
    private String password;

    @NotNull
    @Length(min = 2, max = 64)
    private String firstName;


    @Length(max = 12)
    private String preposition;

    @NotNull
    @Length(min = 2, max = 64)
    private String lastName;


    public SignUpRequest() {

    }

    public SignUpRequest(String email, String password, String firstName, String preposition, String lastName) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.preposition = preposition;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPreposition() {
        return preposition;
    }

    public void setPreposition(String preposition) {
        this.preposition = preposition;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
