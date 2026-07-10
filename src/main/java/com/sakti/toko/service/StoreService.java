package com.sakti.toko.service;

import com.sakti.toko.data.entity.Role;
import com.sakti.toko.data.entity.Store;
import com.sakti.toko.data.repository.StoreRepository;
import com.sakti.toko.data.repository.UserRepository;
import com.sakti.toko.model.dto.StoreDTO;
import com.sakti.toko.model.dto.UserDTO;
import com.sakti.toko.model.request.AddStoreRequest;
import com.sakti.toko.model.request.UpdateStoreRequest;
import com.sakti.toko.model.request.ApiResponse;
import com.sakti.toko.service.details.StoreDetailService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class StoreService {
    private StoreRepository storeRepository;
    private StoreDetailService storeDetailService;
    private UserRepository userRepository;

    public ApiResponse<List<StoreDTO>> getAllStores(){
        List<Store> stores = storeRepository.findAll();

        var result = storeDetailService.getListStoreDetails(stores);

        return new ApiResponse<>(
                true,
                200,
                "Data Found",
                result
        );
    }

    public ApiResponse<StoreDTO> getStoreById(long storeId) {
        var store = storeRepository.findById(storeId).orElseThrow();

        var result = storeDetailService.getStoreDetails(store);

        return new ApiResponse<>(
                true,
                201,
                "Data Found",
                result
        );
    }

    public ApiResponse<StoreDTO> getStoreByName(String storeName) {
        var store = storeRepository.findByStoreName(storeName).orElseThrow();

        var result = storeDetailService.getStoreDetails(store);

        return new ApiResponse<>(
                true,
                200,
                "Data Found",
                result
        );
    }

    @Transactional
    public ApiResponse<StoreDTO> addStore(AddStoreRequest addStoreRequest, UserDTO currentUser) {
        var targetUser = userRepository.findById(currentUser.getId());

        if (targetUser.isEmpty()) {
            return new ApiResponse<>(
                    false,
                    404,
                    "User not found",
                    null
            );
        }

        var userStore = targetUser.get();

        var existingStore = storeRepository.findByUser(userStore);

        if(existingStore.isPresent()) {
            return new ApiResponse<>(
                    false,
                    402,
                    "You are already have a store",
                    null
            );
        }
        var store = new Store();
        store.setUser(userStore);
        store.setStoreName(addStoreRequest.getStoreName());

        storeRepository.save(store);
        var result = storeDetailService.getStoreDetails(store);

        return new ApiResponse<>(
                true,
                200,
                "Store Created Succesfully",
                result
        );
    }

    @Transactional
    public ApiResponse<StoreDTO> updateStore(long storeId, UpdateStoreRequest request) {
        var targetStore = storeRepository.findById(storeId);

        if (targetStore.isEmpty()) {
            return new ApiResponse<>(
                    false,
                    404,
                    "Store not found",
                    null
            );
        }

        var userToUpdate = targetStore.get();

        if (request.getStoreName() != null) {
            userToUpdate.setStoreName(request.getStoreName());
        }

        storeRepository.save(userToUpdate);

        var updatedStore = storeRepository.findById(storeId).orElseThrow();
        var result = storeDetailService.getStoreDetails(updatedStore);

        return new ApiResponse<>(
                true,
                200,
                "Store Updated Succesfully",
                result
        );
    }

    @Transactional
    public ApiResponse<StoreDTO> deleteStore(long storeId, UserDTO currentUser) {
        var targetStore = storeRepository.findById(storeId);

        if (targetStore.isEmpty()) {
            return new ApiResponse<>(
                    false,
                    404,
                    "Store not found",
                    null
            );
        }

        var user = userRepository.findById(currentUser.getId()).orElseThrow();

        if (targetStore.get().getUser() != user){
            return new ApiResponse<>(
                    false,
                    401,
                    "U don't have permission to delete this store",
                    null
            );
        }

        var storeToDelete = targetStore.get();

        storeRepository.delete(storeToDelete);

        return new ApiResponse<>(
                true,
                200,
                "Store Deleted Succesfully",
                null
        );
    }

    @Transactional
    public ApiResponse<StoreDTO> suspendStore(long storeId, UserDTO currentUser) {

        if (!currentUser.getRole().equals(Role.ADMIN)) {
            return new ApiResponse<>(
                    false,
                    403,
                    "You are not allowed to perform this action",
                    null
            );
        }

        var targetStore = storeRepository.findById(storeId);

        if (targetStore.isEmpty()) {
            return new ApiResponse<>(
                    false,
                    404,
                    "Store not found",
                    null
            );
        }

        var storeSuspended = targetStore.get();

        storeSuspended.setIsSuspended(!storeSuspended.getIsSuspended());

        storeRepository.save(storeSuspended);

        var result = storeDetailService.getStoreDetails(storeSuspended);

        return new ApiResponse<>(
                true,
                200,
                storeSuspended.getIsSuspended() ? "Store Suspended" : "Store Unsuspended",
                result
        );
    }
}



