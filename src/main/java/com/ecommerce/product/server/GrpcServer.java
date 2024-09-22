package com.ecommerce.product.server;

import com.ecommerce.product.repository.ProductRepository;
import com.ecommerce.product.service.ProductService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GrpcServer {

    @Autowired
    private ProductRepository productRepository;

    private final Integer grpcPort = 8079;

    private Server server;

    @PostConstruct
    public void start() throws IOException {
        server = ServerBuilder.forPort(grpcPort)
                .addService(new ProductService(productRepository))
                .build()
                .start();

        System.out.println("gRPC Server started, listening on port:" + grpcPort);

        // Ensure the server shuts down gracefully
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down gRPC server...");
            GrpcServer.this.stop();
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
