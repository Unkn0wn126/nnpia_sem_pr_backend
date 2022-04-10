package cz.upce.fei.sem_pr_backend.dto;

import org.modelmapper.ModelMapper;

public interface DTO {
    default ModelMapper updateModelMapper(ModelMapper modelMapper){
        return modelMapper;
    }
}
