package com.example.QuickVote.controller;

import com.example.QuickVote.dto.*;
import com.example.QuickVote.model.Survey;
import com.example.QuickVote.repository.SurveyRepository;
import com.example.QuickVote.service.ResponseService;
import com.example.QuickVote.service.SurveyFilterService;
import com.example.QuickVote.service.SurveyMapper;
import com.example.QuickVote.service.SurveyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/surveys")
public class SurveyController {

    @Autowired
    private SurveyService surveyService;

    @Autowired
    private SurveyMapper surveyMapper;

    @Autowired
    private SurveyFilterService surveyFilterService;

    @Autowired
    private ResponseService responseService;

    private final SurveyRepository surveyRepository;

    public SurveyController(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }

    /**
     * Update an existing survey.
     *
     * @param id          Survey ID from path.
     * @param surveyDTO   DTO containing updated survey details.
     * @return Updated Survey entity.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Survey> updateSurvey(@PathVariable Long id, @RequestBody SurveyDTO surveyDTO) {
        Survey updatedSurvey = surveyService.updateSurvey(id, surveyDTO);
        return ResponseEntity.ok(updatedSurvey);
    }


    /**
     * Create a new survey.
     *
     * @param surveyDTO DTO containing survey details.
     * @return The created Survey entity.
     */
    @PostMapping
    public Survey createSurvey(@RequestBody SurveyDTO surveyDTO) {
        if (surveyDTO.getAdminEmail() == null || surveyDTO.getAdminEmail().isEmpty()) {
            throw new IllegalArgumentException("Admin email is required.");
        }

        Survey survey = surveyMapper.mapToSurveyEntity(surveyDTO);
        survey.setAdminEmail(surveyDTO.getAdminEmail());
        return surveyService.saveSurvey(survey);
    }

    /**
     * Get surveys based on email restrictions.
     *
     * @param requestBody A map containing the user's email.
     * @return A list of surveys the user is allowed to access.
     */
    @PostMapping("/filter-by-email")
    public List<FilterDTO> filterSurveysByEmail(@RequestBody EmailRequest emailRequest) {
        return surveyService.filterSurveysByEmail(emailRequest.getEmail());
    }

    /**
     * Save survey responses.
     *
     * @param surveyId   The ID of the survey.
     * @param responses  The list of responses to be saved.
     * @return ResponseEntity indicating success or failure.
     */
    @PostMapping("/{surveyId}/responses")
    public ResponseEntity<String> saveSurveyResponses(
            @PathVariable Long surveyId,
            @RequestBody Map<String, Object> requestBody) { // Expecting email + responses

        try {
            // Extract email from request body
            String email = (String) requestBody.get("email");

            // Extract responses (ensure safe casting)
            List<Map<String, Object>> responses = (List<Map<String, Object>>) requestBody.get("responses");

            // Save responses with email
            responseService.saveResponses(surveyId, email, responses);

            return ResponseEntity.ok("Responses saved successfully for email: " + email);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to save responses: " + e.getMessage());
        }
    }

    /**
     * Fetch surveys created by a specific admin email.
     *
     * @param requestBody JSON containing "adminEmail"
     * @return Admin surveys data
     */
    @PostMapping("/fetch-by-admin")
    public ResponseEntity<AdminSurveysResponseDTO> getSurveysByAdmin(@RequestBody Map<String, String> requestBody) {
        String adminEmail = requestBody.get("adminEmail");

        if (adminEmail == null || adminEmail.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        AdminSurveysResponseDTO response = surveyService.getSurveysByAdminEmail(adminEmail);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSurvey(@PathVariable Long id) {
        try {
            surveyService.deleteSurvey(id);
            return ResponseEntity.ok("Survey and all responses deleted successfully!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Survey not found!");
        }
    }
    /**
     * Fetch a specific survey by ID.
     *
     * @param id The survey ID.
     * @return Survey details.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SurveyDTO> getSurveyById(@PathVariable Long id) {
        SurveyDTO survey = surveyService.getSurveyById(id);
        return ResponseEntity.ok(survey);
    }
}
