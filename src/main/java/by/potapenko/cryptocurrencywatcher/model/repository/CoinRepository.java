package by.potapenko.cryptocurrencywatcher.model.repository;

import by.potapenko.cryptocurrencywatcher.model.enam.Symbol;
import by.potapenko.cryptocurrencywatcher.model.entity.CoinEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CoinRepository extends JpaRepository<CoinEntity, Long> {

    Optional<CoinEntity> findBySymbol(Symbol symbol);

}
