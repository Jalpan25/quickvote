package com.example.QuickVote.dto;

import com.example.QuickVote.model.Option;

import java.util.List;

public class QuestionDTO {
    private Long id;
    private String text;
    private List<String> options;

    public QuestionDTO(String text, List<String> options) {
        this.text = text;
        this.options=options;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }
}
