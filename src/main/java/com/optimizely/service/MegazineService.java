package com.optimizely.service;

import com.optimizely.constants.ExceptionConstants;
import com.optimizely.helper.exception.ResourceNotFoundException;
import com.optimizely.model.Megazine;
import com.optimizely.repository.MegazineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MegazineService {

    @Autowired
    MegazineRepository megazineRepository;

    public List<Megazine> findAllMegazines() throws Exception {
        List<Megazine> megazine = megazineRepository.findAll();
        if (megazine.isEmpty()) {
            throw new ResourceNotFoundException(ExceptionConstants.MEGAZINE_NOT_FOUND);
        }
        return megazine;
    }

    public Megazine findByIsbn(String isbn) throws Exception {
        Megazine megazines = megazineRepository.findByIsbn(isbn);
        if (megazines == null) {
            throw new ResourceNotFoundException(ExceptionConstants.MEGAZINE_NOT_FOUND_FOR_ISBN);
        }
        return megazines;
    }

    public List<Megazine> findAllSortedBy(String property) throws Exception
    {
        return megazineRepository.findAll(Sort.by(Sort.Direction.ASC,property));
    }
}
