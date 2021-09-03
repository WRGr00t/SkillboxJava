package com.example.blogapp.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter @Setter
@AllArgsConstructor
public class ValidationErrorResponse {

    private final List<Violation> errors;

}
