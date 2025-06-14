package com.example.QuickVote.repository;

import com.example.QuickVote.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    // Now returning Optional<Admin> to handle the possibility of no result
    Optional<Admin> findByEmail(String email);

    // Find all admins where role is "pending"
    List<Admin> findByRole(String role);

    @Query("SELECT a.institutionName FROM Admin a WHERE a.role = :role")
    List<String> findInstitutionNamesByRole(@Param("role") String role);
//     Delete an admin by email
    void deleteByEmail(String email);
    Optional<Admin> findByEmailAndInstitutionName(String email, String institutionName);

    List<Admin> findByInstitutionName(String institutionName);


}
