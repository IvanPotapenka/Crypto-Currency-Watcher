package by.potapenko.cryptocurrencywatcher.controller;

import by.potapenko.cryptocurrencywatcher.model.dto.CoinDetailDto;
import by.potapenko.cryptocurrencywatcher.model.dto.CoinDto;
import by.potapenko.cryptocurrencywatcher.service.CoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/coins")
@RequiredArgsConstructor
public class CoinController {

    private final CoinService coinService;

    @GetMapping
    public List<CoinDto> getCoins() {
        return coinService.getAll();
    }

    @GetMapping("{symbol}")
    public ResponseEntity<CoinDetailDto> getCurrencyByCoin(@PathVariable String symbol) {
        return coinService.getBySymbol(symbol)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
