package com.orchestrator.saga.saga.expose.web;

import com.orchestrator.saga.saga.service.AcountSagaService;
import lombok.RequiredArgsConstructor;
import org.openapitools.api.OrchestratorApi;
import org.openapitools.model.CreateAccountSagaRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class AcountSagaImpl implements OrchestratorApi {
    private final AcountSagaService acountSagaService;
    private static final Logger logger = LoggerFactory.getLogger(AcountSagaImpl.class);
    @Override
    public Mono<ResponseEntity<Void>> createCreditCard(Mono<CreateAccountSagaRequest> createAccountSagaRequest,
                                                       ServerWebExchange exchange) {
        logger.info("Starting orchestrator-saga: createCreditCard");
        return createAccountSagaRequest.flatMap(c -> acountSagaService.createAcount(c));
    }

}
