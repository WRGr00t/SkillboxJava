package com.example.blogapp.api.request;

import com.example.blogapp.entity.User;
import com.example.blogapp.service.CryptoHelper;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.validation.constraints.*;
import java.security.InvalidKeyException;

@Getter
@AllArgsConstructor
public class RegisterRequest {
    @JsonProperty("e_mail")
    @NotEmpty()
    @Email(message = "email should be a valid email")
    private String email;
    @NotNull
    /*@Pattern(
            regexp = ".{6,}",
            message = "Пароль короче 6-ти символов"
    )*/
    //@Size(min = 6, message = "Пароль короче 6-ти символов")
    @Length(min = 6, max = 255, message = "Пароль короче 6-ти символов")
    private String password;
    @NotBlank(message = "Name cannot be empty")
    private String name;
    @NotNull
    private String captcha;

    @JsonProperty("captcha_secret")
    @NotNull
    private String captchaSecret;

    public RegisterRequest(User user) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.captcha = user.getCode().getCode();
        this.captchaSecret = CryptoHelper.encrypt(user.getCode().getCode());
    }
}