package com.iotiq.user.domain;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Embeddable
public class Person implements Serializable {
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    private String phoneNumber;
    @Email
    @NotEmpty
    private String email;
    private String webPage;
}
