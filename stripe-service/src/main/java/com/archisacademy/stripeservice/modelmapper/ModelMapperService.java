package com.archisacademy.stripeservice.modelmapper;

import org.modelmapper.ModelMapper;

public interface ModelMapperService {

    ModelMapper request();

    ModelMapper response();

}