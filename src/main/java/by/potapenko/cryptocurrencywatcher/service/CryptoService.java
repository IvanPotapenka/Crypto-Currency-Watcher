package by.potapenko.cryptocurrencywatcher.service;

import by.potapenko.cryptocurrencywatcher.model.dto.CoinDetailDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Service
public class CryptoService {

    public BigDecimal getPrice(Long id) {
        String url = "https://api.coinlore.net/api/ticker/?id=" + id;
        RestTemplate restTemplate = new RestTemplate();
        CoinDetailDto[] response = restTemplate.getForObject(url, CoinDetailDto[].class);
        return response[0].getPriceUSD();
    }
}
