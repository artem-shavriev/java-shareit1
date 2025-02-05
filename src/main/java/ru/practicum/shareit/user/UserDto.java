package ru.practicum.shareit.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private Integer id;
    private String name;
    private String email;

    public boolean hasEmail() {
        return email != null && !email.isEmpty();
    }

    public boolean hasName() {
        return name != null && !name.isEmpty();
    }
}
