package com.sofiaexport.service;

import com.sofiaexport.commands.AddAutoPartCommand;
import com.sofiaexport.commands.FindAutoPartsCommand;
import com.sofiaexport.exception.AutoPartNotFoundException;
import com.sofiaexport.model.AutoPart;
import com.sofiaexport.model.Car;
import com.sofiaexport.repository.AutoPartRepository;
import com.sofiaexport.repository.AutoPartRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AutoPartService {
    private final AutoPartRepository autoPartRepository;
    private final AutoPartRepositoryCustom autoPartRepositoryCustom;
    private final CarService carService;

    public String saveAutoPart(AddAutoPartCommand addAutoPartCommand) {
        AutoPart autoPartToAdd = addAutoPartCommand.toAutoPart();
        List<Car> compatibleCars = carService.findAllById(addAutoPartCommand.getCompatibleCarsIds());
        autoPartToAdd.setCompatibleCars(new HashSet<>(compatibleCars));

        return autoPartRepository.save(autoPartToAdd).getId();
    }

    public List<AutoPart> findAutoParts(FindAutoPartsCommand command) {
        return autoPartRepositoryCustom.findAutoParts(command);
    }

    public AutoPart findAutoPartById(String id) {
        return autoPartRepository.findById(id).orElseThrow(() -> new AutoPartNotFoundException(id));
    }

    @Transactional
    public void decreaseStock(Set<AutoPart> parts) {
        parts.parallelStream().forEach(AutoPart::decreaseStockQuantity);
        autoPartRepository.saveAll(parts);
    }
}
