package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.User;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ItemDtoUpdate {
    private Integer id;
    private User owner;
    private String name;
    private String description;
    private Boolean available;
    private ItemRequest request;
}
