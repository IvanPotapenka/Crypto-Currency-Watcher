package by.potapenko.cryptocurrencywatcher.config;

import by.potapenko.cryptocurrencywatcher.model.entity.CoinEntity;
import by.potapenko.cryptocurrencywatcher.model.repository.CoinRepository;
import by.potapenko.cryptocurrencywatcher.service.CoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ApplicationStartupListener {
    private final CoinRepository coinRepository;
    private final CoinService coinService;

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReadyEvent() {
        List<CoinEntity> coins = coinRepository.findAll();
        while (true) {
            try {
                coinService.updatePrice(coins);
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
