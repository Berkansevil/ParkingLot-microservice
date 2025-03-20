package com.archisacademy.email_service.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailTemplateResponse {

    private String to;

    private String subject;

    private String template;

    private Map<String, Object> model;

}
