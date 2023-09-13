package by.potapenko.cryptocurrencywatcher;

import by.potapenko.cryptocurrencywatcher.model.dto.UserDto;
import by.potapenko.cryptocurrencywatcher.model.entity.UserEntity;
import by.potapenko.cryptocurrencywatcher.model.repository.CoinRepository;
import by.potapenko.cryptocurrencywatcher.model.repository.UserRepository;
import by.potapenko.cryptocurrencywatcher.service.CoinService;
import by.potapenko.cryptocurrencywatcher.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private CoinRepository coinRepository;

    @Mock
    private CoinService coinService;

    private static final ModelMapper modelMapper = new ModelMapper();

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void init() {
        userService = new UserService(userRepository,
                coinRepository, coinService, modelMapper);
    }

    @Test
    void whenGetAllInvoked_thenAllTheUsersAreReturned() {
        when(userRepository.findAll()).thenReturn(List.of(kiraUser, ivanUser));
        userService.getAll().forEach(UserDto::getUsername);
        String[] expected = new String[]{kiraUser.getUsername(), ivanUser.getUsername(),};
        Assertions.assertThat(expected).containsExactlyInAnyOrder(kiraUser.getUsername(), ivanUser.getUsername());
    }

    private UserEntity ivanUser = UserEntity.builder()
            .id(1L)
            .username("Ivan")
            .coinPricePerRegistration(BigDecimal.valueOf(123.0))
            .build();
    private UserEntity kiraUser = UserEntity.builder()
            .id(2L)
            .username("Kira")
            .coinPricePerRegistration(BigDecimal.valueOf(4321.0))
            .build();
}