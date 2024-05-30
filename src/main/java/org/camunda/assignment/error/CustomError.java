package org.camunda.assignment.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CustomError extends Exception{

    private String code;
    private String description;
    private String responseCode;
}
