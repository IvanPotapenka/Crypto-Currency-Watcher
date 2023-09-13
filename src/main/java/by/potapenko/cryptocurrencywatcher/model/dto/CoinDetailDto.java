package by.potapenko.cryptocurrencywatcher.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class CoinDetailDto implements Serializable {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("symbol")
    private String symbol;
    @JsonProperty("price_usd")
    private BigDecimal priceUSD;
}
