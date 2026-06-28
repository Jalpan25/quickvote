package com.example.QuickVote.service;

import com.example.QuickVote.dto.*;
import com.example.QuickVote.model.Admin;
import com.example.QuickVote.model.Survey;
import com.example.QuickVote.repository.AdminRepository;
import com.example.QuickVote.repository.OptionRepository;
import com.example.QuickVote.repository.ResponseRepository;
import com.example.QuickVote.repository.SurveyRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.QuickVote.model.Option;
import org.springframework.transaction.annotation.Transactional;


import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
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

    @Autowired
    private AdminRepository adminRepository;

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

    public List<FilterDTO> filterSurveysByEmailAndInstitution(String email, String institutionName) {
        System.out.println("Filtering surveys for email: " + email + " and institution: " + institutionName);

        // Step 1: Find all admins for the institution
        List<Admin> admins = adminRepository.findByInstitutionName(institutionName);

        if (admins.isEmpty()) {
            //System.out.println("No admins found for institution: " + institutionName);
            return Collections.emptyList();
        }

        System.out.println("Found " + admins.size() + " admin(s) for institution: " + institutionName);

        // Step 2: Collect surveys created by all those admins
        List<Survey> allSurveys = new ArrayList<>();
        for (Admin admin : admins) {
            List<Survey> surveys = surveyRepository.findByAdminEmail(admin.getEmail());
            //System.out.println("Admin: " + admin.getEmail() + " | Surveys found: " + surveys.size());
            allSurveys.addAll(surveys);
        }

        // Step 3: Filter by email domain restriction
        List<Survey> eligibleSurveys = allSurveys.stream()
                .filter(survey -> {
                    boolean eligible = isEmailEligible(email, survey.getEmailRestriction());
//                    System.out.println("Survey ID " + survey.getId() + " | Eligible: " + eligible +
//                            " | Restriction: " + survey.getEmailRestriction());
                    return eligible;
                })
                .collect(Collectors.toList());

        // Step 4: Build DTOs
        List<FilterDTO> result = eligibleSurveys.stream().map(survey -> {
            FilterDTO dto = new FilterDTO();
            dto.setId(survey.getId());
            dto.setTitle(survey.getTitle());
            dto.setEndTime(survey.getEndTime().toString());
            dto.setResult_show(survey.isResultShow());

            boolean isAttempted = responseRepository.existsBySurveyAndEmail(survey, email);
            dto.setAttempted(isAttempted);

           // System.out.println("Built DTO for survey: " + dto.getTitle() + " | Attempted: " + isAttempted);
            return dto;
        }).collect(Collectors.toList());

       // System.out.println("Returning " + result.size() + " eligible surveys");
        return result;
    }



    private boolean isEmailEligible(String email, String emailRestriction) {
        if (emailRestriction == null || emailRestriction.isEmpty()) {
            return true;
        }

        if (emailRestriction.startsWith("all@")) {
            String restrictionDomain = emailRestriction.substring(emailRestriction.indexOf('@') + 1);
            String emailDomain = email.substring(email.indexOf('@') + 1);
            //System.out.println("Checking domain match: " + restrictionDomain + " vs " + emailDomain);
            return restrictionDomain.equalsIgnoreCase(emailDomain);
        }

        String regex = emailRestriction.replace("*", ".");
        boolean matches = Pattern.matches(regex, email);
        //System.out.println("Checking regex match: " + regex + " vs " + email + " = " + matches);
        return matches;
    }

}
