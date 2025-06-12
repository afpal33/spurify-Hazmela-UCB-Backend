package com.hazmelaucb.edge_server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class GatewayLoggingFilter implements GlobalFilter, Ordered {

  private static final Logger LOGGER = LoggerFactory.getLogger(GatewayLoggingFilter.class);

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    String path = exchange.getRequest().getPath().toString();
    String method = exchange.getRequest().getMethod().name();
    LOGGER.info("Solicitud entrante: {} {}", method, path);
    return chain.filter(exchange)
        .doOnSuccess(aVoid -> LOGGER.info("Solicitud procesada: {} {}", method, path));
  }

  @Override
  public int getOrder() {
    return -1; // Alta prioridad
  }
}