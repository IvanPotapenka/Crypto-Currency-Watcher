package by.potapenko.cryptocurrencywatcher.service;

import by.potapenko.cryptocurrencywatcher.model.dto.CoinDetailDto;
import by.potapenko.cryptocurrencywatcher.model.dto.UserDto;
import by.potapenko.cryptocurrencywatcher.model.enam.Symbol;
import by.potapenko.cryptocurrencywatcher.model.entity.CoinEntity;
import by.potapenko.cryptocurrencywatcher.model.entity.UserEntity;
import by.potapenko.cryptocurrencywatcher.model.repository.CoinRepository;
import by.potapenko.cryptocurrencywatcher.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CoinRepository coinRepository;
    private final CoinService coinService;
    private final ModelMapper modelMapper;

    @Transactional
    public UserDto notify(String username, String symbol) {
        UserDto userDto = new UserDto();
        if (username != null && symbol != null) {
            if (userRepository.findByUsername(username).isEmpty()) {
                Optional<CoinEntity> coin = coinRepository.findBySymbol(Symbol.valueOf(symbol));
                UserEntity user = UserEntity.builder()
                        .username(username)
                        .coin(coinService.getBySymbol(symbol)
                                .map(this::convertToCoinEntity).orElseThrow())
                        .coinPricePerRegistration(coin.get().getPriceUsd())
                        .build();
                coin.get().addUsers(user);
                userDto = convertToUserDto(user);
                userDto.setId(userRepository.save(user).getId());
            }
        }
        return userDto;
    }

    @Transactional(readOnly = true)
    public List<UserDto> getAll() {
        return userRepository.findAll()
                .stream()
                .map(this::convertToUserDto)
                .toList();
    }

    private CoinEntity convertToCoinEntity(CoinDetailDto coin) {
        return modelMapper.map(coin, CoinEntity.class);
    }

    private UserDto convertToUserDto(UserEntity user) {
        return modelMapper.map(user, UserDto.class);
    }
}
