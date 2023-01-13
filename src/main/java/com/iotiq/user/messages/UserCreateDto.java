package com.iotiq.user.messages;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class UserCreateDto extends UserUpdateDto {
    @NotEmpty
    private String password;
}
