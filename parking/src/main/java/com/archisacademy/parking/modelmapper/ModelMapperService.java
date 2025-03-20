package com.archisacademy.parking.modelmapper;

import org.modelmapper.ModelMapper;

public interface ModelMapperService {

    ModelMapper request();

    ModelMapper response();

}