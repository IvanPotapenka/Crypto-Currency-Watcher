package by.potapenko.cryptocurrencywatcher.model.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserDto {
    private Long id;
    private String username;
    private BigDecimal registeredPrice;
}
