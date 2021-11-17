package com.example.training.service;

import com.example.training.model.UserEntity;
import com.example.training.repository.UserRepository;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static com.example.training.model.Role.ADMIN;
import static com.example.training.model.Role.USER;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

    private final UserRepository repository = mock(UserRepository.class);
    private final UserService service = new UserServiceImpl(repository);

    String PASSWORD = RandomStringUtils.randomAlphanumeric(50);

    UserEntity userFirst = new UserEntity(1, RandomStringUtils.randomAlphanumeric(5), RandomStringUtils.randomAlphanumeric(5), PASSWORD, ADMIN);
    UserEntity userSecond = new UserEntity(2, RandomStringUtils.randomAlphanumeric(5), RandomStringUtils.randomAlphanumeric(5), PASSWORD, USER);

    @Test
    void getById() {
        when(repository.findById(1)).thenReturn(Optional.of(userFirst));
        assertThat(Optional.of(userFirst), is(service.getById(1)));
    }

    @Test
    void putCreat() {
        when(repository.findOneByLastName(userFirst.getLastName())).thenReturn(Optional.empty());
        when(repository.insert(userFirst)).thenReturn(1);
        assertThat(1, is(service.put(userFirst)));
    }

    @Test
    void putUpdate() {
        when(repository.findOneByLastName(userFirst.getLastName())).thenReturn(Optional.of(userFirst));
        when(repository.update(userFirst)).thenReturn(1);
        assertThat(1, is(service.put(userFirst)));
    }

    @Test
    void getAll() {
        when(repository.findAll()).thenReturn(List.of(userFirst, userSecond));
        assertThat(List.of(userFirst, userSecond), is(service.getAll()));
    }

    @Test
    void delete() {
        when(repository.delete(1)).thenReturn(1);
        assertThat(true, is(service.delete(1)));
    }
}
