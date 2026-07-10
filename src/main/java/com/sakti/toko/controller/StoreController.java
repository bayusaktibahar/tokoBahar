package com.sakti.toko.controller;

import com.sakti.toko.common.annotation.AuthCheck;
import com.sakti.toko.common.annotation.CurrentUser;
import com.sakti.toko.model.dto.StoreDTO;
import com.sakti.toko.model.dto.UserDTO;
import com.sakti.toko.model.request.AddStoreRequest;
import com.sakti.toko.model.request.UpdateStoreRequest;
import com.sakti.toko.model.request.ApiResponse;
import com.sakti.toko.service.StoreService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/store")
@AllArgsConstructor
public class StoreController {
    private final StoreService storeService;

    @GetMapping("/all")
    public ApiResponse<List<StoreDTO>> getAllStore(){
        return storeService.getAllStores();
    }

    @GetMapping("/{id}")
    public ApiResponse<StoreDTO> getStoreById(@PathVariable long id){
        return storeService.getStoreById(id);
    }

    @GetMapping("/name")
    public ApiResponse<StoreDTO> getStoreByName(String name){
        return storeService.getStoreByName(name);
    }

    @PostMapping("/add")
    @AuthCheck
    public ApiResponse<StoreDTO> addStore(@RequestBody AddStoreRequest addStoreRequest, @CurrentUser UserDTO currentUser){
        return storeService.addStore(addStoreRequest, currentUser);
    }

    @PostMapping("/update/{id}")
    public ApiResponse<StoreDTO> updateStore(@PathVariable long id, @RequestBody UpdateStoreRequest updateStoreRequest){
        return storeService.updateStore(id, updateStoreRequest);
    }

    @DeleteMapping("/{id}")
    @AuthCheck
    public ApiResponse<StoreDTO> deleteStore(@PathVariable long id, @CurrentUser UserDTO currentUser ){
        return storeService.deleteStore(id,  currentUser);
    }

    @PatchMapping("/{id}/suspend")
    @AuthCheck
    public ApiResponse<StoreDTO> suspendStore(@PathVariable long id, @CurrentUser UserDTO currentUser) {
        return storeService.suspendStore(id, currentUser);
    }
}
