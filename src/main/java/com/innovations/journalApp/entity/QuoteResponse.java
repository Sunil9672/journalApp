package com.innovations.journalApp.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
public class QuoteResponse {
    @JsonProperty("q")
    public String quote;
    @JsonProperty("a")
    public String author;
}


