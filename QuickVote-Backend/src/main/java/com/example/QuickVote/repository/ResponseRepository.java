package com.example.QuickVote.repository;

import com.example.QuickVote.model.Response;

import com.example.QuickVote.model.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ResponseRepository extends JpaRepository<Response, Long> {
    boolean existsBySurveyAndEmail(Survey survey, String email);
//    boolean existsBySurveyAndEmail(Survey survey, String email);

    void deleteByEmail(String email);

    // ✅ New method to fetch responses by survey ID
    List<Response> findBySurveyId(Long surveyId);

    List<Response> findBySurveyIdAndEmail(Long surveyId, String email);
    @Modifying
    @Transactional
    @Query("DELETE FROM Response r WHERE r.survey.id = :surveyId")
    void deleteBySurveyId(@Param("surveyId") Long surveyId);
}
