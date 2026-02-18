package com.fixitnow.backend.controller;

import com.fixitnow.backend.entity.ProviderProfile;
import com.fixitnow.backend.entity.User;
import com.fixitnow.backend.repository.ProviderProfileRepository;
import com.fixitnow.backend.repository.UserRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private ProviderProfileRepository providerProfileRepository;

    @Autowired
    private UserRepository userRepository;


        @PutMapping("/approve/{userId}")
        public ResponseEntity<?> approveProvider(@PathVariable Long userId) {

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            ProviderProfile profile = providerProfileRepository.findByUser(user)
                    .orElseThrow(() -> new RuntimeException("Provider profile not found"));

            profile.setApprovalStatus("APPROVED");
            providerProfileRepository.save(profile);

            return ResponseEntity.ok("Provider approved successfully");
        }



        @PutMapping("/reject/{userId}")
        public ResponseEntity<?> rejectProvider(@PathVariable Long userId) {

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            ProviderProfile profile = providerProfileRepository.findByUser(user)
                    .orElseThrow(() -> new RuntimeException("Provider profile not found"));

            profile.setApprovalStatus("REJECTED");
            providerProfileRepository.save(profile);

            return ResponseEntity.ok("Provider rejected successfully");
        }
        // @GetMapping("/pending-providers")
        // public ResponseEntity<?> getPendingProviders() {

        //     List<ProviderProfile> pendingProviders =
        //             providerProfileRepository.findByApprovalStatus("PENDING");

        //     return ResponseEntity.ok(pendingProviders);
        
        @GetMapping("/pending-providers")
        public List<ProviderProfile> getAllProviders() {
        return providerProfileRepository.findAll();
        }

        @GetMapping("/providers")
        public List<ProviderProfile> getProviders(
        @RequestParam(required = false) String status) {

         if (status != null) {
        return providerProfileRepository.findByApprovalStatus(status.toUpperCase());
        }

        return providerProfileRepository.findAll();
        }


}
