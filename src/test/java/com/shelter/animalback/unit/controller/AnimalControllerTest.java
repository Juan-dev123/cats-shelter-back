package com.shelter.animalback.unit.controller;

import com.shelter.animalback.controller.AnimalController;
import com.shelter.animalback.controller.dto.AnimalDto;
import com.shelter.animalback.domain.Animal;
import com.shelter.animalback.service.interfaces.AnimalService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ExtendWith(MockitoExtension.class)
public class AnimalControllerTest {
    @Mock
    AnimalService animalService;

    @InjectMocks
    AnimalController animalController;

    @Test
    public void testListAnimals() {
        Animal expectedAnimal = new Animal();
        expectedAnimal.setName("Bigotes");
        expectedAnimal.setBreed("Siames");
        expectedAnimal.setGender("Male");
        expectedAnimal.setVaccinated(false);
        List<Animal> expectedAnimalList = new ArrayList<>();
        expectedAnimalList.add(expectedAnimal);
        Mockito.when(animalService.getAll()).thenReturn(expectedAnimalList);

        ResponseEntity<Collection<AnimalDto>> actualResponse = animalController.listAnimals();
        List<AnimalDto> actualAnimalList = (List<AnimalDto>) actualResponse.getBody();

        assertThat(actualResponse.getStatusCode().is2xxSuccessful(), is(true));
        assertThat(actualAnimalList, hasSize(expectedAnimalList.size()));
        AnimalDto actualAnimal = actualAnimalList.get(0);
        assertThat(actualAnimal.getName(), equalTo(expectedAnimal.getName()));
        assertThat(actualAnimal.getGender(), equalTo(expectedAnimal.getGender()));
        assertThat(actualAnimal.getBreed(), equalTo(expectedAnimal.getBreed()));
        assertThat(actualAnimal.isVaccinated(), equalTo(expectedAnimal.isVaccinated()));
    }
}
