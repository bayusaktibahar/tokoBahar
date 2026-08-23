//package com.sakti.toko.config;
//
//import com.sakti.toko.common.extension.AESOperation;
//import com.sakti.toko.data.entity.Role;
//import com.sakti.toko.data.entity.User;
//import com.sakti.toko.data.repository.UserRepository;
//import lombok.AllArgsConstructor;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDateTime;
//
//@Component
//@AllArgsConstructor
//public class DataInitializer implements CommandLineRunner {
//
//    private final UserRepository userRepository;
//
//    @Override
//    public void run(String... args) throws Exception {
//        String adminEmail = "admin@toko.com";
//
//        // Cek apakah email admin sudah terdaftar di database
//        if (userRepository.findByEmailIgnoreCase(adminEmail).isEmpty()) {
//            User admin = User.builder()
//                    .email(adminEmail)
//                    .password(AESOperation.encode("Admin@123")) // Password otomatis di-encrypt
//                    .role(Role.ADMIN) // Set langsung sebagai ADMIN
//                    .createdAt(LocalDateTime.now())
//                    .isSuspended(false)
//                    .build();
//
//            userRepository.save(admin);
//            System.out.println("=== AKUN ADMIN OTOMATIS BERHASIL DIBUAT ===");
//            System.out.println("Email: admin@toko.com");
//            System.out.println("Password: Admin@123");
//            System.out.println("===========================================");
//        }
//    }
//}
