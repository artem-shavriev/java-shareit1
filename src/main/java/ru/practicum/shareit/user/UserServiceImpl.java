package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMemoryRepository userMemoryRepository;

    @Override
    public UserDto addUser(UserDto userDtoRequest) {
        return userMemoryRepository.addUser(userDtoRequest);
    }

    @Override
    public List<UserDto> getUsers() {
        return userMemoryRepository.getUsers();
    }

    @Override
    public UserDto getUserById(Integer userId) {
        return userMemoryRepository.getUserById(userId);
    }

    @Override
    public UserDto updateUser(UserDto userDtoRequest, Integer userId) {
        return userMemoryRepository.updateUser(userDtoRequest, userId);
    }

    @Override
    public UserDto deleteUser(Integer userId) {
        return userMemoryRepository.deleteUser(userId);
    }
}
