package com.sofiaexport.service;

import com.sofiaexport.commands.FindAutoPartsCommand;
import com.sofiaexport.exception.AutoPartNotFoundException;
import com.sofiaexport.model.AutoPart;
import com.sofiaexport.repository.AutoPartRepository;
import com.sofiaexport.repository.AutoPartRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AutoPartService {
    private final AutoPartRepository autoPartRepository;
    private final AutoPartRepositoryCustom autoPartRepositoryCustom;

    public List<AutoPart> findAllAutoParts() {
        return autoPartRepository.findAll();
    }

    public void saveAutoPart(AutoPart autoPart) {
        autoPartRepository.save(autoPart);
    }

    public List<AutoPart> findAutoParts(FindAutoPartsCommand command) {
        return autoPartRepositoryCustom.findAutoParts(command);
    }

    public AutoPart findAutoPartById(String id) {
        return autoPartRepository.findById(id).orElseThrow(() -> new AutoPartNotFoundException(id));
    }

    //TODO has to be transactional
    public void decreaseStock(Set<AutoPart> parts) {
        for (AutoPart part: parts) {
            part.decreaseStockQuantity();
            autoPartRepository.save(part);
        }
    }
}
