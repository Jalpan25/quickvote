package com.example.QuickVote.dto;

public class OptionDTO {
    private Long id;
    private String optionText;

    // Constructor
    public OptionDTO(Long id, String optionText) {
        this.id = id;
        this.optionText = optionText;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOptionText() {
        return optionText;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }
}
