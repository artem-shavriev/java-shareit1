package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.DuplicatedDataException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.service.IdGenerator;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Slf4j
@Repository
public class UserMemoryRepository extends IdGenerator {
    private final UserMapper userMapper;
    Map<Integer, User> usersMap = new HashMap<>();

    public UserDto addUser(UserDto userDtoRequest) {
        if (userDtoRequest.getEmail() == null || userDtoRequest.getEmail().isEmpty()) {
            log.error("В запросе не найден имел, У пользователя должен быть имейл.");
            throw new ValidationException("У пользователя должен быть имейл.");
        }

        emailValidator(userDtoRequest.getEmail());

        User user = userMapper.mapToUser(userDtoRequest);

        user.setId(getNextId(usersMap));

        usersMap.put(user.getId(), user);
        log.info("Пользователь с id " + user.getId() + " добавлен");
        return userMapper.mapToUserDto(user);
    }

    public List<UserDto> getUsers() {
        List<UserDto> userDtoList = usersMap.values().stream().map(user -> userMapper.mapToUserDto(user)).toList();
        log.info("Все пользователи получены.");
        return userDtoList;
    }

    public UserDto getUserById(Integer userId) {
        return userMapper.mapToUserDto(usersMap.get(userId));
    }

    public UserDto updateUser(UserDto userDtoRequest, Integer userId) {
        if (!usersMap.containsKey(userId)) {
            log.error("пользователь с id {} не найден.", userId);
            throw new RuntimeException("Пользовтеь с id " + userId + " не найден.");
        }

        String email = userDtoRequest.getEmail();

        if (email != null) {
            emailValidator(userDtoRequest.getEmail());
        }

        User user = usersMap.get(userId);

        user = userMapper.updateUserFields(user, userDtoRequest);

        usersMap.put(userId, user);
        log.info("Пользователь с id {} обновлен", userId);

        return userMapper.mapToUserDto(user);
    }

    public UserDto deleteUser(Integer userId) {
        UserDto user = userMapper.mapToUserDto(usersMap.get(userId));
        usersMap.remove(userId);
        return  user;
    }

    public void emailValidator(String email) {
        Pattern pattern = Pattern.compile("\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*\\.\\w{2,4}");
        Matcher matcher = pattern.matcher(email);
        boolean matches = matcher.matches();

        if (!matches) {
            throw new ValidationException("Имейл в неверном формате.");
        }

        Collection<User> allUsersList = usersMap.values();

        allUsersList.forEach(user -> {
            if (user.getEmail().equals(email)) {
                log.error("Имейл пользователя: {} уже существует.", email);
                throw new DuplicatedDataException("Имейл пользователя уже существует.");
            }
        });
    }
}
