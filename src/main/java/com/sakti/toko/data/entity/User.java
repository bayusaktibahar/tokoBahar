package com.sakti.toko.data.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "users")
@Data                   // Otomatis membuat getter, setter, toString, equals, dan hashCode
@NoArgsConstructor      // Membuat constructor kosong (wajib untuk JPA)
@AllArgsConstructor     // Membuat constructor dengan semua parameter
@Builder                // Membantu pembuatan objek dengan design pattern Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // Menggunakan UUID otomatis
    private UUID id;

    @Column(unique = true, nullable = false, length = 100)
    private String email;

    @Column(nullable = false)
    private String password; // Ingat: ini harus disimpan dalam bentuk Bcrypt/hashed nantinya

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @CreationTimestamp // Otomatis mengisi waktu saat data pertama kali dibuat
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}