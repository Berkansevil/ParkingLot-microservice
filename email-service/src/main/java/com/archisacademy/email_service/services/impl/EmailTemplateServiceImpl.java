package com.archisacademy.email_service.services.impl;

import com.archisacademy.email_service.services.EmailTemplateService;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

@Service
public class EmailTemplateServiceImpl implements EmailTemplateService {

    private final TemplateEngine templateEngine;

    public EmailTemplateServiceImpl(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Override
    public String generateEmailContent(String templateName, Map<String, Object> model) {
        Context context = new Context();
        context.setVariables(model);

        return templateEngine.process(templateName, context);
    }

}
