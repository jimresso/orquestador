package com.orchestrator.saga.saga.service.Impl;

import com.orchestrator.saga.saga.model.CheckDebtorsRequest;
import com.orchestrator.saga.saga.service.AcountSagaService;
import lombok.RequiredArgsConstructor;
import org.openapitools.model.AccountRequest;
import org.openapitools.model.CreateAccountSagaRequest;
import org.openapitools.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AcountSagaServiceImpl implements AcountSagaService {
    private final WebClient.Builder webClientBuilder;
    private static final Logger logger = LoggerFactory.getLogger(AcountSagaServiceImpl.class);
    @Override
    public Mono<ResponseEntity<Void>> createAcount(CreateAccountSagaRequest c) {
        String dni = c.getCustomer().getDni();

        return findAccountsByDni(dni)
                .map(accounts -> accounts.stream()
                        .map(AccountRequest::getCustomerId)
                        .distinct()
                        .toList())
                .flatMap(this::checkDebtsForCustomers)
                .flatMap(hasDebt -> {
                    if (Boolean.TRUE.equals(hasDebt)) {
                        logger.warn("Customer with DNI {} has debts. Aborting Saga flow.", dni);
                        return Mono.just(ResponseEntity.badRequest().build());
                    }
                    return createCustomer(c.getCustomer())
                            .doOnSuccess(resp -> logger.info("Customer created. Proceeding to create account."))
                            .then(createAccount(c.getAccount())
                                    .then(Mono.fromCallable(() -> {
                                        logger.info("Account successfully created for customer {}", c.getCustomer().getDni());
                                        return ResponseEntity.ok().<Void>build();
                                    }))
                                    .onErrorResume(ex -> {
                                        logger.error("Error during account creation step: {}", ex.getMessage(), ex);
                                        return deleteCustomerByCustomerId(c.getCustomer().getCustomerId())
                                                .thenReturn(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).<Void>build());
                                    })
                            );
                });
    }
    private Mono<List<AccountRequest>> findAccountsByDni(String dni) {
        return webClientBuilder.build()
                .post()
                .uri("http://localhost:8086/api/accounts/by-dni")
                .bodyValue(Collections.singletonMap("dni", dni))
                .retrieve()
                .bodyToFlux(AccountRequest.class)
                .collectList();
    }
    private Mono<Boolean> checkDebtsForCustomers(List<String> customerIds) {
        CheckDebtorsRequest request = new CheckDebtorsRequest();
        request.setCustomerIds(customerIds);

        return webClientBuilder.build()
                .post()
                .uri("http://localhost:8087/debtors/find")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Boolean.class);
    }
    private Mono<Void> createCustomer(Customer customer) {
        return webClientBuilder.build()
                .post()
                .uri("http://localhost:8085/api/customers")
                .bodyValue(customer)
                .retrieve()
                .toBodilessEntity()
                .then();
    }
    private Mono<AccountRequest> createAccount(AccountRequest accountRequest) {
        return webClientBuilder.build()
                .post()
                .uri("http://localhost:8086/api/accounts")
                .bodyValue(accountRequest)
                .retrieve()
                .bodyToMono(AccountRequest.class);
    }

    private Mono<Void> deleteCustomerByCustomerId(String customerId) {
        return webClientBuilder.build()
                .delete()
                .uri("http://localhost:8085/api/delete/{customerId}", customerId)
                .retrieve()
                .bodyToMono(Void.class);
    }

}
