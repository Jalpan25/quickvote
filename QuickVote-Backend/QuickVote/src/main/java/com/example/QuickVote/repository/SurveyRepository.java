package com.example.QuickVote.repository;

import com.example.QuickVote.model.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SurveyRepository extends JpaRepository<Survey, Long> {

    @Query("SELECT s FROM Survey s JOIN FETCH s.questions q JOIN FETCH q.options WHERE s.id = :surveyId")
    Survey findSurveyWithQuestionsAndOptions(@Param("surveyId") Long surveyId);

    @Query("SELECT s FROM Survey s WHERE s.adminEmail = :adminEmail")
    List<Survey> findSurveysByAdminEmail(@Param("adminEmail") String adminEmail);
    void deleteById(Long id);
    // Fetch surveys where email matches the emailRestriction pattern (with * replaced by _)
    @Query("SELECT s FROM Survey s WHERE s.emailRestriction IS NOT NULL AND :email LIKE REPLACE(s.emailRestriction, '*', '_')")
    List<Survey> findSurveysByEmailRestriction(@Param("email") String email);

    List<Survey> findByAdminEmail(String email);

}
