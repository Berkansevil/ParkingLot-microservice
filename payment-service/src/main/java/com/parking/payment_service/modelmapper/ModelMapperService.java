package com.parking.payment_service.modelmapper;

import org.modelmapper.ModelMapper;

public interface ModelMapperService {

    ModelMapper request();

    ModelMapper response();

}