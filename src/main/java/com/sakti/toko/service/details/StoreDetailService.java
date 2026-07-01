package com.sakti.toko.service.details;

import com.sakti.toko.data.entity.Store;
import com.sakti.toko.model.dto.StoreDTO;
import com.sakti.toko.model.dto.UserDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StoreDetailService {

    private final UserDetailService userDetailService;

    public StoreDTO getStoreDetails(Store store) {

        // Langsung pakai objek user yang sudah ada dari relasi
        UserDTO userDTO = userDetailService.getUserDetailWithoutSession(store.getUser());

        return StoreDTO.builder()
                .id(store.getId())
                .userDTO(userDTO)
                .storeName(store.getStoreName())
                .build();
    }

    public List<StoreDTO> getListStoreDetails(List<Store> stores) {
        return stores.stream()
                .map(this::getStoreDetails)
                .collect(Collectors.toList());
    }
}

