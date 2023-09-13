--liquibase formatted sql
--changeSet abed:001-02

INSERT INTO coins(id, symbol, price_usd)
VALUES (90, 'BTC', 0);
INSERT INTO coins(id, symbol, price_usd)
VALUES (80, 'ETH', 0);
INSERT INTO coins(id, symbol, price_usd)
VALUES (48543, 'SOL', 0);

