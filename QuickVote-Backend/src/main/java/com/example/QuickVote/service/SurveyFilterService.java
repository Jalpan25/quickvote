package com.example.QuickVote.service;

import com.example.QuickVote.dto.SurveyResponseDTO;
import com.example.QuickVote.model.Survey;
import com.example.QuickVote.repository.ResponseRepository;
import com.example.QuickVote.repository.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SurveyFilterService {

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private ResponseRepository responseRepository; // ✅ Added repository

    /**
     * Fetch surveys based on email restriction.
     *
     * @param email The user's email.
     * @return A list of SurveyResponseDTO with attempted flag.
     */
    public List<SurveyResponseDTO> getSurveysForEmail(String email) {
        List<Survey> surveys = surveyRepository.findAll();

        return surveys.stream()
                .map(survey -> new SurveyResponseDTO(
                        survey.getId().toString(),
                        survey.getTitle(),
                        survey.getEndTime(),
                        responseRepository.existsBySurveyAndEmail(survey, email) // ✅ Check if attempted
                ))
                .collect(Collectors.toList());
    }
}
