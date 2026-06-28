//package com.example.QuickVote.dto;
//
//public class QuestionDTORenamed {
//}
package com.example.QuickVote.dto;

import com.example.QuickVote.model.Option;

import java.util.List;

public class QuestionDTORenamed {
    private Long id;
    private String text;
    private List<Option> options;

    // Getters and Setters
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

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }
}

