package com.sofiaexport.service;

import com.sofiaexport.model.AutoPart;
import com.sofiaexport.repository.AutoPartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AutoPartService {
    private final AutoPartRepository autoPartRepository;

    public List<AutoPart> findAllAutoParts() {
        return autoPartRepository.findAll();
    }

    public void saveAutoPart(AutoPart autoPart) {
        autoPartRepository.save(autoPart);
    }

}
