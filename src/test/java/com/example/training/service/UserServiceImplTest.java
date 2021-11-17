package com.example.training.service;

import com.example.training.model.Role;
import com.example.training.model.UserEntity;
import com.example.training.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceImplTest {

    private final UserRepository userDao = mock(UserRepository.class);
    private final UserService service = new UserServiceImpl(userDao);

    UserEntity userFirst = new UserEntity(1, "Name-1", "LastName-1", "$2a$12$sO4f2lJ9Yh8sBhIDGU3YTOswBgK23yAd1uOHnkJQ1n4AN0U.D4KsO", Role.USER);
    UserEntity userSecond = new UserEntity(2, "Name-2", "LastName-2", "$2a$12$sO4f2lJ9Yh8sBhIDGU3YTOswBgK23yAd1uOHnkJQ1n4AN0U.D4KsO", Role.ADMIN);
    UserEntity userThree = new UserEntity(3, "Name-3", "LastName-3", "$2a$12$sO4f2lJ9Yh8sBhIDGU3YTOswBgK23yAd1uOHnkJQ1n4AN0U.D4KsO", Role.USER);
    UserEntity userThreeUpdate = new UserEntity(3, "Name-4", "LastName-3", "$2a$12$sO4f2lJ9Yh8sBhIDGU3YTOswBgK23yAd1uOHnkJQ1n4AN0U.D4Ks4", Role.ADMIN);

    @BeforeEach
    public void setUp() {
        userDao.delete(1);
        userDao.delete(2);
        userDao.delete(3);
        userDao.insert(userFirst);
        userDao.insert(userSecond);
    }

    @Test
    void getById() {
        when(service.getById(3)).thenReturn(java.util.Optional.of(new UserEntity(3, "Name-3", "LastName-3", "$2a$12$sO4f2lJ9Yh8sBhIDGU3YTOswBgK23yAd1uOHnkJQ1n4AN0U.D4KsO", Role.USER)));
        Optional<UserEntity> user = service.getById(3);
        assertThat("Name-3", is(user.get().getFirstName()));
        assertThat("LastName-3", is(user.get().getLastName()));
        assertThat("$2a$12$sO4f2lJ9Yh8sBhIDGU3YTOswBgK23yAd1uOHnkJQ1n4AN0U.D4KsO", is(user.get().getPassword()));
        assertThat(Role.USER, is(user.get().getRole()));
    }

    @Test
    void creat() {
        service.put(userThree);
        when(service.getAll()).thenReturn(List.of(userFirst, userSecond, userThree));
        assertThat(List.of(userFirst, userSecond, userThree), is(service.getAll()));
    }

    @Test
    void update() {
        service.put(userThree);
        service.put(userThreeUpdate);
        when(service.getAll()).thenReturn(List.of(userFirst, userSecond, userThreeUpdate));
        assertThat(List.of(userFirst, userSecond, userThreeUpdate), is(service.getAll()));
    }


    @Test
    void getAll() {
        when(service.getAll()).thenReturn(List.of(userFirst, userSecond));
        assertThat(List.of(userFirst, userSecond), is(service.getAll()));
    }

    @Test
    void delete() {
        service.delete(2);
        when(service.getAll()).thenReturn(List.of(userFirst));
        assertThat(List.of(userFirst), is(service.getAll()));
    }
}
