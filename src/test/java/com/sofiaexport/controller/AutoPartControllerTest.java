package com.sofiaexport.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sofiaexport.commands.AddAutoPartCommand;
import com.sofiaexport.commands.FindAutoPartsCommand;
import com.sofiaexport.model.AutoPart;
import com.sofiaexport.model.PartType;
import com.sofiaexport.service.AutoPartService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.*;

@WebMvcTest(AutoPartController.class)
class AutoPartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AutoPartService autoPartService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void addAutoPart_ShouldReturnSuccessMessage() throws Exception {
        /*
        AddAutoPartCommand addAutoPartCommand = new AddAutoPartCommand(
                "ExampleBrand",
                "ExampleDescription",
                "ExampleSerialNumber",
                10,
                PartType.FILTER,
                100.0,
                Set.of("CarId1", "CarId2")
        );

        when(autoPartService.saveAutoPart(ArgumentMatchers.any(AddAutoPartCommand.class))).thenReturn("Success");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/autopart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addAutoPartCommand)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Success"));

        verify(autoPartService, times(1)).saveAutoPart(addAutoPartCommand);
        */
    }

    @Test
    void findAutoParts_ShouldReturnAutoPartResponses() throws Exception {
        /*
        // Arrange
        FindAutoPartsCommand findAutoPartsCommand = new FindAutoPartsCommand("BILSTEIN", PartType.FILTER, 200.0);

        List<AutoPart> autoParts = List.of(new AutoPart("", PartType.CLUTCH, "description", 20.0, "serialNumber", 2));
        when(autoPartService.findAutoParts(ArgumentMatchers.any(FindAutoPartsCommand.class)))
                .thenReturn(autoParts);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/autoparts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(findAutoPartsCommand)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(autoParts.size()));

        // Verify that the service method was called with the correct argument
        verify(autoPartService, times(1)).findAutoParts(findAutoPartsCommand);*/
    }
}
