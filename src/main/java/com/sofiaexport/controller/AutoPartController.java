package com.sofiaexport.controller;

import com.sofiaexport.commands.FindAutoPartsCommand;
import com.sofiaexport.model.AutoPart;
import com.sofiaexport.response.AutoPartResponse;
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

    @PostMapping(path = "/v1/autopart")
    public String addAutoPart(@RequestBody final AutoPart autoPart) {
        return autoPartService.saveAutoPart(autoPart);
    }

    @GetMapping(path = "/v1/autoparts")
    public List<AutoPartResponse> findAutoParts(final FindAutoPartsCommand command) {
        return autoPartService.findAutoParts(command)
                .stream()
                .map(AutoPartResponse::from)
                .toList();
    }
}
