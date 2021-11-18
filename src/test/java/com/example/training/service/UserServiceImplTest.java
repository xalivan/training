package com.example.training.service;

import com.example.training.model.UserEntity;
import com.example.training.repository.UserRepository;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static com.example.training.utils.UserEntityGenerator.generatorUser;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

    private final UserRepository repository = mock(UserRepository.class);
    private final UserService service = new UserServiceImpl(repository);

    @Test
    void getById() {
        UserEntity userEntity = generatorUser();
        when(repository.findById(1)).thenReturn(Optional.of(userEntity));
        assertThat(service.getById(1).get(), is(userEntity));
    }

    @Test
    void putCreat() {
        UserEntity userEntity = generatorUser();
        when(repository.findOneByLastName(userEntity.getLastName())).thenReturn(Optional.empty());
        when(repository.insert(userEntity)).thenReturn(1);
        assertThat(service.put(userEntity), is(1));
    }

    @Test
    void putUpdate() {
        UserEntity userEntity = generatorUser();
        when(repository.findOneByLastName(userEntity.getLastName())).thenReturn(Optional.of(userEntity));
        when(repository.update(userEntity)).thenReturn(1);
        assertThat(service.put(userEntity), is(1));
    }

    @Test
    void getAll() {
        UserEntity userEntity_1 = generatorUser();
        UserEntity userEntity_2 = generatorUser();
        when(repository.findAll()).thenReturn(List.of(userEntity_1, userEntity_2));
        assertThat(service.getAll(), containsInAnyOrder(userEntity_1, userEntity_2));
    }

    @Test
    void delete() {
        when(repository.delete(1)).thenReturn(1);
        assertThat(service.delete(1), is(true));
    }
}
