package com.recreo.services.impl;

import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.stream.Collectors;

public class BaseServices {
    protected ModelMapper modelMapper;

    public <D, T> D convertToDto(T entity, Class<D> outClass) {
        return modelMapper.map(entity, outClass);
    }

    public <D, T> List<D> convertToDto(List<T> entityList, Class<D> outClass) {
        return entityList.stream()
                .map(x -> convertToDto(x, outClass))
                .collect(Collectors.toList());
    }
}
