package com.sakti.toko.service;

import com.sakti.toko.data.entity.Product;
import com.sakti.toko.data.repository.ProductRepository;
import com.sakti.toko.data.repository.StoreRepository;
import com.sakti.toko.data.repository.UserRepository;
import com.sakti.toko.model.dto.ProductDTO;
import com.sakti.toko.model.dto.UserDTO;
import com.sakti.toko.model.request.AddProductRequest;
import com.sakti.toko.model.request.ApiResponse;
import com.sakti.toko.model.request.UpdateProductRequest;
import com.sakti.toko.service.details.ProductDetailService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductDetailService productDetailService;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    public ApiResponse<List<ProductDTO>> getAllProducts() {
        List<Product> products = productRepository.findAll();

        var result = productDetailService.getListProductDetails(products);

        return new ApiResponse<>(
                true,
                200,
                "Data Found",
                result
        );
    }

    public ApiResponse<ProductDTO> getProductById(long productId) {
        var product = productRepository.findById(productId).orElseThrow();

        var result = productDetailService.getProductDetail(product);

        return new ApiResponse<>(
                true,
                201,
                "Data Found",
                result
        );
    }

    public ApiResponse<ProductDTO> getProductByName(String productName) {
        var product = productRepository.findByName(productName).orElseThrow();

        var result = productDetailService.getProductDetail(product);

        return new ApiResponse<>(
                true,
                201,
                "Data Found",
                result
        );
    }

    @Transactional
    public ApiResponse<ProductDTO> addProduct(AddProductRequest addProductRequest, UserDTO currentUser) {
        var targetUser = userRepository.findById(currentUser.getId());

        if (targetUser.isEmpty())
            return new ApiResponse<>(
                    false,
                    404,
                    "User Not Found",
                    null
            );

        var user = targetUser.get();

        var targetStore = storeRepository.findByUser(user);

        if (targetStore.isEmpty()) {
            return new ApiResponse<>(
                    false,
                    404,
                    "You don't have a store",
                    null
            );
        }

        var store = targetStore.get();

        if (store.getIsSuspended()) {
            return new ApiResponse<>(
                    false,
                    403,
                    "Your store has been suspended",
                    null
            );
        }

        var product = new Product();
        product.setStore(store);
        product.setSku(addProductRequest.getSku());
        product.setName(addProductRequest.getName());
        product.setDescription(addProductRequest.getDescription());
        product.setPrice(addProductRequest.getPrice());
        product.setStock(addProductRequest.getStock());

        productRepository.save(product);
        var  result = productDetailService.getProductDetail(product);

        return new ApiResponse<>(
                true,
                201,
                "Product Created Successfully",
                result
        );
    }

    @Transactional
    public ApiResponse<ProductDTO> updateProduct(long productId, UpdateProductRequest request, UserDTO currentUser) {
        var targetUser = userRepository.findById(currentUser.getId());

        if (targetUser.isEmpty())
            return new ApiResponse<>(
                    false,
                    404,
                    "User not Found",
                    null
            );

        var user = targetUser.get();

        var tagetStore = storeRepository.findByUser(user);

        if (tagetStore.isEmpty())

            return new ApiResponse<>(
                    false,
                    404,
                    "you don't have a store",
                    null
            );

        var targetProduct = productRepository.findById(productId);

        if (targetProduct.isEmpty()) {
            return new ApiResponse<>(
                    false,
                    404,
                    "Product not found",
                    null
            );
        }

        var productToUpdate = targetProduct.get();

        var store = tagetStore.get();

        if (!Objects.equals(productToUpdate.getStore().getId(), store.getId())) {
            return new ApiResponse<>(
                    false,
                    403,
                    "You don't have permission to update this product",
                    null
            );
        }

        if (request.getName() != null)  {
            productToUpdate.setName(request.getName());
        }
        if (request.getSku() != null)  {
            productToUpdate.setSku(request.getSku());
        }
        if (request.getDescription() != null)  {
            productToUpdate.setDescription(request.getDescription());
        }
        if (request.getPrice() != null)  {
            productToUpdate.setPrice(request.getPrice());
        }
        if (request.getStock() != null)  {
            productToUpdate.setStock(request.getStock());
        }

        productRepository.save(productToUpdate);

        var updatedProduct = productRepository.findById(productId).orElseThrow();
        var result = productDetailService.getProductDetail(updatedProduct);

        return new ApiResponse<>(
                true,
                201,
                "Data Updated Successfully",
                result
        );
    }

    @Transactional
    public ApiResponse<ProductDTO> deleteProduct(long productId, UserDTO currentUser) {

        var targetProduct = productRepository.findById(productId);

        if (targetProduct.isEmpty()) {
            return new ApiResponse<>(
                    false,
                    404,
                    "Product not found",
                    null
            );
        }

        var user = userRepository.findById(currentUser.getId()).orElseThrow();

        if (!targetProduct.get().getStore().getUser().getId().equals(user.getId())) {
            return new ApiResponse<>(
                    false,
                    403,
                    "U don't have permission to delete this product",
                    null
            );
        }

        var productToDelete = targetProduct.get();

        productRepository.delete(productToDelete);

        return new ApiResponse<>(
                true,
                204,
                "Data Deleted Successfully",
                null
        );
    }

}
