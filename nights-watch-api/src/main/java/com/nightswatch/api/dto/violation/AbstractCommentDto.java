package com.nightswatch.api.dto.violation;

public abstract class AbstractCommentDto {

    protected String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
