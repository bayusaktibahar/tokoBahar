package com.sakti.toko.controller;

import com.sakti.toko.common.annotation.AuthCheck;
import com.sakti.toko.common.annotation.CurrentUser;
import com.sakti.toko.model.dto.StoreDTO;
import com.sakti.toko.model.dto.UserDTO;
import com.sakti.toko.model.request.AddStoreRequest;
import com.sakti.toko.model.request.UpdateStoreRequest;
import com.sakti.toko.model.request.ApiResponse;
import com.sakti.toko.service.StoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/store")
@AllArgsConstructor
@Tag(name = "3. Store", description = "Manajemen toko")
public class StoreController {
    private final StoreService storeService;

    @Operation(summary = "Get all stores")
    @GetMapping("/all")
    public ApiResponse<List<StoreDTO>> getAllStore(){
        return storeService.getAllStores();
    }

    @Operation(summary = "Get store by ID")
    @GetMapping("/{id}")
    public ApiResponse<StoreDTO> getStoreById(@PathVariable long id){
        return storeService.getStoreById(id);
    }

    @Operation(summary = "Get store by name")
    @GetMapping("/name")
    public ApiResponse<StoreDTO> getStoreByName(@RequestParam(name = "name") String name){
        return storeService.getStoreByName(name);
    }

    @Operation(summary = "Add store", description = "Membuka toko baru. Butuh login.")
    @PostMapping("/add")
    @AuthCheck
    @SecurityRequirement(name = "bearerAuth")
    public ApiResponse<StoreDTO> addStore(@RequestBody AddStoreRequest addStoreRequest, @Parameter(hidden = true) @CurrentUser UserDTO currentUser){
        return storeService.addStore(addStoreRequest, currentUser);
    }

    @Operation(summary = "Update store", description = "Mengubah nama toko.")
    @PostMapping("/update/{id}")
    @AuthCheck
    @SecurityRequirement(name = "bearerAuth")
    public ApiResponse<StoreDTO> updateStore(@PathVariable long id, @RequestBody UpdateStoreRequest updateStoreRequest, @Parameter(hidden = true) @CurrentUser UserDTO currentUser){
        return storeService.updateStore(id, updateStoreRequest, currentUser);
    }

    @Operation(summary = "Delete store", description = "Menghapus toko milik sendiri.")
    @DeleteMapping("/{id}")
    @AuthCheck
    @SecurityRequirement(name = "bearerAuth")
    public ApiResponse<StoreDTO> deleteStore(@PathVariable long id, @Parameter(hidden = true) @CurrentUser UserDTO currentUser){
        return storeService.deleteStore(id, currentUser);
    }

    @Operation(summary = "Suspend store", description = "Menangguhkan toko. Biasanya aksi admin.")
    @PatchMapping("/{id}/suspend")
    @AuthCheck
    @SecurityRequirement(name = "bearerAuth")
    public ApiResponse<StoreDTO> suspendStore(@PathVariable long id, @Parameter(hidden = true) @CurrentUser UserDTO currentUser) {
        return storeService.suspendStore(id, currentUser);
    }
}