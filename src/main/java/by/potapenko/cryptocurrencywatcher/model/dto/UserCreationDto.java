package by.potapenko.cryptocurrencywatcher.model.dto;

import by.potapenko.cryptocurrencywatcher.model.enam.Symbol;
import lombok.Data;

@Data
public class UserCreationDto {

    private String username;
    private Symbol symbol;
}
