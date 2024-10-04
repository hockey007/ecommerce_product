package com.ecommerce.product.server;

import com.ecommerce.product.service.GrpcProductService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GrpcServer {

    @Value("${server.grpc.port}")
    private Integer grpcPort;

    private Server server;

    @PostConstruct
    public void start() throws IOException {
        server = ServerBuilder.forPort(grpcPort)
                .addService(new GrpcProductService())
                .build()
                .start();

        System.out.println("gRPC Server started, listening on port:" + grpcPort);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down gRPC server...");
            stop();
        }));
    }

    @PreDestroy
    public void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    public void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

}
