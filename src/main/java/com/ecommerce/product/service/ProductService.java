package com.ecommerce.product.service;

import com.ecommerce.product.model.Product;
import com.ecommerce.product.model.Variant;
import com.ecommerce.product.repository.ProductRepository;
import inventory.InventoryProto.InventoryResponse;
import inventory.InventoryProto.InventoryRequest;
import inventory.InventoryProto.CreateInventoryRequest;
import inventory.InventoryServiceGrpc.InventoryServiceBlockingStub;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import product.ProductProto;
import product.ProductServiceGrpc.ProductServiceImplBase;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService extends ProductServiceImplBase {

    @Autowired
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Autowired
    private InventoryServiceBlockingStub inventoryServiceBlockingStub;

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public void validateProduct(
            ProductProto.ProductRequest productRequest,
            StreamObserver<ProductProto.ProductResponse> responseStreamObserver
    ) {
        UUID productId = UUID.fromString(productRequest.getProductId());
        UUID variantId = UUID.fromString(productRequest.getVariantId());

        // TODO: refer if we can find using product_id and internal variant_id
        // else fetch only product and check for variant in it's variants
        Optional<Product> optionalProduct = productRepository
                .findByIdAndVariants_Id(productId, variantId);

        ProductProto.ProductResponse productResponse;

        if(optionalProduct.isEmpty()) {
            productResponse = ProductProto.ProductResponse.newBuilder()
                    .setError(true)
                    .setValid(false)
                    .setMessage("Product not found")
                    .build();
        } else {
            productResponse = ProductProto.ProductResponse.newBuilder()
                    .setValid(true)
                    .build();
        }

        responseStreamObserver.onNext(productResponse);
        responseStreamObserver.onCompleted();
    }

    public void addProduct(Product product) {
        product.setId(UUID.randomUUID());
        product.setCreatedAt(new Date());
        product.setUpdatedAt(new Date());

        for(Variant variant: product.getVariants()) {
            variant.setId(UUID.randomUUID());
            variant.setCreatedAt(new Date());
            variant.setUpdatedAt(new Date());
        }

        Product savedProduct = productRepository.save(product);

        for(Variant variant: product.getVariants()) {
            InventoryRequest inventory = InventoryRequest.newBuilder()
                    .setProductId(product.getId().toString())
                    .setVariantId(variant.getId().toString())
                    .setSku("Test")
                    .setLocation("Test")
                    .setStock(50)
                    .build();

            CreateInventoryRequest inventoryRequest = CreateInventoryRequest.newBuilder()
                    .setInventory(inventory)
                    .build();

            InventoryResponse response =
                    inventoryServiceBlockingStub.createInventory(inventoryRequest);

            System.out.println("Inventory created with ID: " + response.getInventoryId());
        }
    }

}
