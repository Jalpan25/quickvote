package com.example.QuickVote.service;

import com.example.QuickVote.dto.*;
import com.example.QuickVote.model.Survey;
import com.example.QuickVote.repository.ResponseRepository;
import com.example.QuickVote.repository.SurveyRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.QuickVote.model.Option;
import org.springframework.transaction.annotation.Transactional;


import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class    SurveyService {

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private SurveyMapper surveyMapper;
    @Autowired
    private ResponseRepository responseRepository;

    public Survey saveSurvey(Survey survey) {
        return surveyRepository.save(survey);
    }

    public SurveyDTO getSurveyById(Long id) {
        Survey survey = surveyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Survey not found with ID: " + id));

        return convertToDto(survey);
    }

    private SurveyDTO convertToDto(Survey survey) {
        SurveyDTO dto = new SurveyDTO();
        dto.setAdminEmail(survey.getAdminEmail());
        dto.setEmailRestriction(survey.getEmailRestriction());
        dto.setEndTime(survey.getEndTime());
        dto.setTitle(survey.getTitle());
        dto.setResultShow(survey.isResultShow());  // For boolean
        dto.setParticipationNo(survey.getParticipationNo());  // For int


        List<QuestionDTO> questionDtos = survey.getQuestions().stream()
                .map(question -> new QuestionDTO(
                question.getText(),
                question.getOptions().stream().map(Option::getOptionText).collect(Collectors.toList())
        ))
                .collect(Collectors.toList());

        dto.setQuestions(questionDtos);
        return dto;
    }
    public Survey updateSurvey(Long id, SurveyDTO surveyDTO) {
        Survey existingSurvey = surveyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Survey not found with ID: " + id));

        // Use SurveyMapper to update existing survey fields
        Survey updatedSurvey = surveyMapper.mapToSurveyEntity(surveyDTO);
        updatedSurvey.setId(existingSurvey.getId()); // Preserve the original ID

        // Explicitly set adminEmail from surveyDTO
        updatedSurvey.setAdminEmail(surveyDTO.getAdminEmail() != null ? surveyDTO.getAdminEmail() : existingSurvey.getAdminEmail());

        return surveyRepository.save(updatedSurvey);
    }


    @Transactional
    public void deleteSurvey(Long id) {
        if (!surveyRepository.existsById(id)) {
            throw new RuntimeException("Survey not found!");
        }

        // Delete responses linked to this survey first
        responseRepository.deleteBySurveyId(id);

        // Now delete the survey
        surveyRepository.deleteById(id);
    }


    public AdminSurveysResponseDTO getSurveysByAdminEmail(String adminEmail) {
        List<Survey> surveys = surveyRepository.findSurveysByAdminEmail(adminEmail);

        List<CreatedSurveyDTO> createdSurveys = surveys.stream()
                .map(survey -> new CreatedSurveyDTO(
                        survey.getTitle(),
                        survey.getId().toString(),
                        survey.getEndTime().toLocalDate().toString()
                ))
                .collect(Collectors.toList());

        AdminDTO admin = new AdminDTO(adminEmail);

        return new AdminSurveysResponseDTO(admin, createdSurveys);
    }

    public List<FilterDTO> filterSurveysByEmail(String email) {
        List<Survey> surveys = surveyRepository.findAll();

        // Filter surveys by email restriction
        List<Survey> eligibleSurveys = surveys.stream()
                .filter(survey -> isEmailEligible(email, survey.getEmailRestriction()))
                .collect(Collectors.toList());

        // Create a list of DTOs with the "attempted" status
        return eligibleSurveys.stream().map(survey -> {
            FilterDTO dto = new FilterDTO();
            dto.setId(survey.getId());
            dto.setTitle(survey.getTitle());
            dto.setEndTime(survey.getEndTime().toString());
            dto.setResult_show(survey.isResultShow());

            // Check if a response exists for this survey and email
            boolean isAttempted = responseRepository.existsBySurveyAndEmail(survey, email);
            dto.setAttempted(isAttempted);

            return dto;
        }).collect(Collectors.toList());
    }

    private boolean isEmailEligible(String email, String emailRestriction) {
        if (emailRestriction == null || emailRestriction.isEmpty()) {
            return true; // No restriction means the email is eligible
        }

        // Special case: if restriction starts with "all@", match domain
        if (emailRestriction.startsWith("all@")) {
            String restrictionDomain = emailRestriction.substring(emailRestriction.indexOf('@') + 1);
            String emailDomain = email.substring(email.indexOf('@') + 1);
            return restrictionDomain.equalsIgnoreCase(emailDomain);
        }

        // Convert the email restriction to a regex pattern
        String regex = emailRestriction.replace("*", ".");

        // Check if the email matches the restriction pattern
        return Pattern.matches(regex, email);
    }
}

