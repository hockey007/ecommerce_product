package com.ecommerce.product.service;

import com.ecommerce.common.ProductRequest;
import com.ecommerce.common.ProductResponse;
import com.ecommerce.common.ProductServiceGrpc.ProductServiceImplBase;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;

import static java.util.UUID.fromString;

public class GrpcProductService extends ProductServiceImplBase {

    @Autowired
    private ProductService productService;

    public void validateProduct(
            ProductRequest productRequest,
            StreamObserver<ProductResponse> responseStreamObserver
    ) {
        ProductResponse productResponse = productService.validateProduct(
                fromString(productRequest.getProductId()), fromString(productRequest.getVariantId()));
        responseStreamObserver.onNext(productResponse);
        responseStreamObserver.onCompleted();
    }

}
