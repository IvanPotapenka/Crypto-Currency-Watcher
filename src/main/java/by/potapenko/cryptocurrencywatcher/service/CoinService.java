package by.potapenko.cryptocurrencywatcher.service;

import by.potapenko.cryptocurrencywatcher.model.dto.CoinDetailDto;
import by.potapenko.cryptocurrencywatcher.model.dto.CoinDto;
import by.potapenko.cryptocurrencywatcher.model.enam.Symbol;
import by.potapenko.cryptocurrencywatcher.model.entity.CoinEntity;
import by.potapenko.cryptocurrencywatcher.model.repository.CoinRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CoinService {

    private final CoinRepository coinRepository;
    private final CryptoService cryptoService;

    private static final Logger LOGGER = LoggerFactory.getLogger(CoinEntity.class);
    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public List<CoinDto> getAll() {
        return coinRepository.findAll().stream()
                .map(this::convertToCoinDto)
                .toList();
    }

    @Transactional
    public void updatePrice(List<CoinEntity> coins) {
        for (CoinEntity coin : coins) {
            BigDecimal price = cryptoService.getPrice(coin.getId());
            coin.getUsers().forEach(userEntity ->
                    this.checkUpdatePrice(price, userEntity.getCoinPricePerRegistration(),
                            userEntity.getUsername(), userEntity.getCoin().getSymbol().name()));
            coin.setPriceUsd(price);
            coinRepository.save(coin);
        }
    }

    @Transactional(readOnly = true)
    public Optional<CoinDetailDto> getBySymbol(String symbol) {
        Optional<CoinEntity> coin = coinRepository.findBySymbol(Symbol.valueOf(symbol));
        if (coin.isPresent()) {
            CoinEntity coinEntity = coin.get();
            return Optional.of(convertToCoinDetailDto(coinEntity));
        }
        return Optional.empty();
    }

    public void checkUpdatePrice(BigDecimal price, BigDecimal registeredPrice,
                                 String username, String symbol) {
        BigDecimal percentageChange = price.subtract(registeredPrice)
                .multiply(new BigDecimal(100))
                .divide(registeredPrice, 2, RoundingMode.HALF_UP);

        if (percentageChange.abs().compareTo(new BigDecimal(1)) > 0) {
            LOGGER.warn("Price change for {} more than 1% since registration by user {}: {}%", symbol, username, percentageChange);
        }
    }


    private CoinDto convertToCoinDto(CoinEntity coin) {
        return modelMapper.map(coin, CoinDto.class);
    }

    private CoinDetailDto convertToCoinDetailDto(CoinEntity coin) {
        return modelMapper.map(coin, CoinDetailDto.class);
    }
}
