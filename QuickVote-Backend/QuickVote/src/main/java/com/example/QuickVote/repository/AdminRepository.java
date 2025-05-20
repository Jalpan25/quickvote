package com.example.QuickVote.repository;

import com.example.QuickVote.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    // Now returning Optional<Admin> to handle the possibility of no result
    Optional<Admin> findByEmail(String email);

    // Find all admins where role is "pending"
    List<Admin> findByRole(String role);

    // Delete an admin by email
    void deleteByEmail(String email);
}
