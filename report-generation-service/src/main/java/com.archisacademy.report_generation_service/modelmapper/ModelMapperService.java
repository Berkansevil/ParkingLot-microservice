package com.archisacademy.report_generation_service.modelmapper;

import org.modelmapper.ModelMapper;

public interface ModelMapperService {

    ModelMapper request();

    ModelMapper response();

}