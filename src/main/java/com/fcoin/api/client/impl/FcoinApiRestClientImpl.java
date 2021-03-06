package com.fcoin.api.client.impl;

import com.fcoin.api.client.FcoinApiRestClient;
import com.fcoin.api.client.FcoinApiService;
import com.fcoin.api.client.domain.*;
import com.fcoin.api.client.domain.enums.*;
import com.fcoin.api.client.domain.reqs.PlaceOrderRequest;

import java.math.BigDecimal;
import java.util.Set;

import static com.fcoin.api.client.FcoinApiServiceGenerator.createService;
import static com.fcoin.api.client.FcoinApiServiceGenerator.executeSync;

/**
 * created by jacky. 2018/7/20 8:58 PM
 */
public class FcoinApiRestClientImpl implements FcoinApiRestClient {

    private FcoinApiService service;
    private String apiKey;
    private String apiSecret;

    public FcoinApiRestClientImpl(String apiKey, String apiSecret) {
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
        service = createService(FcoinApiService.class, apiKey, apiSecret);
    }

    @Override
    public Long serverTime() {
        return executeSync(service.serverTime()).getData();
    }

    @Override
    public Set<String> currencies() {
        return executeSync(service.currencies()).getData();
    }

    @Override
    public Set<Symbol> symbols() {
        return executeSync(service.symbols()).getData();
    }

    @Override
    public Ticker ticker(String symbol) {
        return executeSync(service.ticker(symbol)).getData();
    }


    @Override
    public Depth depth(DepthLevel level, String symbol) {
        return executeSync(service.depth(level.name(),symbol)).getData();
    }

    @Override
    public Set<Trade> trades(String symbol, String before, Integer limit) {
        return executeSync(service.trades(symbol, before, limit)).getData();
    }

    @Override
    public Set<Candle> candles(Resolution resolution, String symbol, String before, Integer limit) {
        return executeSync(service.candles(resolution.name(),symbol,before,limit)).getData();
    }

    @Override
    public Set<Asset> balance() {
        return executeSync(service.balance()).getData();
    }


    @Override
    public String place(String symbol, OrderSide side, OrderType type, BigDecimal price, BigDecimal amount) {
        PlaceOrderRequest req = new PlaceOrderRequest();
        req.setSymbol(symbol);
        req.setSide(side.name());
        req.setType(type.name());
        req.setPrice(price.stripTrailingZeros().toPlainString());
        req.setAmount(amount.stripTrailingZeros().toPlainString());
//        return executeSync(service.place(symbol, side.name(), type.name(), price.toPlainString(), amount.toPlainString())).getData();
        return executeSync(service.place(req)).getData();
    }

    @Override
    public Set<Order> orders(String symbol, OrderState state, String before, String after, Integer limit) {
        return executeSync(service.orders(symbol.toLowerCase(), state==null?null:state.getCode(), before, after, limit==null?null:limit.toString())).getData();
    }

    @Override
    public Order get(String orderId) {
        return executeSync(service.get(orderId)).getData();
    }

    @Override
    public void cancel(String orderId) {
         executeSync(service.cancel(orderId)).getData();
    }

    @Override
    public Set<MatchResult> matchResult(String orderId) {
        return executeSync(service.matchResult(orderId)).getData();
    }
}
