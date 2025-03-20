package com.archisacademy.email_service.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.getConfiguration().setCollectionsMergeEnabled(true);

        return modelMapper;
    }

}
