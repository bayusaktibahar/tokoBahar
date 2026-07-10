package com.sakti.toko.model.dto;

import com.sakti.toko.data.entity.Role;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private UUID id;
    private String sessionId;
    private String email;
    private String password;
    private Role role;
    private LocalDateTime createdAt;
    private Boolean isSuspended;
}
