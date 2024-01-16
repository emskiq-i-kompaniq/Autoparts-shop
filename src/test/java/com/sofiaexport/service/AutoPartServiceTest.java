package com.sofiaexport.service;

import com.sofiaexport.commands.AddAutoPartCommand;
import com.sofiaexport.commands.FindAutoPartsCommand;
import com.sofiaexport.exception.AutoPartNotFoundException;
import com.sofiaexport.model.AutoPart;
import com.sofiaexport.model.Car;
import com.sofiaexport.model.PartType;
import com.sofiaexport.repository.AutoPartRepository;
import com.sofiaexport.repository.AutoPartRepositoryCustom;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class AutoPartServiceTest {

    @Mock
    private AutoPartRepository autoPartRepository;

    @Mock
    private AutoPartRepositoryCustom autoPartRepositoryCustom;

    @Mock
    private CarService carService;

    @InjectMocks
    private AutoPartService autoPartService;

    @Test
    void saveAutoPart_ShouldReturnAutoPartId() {
        // Arrange
        AddAutoPartCommand addAutoPartCommand = new AddAutoPartCommand(
                "ExampleBrand",
                "ExampleDescription",
                "ExampleSerialNumber",
                10,
                PartType.FILTER,
                100.0,
                Set.of("CarId1", "CarId2")
        );

        when(carService.findAllById(ArgumentMatchers.any())).thenReturn(List.of(new Car()));

        // Create a mock AutoPart instance with a specific ID
        AutoPart mockAutoPart = new AutoPart("ExampleBrand", PartType.FILTER, "ExampleDescription", 100.0, "ExampleSerialNumber", 10);
        mockAutoPart.setId("AutoPartId");

        when(autoPartRepository.save(Mockito.any())).thenReturn(mockAutoPart);

        // Act
        String result = autoPartService.saveAutoPart(addAutoPartCommand);

        // Assert
        assertNotNull(result);
        assertEquals("AutoPartId", result);
        verify(autoPartRepository, times(1)).save(Mockito.any());
    }

    @Test
    void findAutoParts_ShouldReturnAutoPartsList() {
        // Arrange
        FindAutoPartsCommand findAutoPartsCommand = new FindAutoPartsCommand("ExampleBrand", PartType.FILTER, 100.0);
        List<AutoPart> expectedAutoParts = List.of(new AutoPart());

        when(autoPartRepositoryCustom.findAutoParts(ArgumentMatchers.any())).thenReturn(expectedAutoParts);

        // Act
        List<AutoPart> result = autoPartService.findAutoParts(findAutoPartsCommand);

        // Assert
        assertNotNull(result);
        assertEquals(expectedAutoParts, result);
        verify(autoPartRepositoryCustom, times(1)).findAutoParts(ArgumentMatchers.any());
    }

    @Test
    void findAutoPartById_ShouldReturnAutoPart() {
        // Arrange
        String autoPartId = "ExampleAutoPartId";
        AutoPart expectedAutoPart = new AutoPart();

        when(autoPartRepository.findById(autoPartId)).thenReturn(java.util.Optional.of(expectedAutoPart));

        // Act
        AutoPart result = autoPartService.findAutoPartById(autoPartId);

        // Assert
        assertNotNull(result);
        assertEquals(expectedAutoPart, result);
        verify(autoPartRepository, times(1)).findById(autoPartId);
    }

    @Test
    void findAutoPartById_ShouldThrowAutoPartNotFoundException() {
        // Arrange
        String autoPartId = "NonExistingAutoPartId";

        when(autoPartRepository.findById(autoPartId)).thenReturn(java.util.Optional.empty());

        // Act and Assert
        assertThrows(AutoPartNotFoundException.class, () -> autoPartService.findAutoPartById(autoPartId));
        verify(autoPartRepository, times(1)).findById(autoPartId);
    }

}
