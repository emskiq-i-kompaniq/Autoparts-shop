package com.sofiaexport.controller;

import com.sofiaexport.model.AutoPart;
import com.sofiaexport.service.AutoPartService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
@Tag(name = "AutoPart")
@RequiredArgsConstructor
public class AutoPartController {
    private final AutoPartService autoPartService;

    @GetMapping(path = "/v1/autoparts")
    public List<AutoPart> findAll() {
        return autoPartService.findAllAutoParts();
    }

    @PostMapping(path = "/v1/autopart")
    public void addAutoPart(@RequestBody final AutoPart autoPart) {
        autoPartService.saveAutoPart(autoPart);
    }
}
