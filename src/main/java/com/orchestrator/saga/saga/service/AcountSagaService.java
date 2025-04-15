package com.orchestrator.saga.saga.service;

import org.openapitools.model.CreateAccountSagaRequest;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface AcountSagaService {
    Mono<ResponseEntity<Void>> createAcount(CreateAccountSagaRequest c);
}
