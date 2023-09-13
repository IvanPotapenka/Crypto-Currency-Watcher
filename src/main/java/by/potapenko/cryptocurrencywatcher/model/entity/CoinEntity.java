package by.potapenko.cryptocurrencywatcher.model.entity;

import by.potapenko.cryptocurrencywatcher.model.enam.Symbol;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.EAGER;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "coins")
public class CoinEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "symbol", length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private Symbol symbol;

    @Column(name = "price_usd", nullable = false)
    private BigDecimal priceUsd;

    @Builder.Default
    @OneToMany(mappedBy = "coin", fetch = EAGER)
    private List<UserEntity> users = new ArrayList<>();

    public void addUsers(UserEntity user) {
        users.add(user);
        user.setCoin(this);
    }
}
