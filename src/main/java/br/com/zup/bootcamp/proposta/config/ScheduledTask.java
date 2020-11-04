package br.com.zup.bootcamp.proposta.config;

import br.com.zup.bootcamp.proposta.service.CartaoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ScheduledTask {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTask.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private final CartaoService cartaoService;

    public ScheduledTask(CartaoService cartaoService) {
        this.cartaoService = cartaoService;
    }

    @Scheduled(fixedRateString = "${periodicidade-schedue.criar-cartao}")
    public void executaCartaoAsync() throws InterruptedException {
        logger.info("Executando executaCartaoAsync() {}", dateFormat.format(new Date()));
        cartaoService.verificarCartao();
        logger.info("executaCartaoAsync() terminada as {}", dateFormat.format(new Date()));
    }
}
