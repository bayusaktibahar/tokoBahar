package com.sakti.toko.service.details;




import com.sakti.toko.data.entity.Store;
import com.sakti.toko.data.repository.UserRepository;
import com.sakti.toko.model.dto.StoreDTO;
import com.sakti.toko.model.dto.UserDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StoreDetailService {

    private final UserRepository userRepository;

    public StoreDTO getStoreDetails(Store store) {

                UserDTO userDTO = userRepository.findById(store.getUser().getId())
                        .map(user -> UserDTO.builder()
                                .id(user.getId())
                                .email(user.getEmail())
                                .role(user.getRole())
                                .createdAt(user.getCreatedAt())
                                .build())
                        .orElse(null);

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

