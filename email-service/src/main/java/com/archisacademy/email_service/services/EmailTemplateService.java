package com.archisacademy.email_service.services;

import java.util.Map;

public interface EmailTemplateService {

    String generateEmailContent(String templateName, Map<String, Object> model);

}
