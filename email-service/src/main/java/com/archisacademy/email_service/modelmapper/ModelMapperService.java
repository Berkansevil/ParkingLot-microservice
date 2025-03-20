package com.archisacademy.email_service.modelmapper;

import org.modelmapper.ModelMapper;

public interface ModelMapperService {

    ModelMapper request();

    ModelMapper response();

}