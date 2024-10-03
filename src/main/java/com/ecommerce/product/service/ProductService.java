package com.ecommerce.product.service;

import com.ecommerce.common.CreateInventoryRequest;
import com.ecommerce.common.InventoryResponse;
import com.ecommerce.common.InventoryRequest;
import com.ecommerce.common.ProductResponse;
import com.ecommerce.product.model.Product;
import com.ecommerce.product.model.Variant;
import com.ecommerce.product.repository.ProductRepository;
import com.ecommerce.common.InventoryServiceGrpc.InventoryServiceBlockingStub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private final ProductRepository productRepository;

    @Autowired
    private InventoryServiceBlockingStub inventoryServiceBlockingStub;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public ProductResponse validateProduct(UUID productId, UUID variantId) {
        Optional<Product> optionalProduct = productRepository
                .findByIdAndVariants_Id(productId, variantId);

        ProductResponse.Builder productResponse;
        if (optionalProduct.isEmpty()) {
            productResponse = ProductResponse.newBuilder()
                    .setError(true)
                    .setValid(false)
                    .setMessage("Product not found");
        } else {
            productResponse = ProductResponse.newBuilder()
                    .setValid(true);
        }

        return productResponse.build();
    }

    public void addProduct(Product product) {
        product.setId(UUID.randomUUID());
        product.setCreatedAt(new Date());
        product.setUpdatedAt(new Date());

        for (Variant variant : product.getVariants()) {
            variant.setId(UUID.randomUUID());
            variant.setCreatedAt(new Date());
            variant.setUpdatedAt(new Date());
        }

        Product savedProduct = productRepository.save(product);
        for (Variant variant : savedProduct.getVariants()) {
            InventoryRequest inventory = InventoryRequest.newBuilder()
                    .setProductId(savedProduct.getId().toString())
                    .setVariantId(variant.getId().toString())
                    .setSku("Test")
                    .setLocation("Test")
                    .setStock(50)
                    .build();

            CreateInventoryRequest inventoryRequest = CreateInventoryRequest.newBuilder()
                    .setInventory(inventory)
                    .build();

            InventoryResponse response = inventoryServiceBlockingStub.createInventory(inventoryRequest);
            System.out.println("Inventory created with ID: " + response.getInventoryId());
        }
    }

}
