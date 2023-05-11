package com.shelter.animalback.unit.service;

import com.shelter.animalback.domain.Animal;
import com.shelter.animalback.exceptions.AnimalNotFoundException;
import com.shelter.animalback.model.AnimalDao;
import com.shelter.animalback.repository.AnimalRepository;
import com.shelter.animalback.service.AnimalServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class AnimalServiceImpTest {
    @Mock
    private AnimalRepository animalRepository;

    @InjectMocks
    private AnimalServiceImp animalServiceImp;

    private AnimalDao expectedAnimalWithoutVaccines;

    @BeforeEach
    public void setup(){
        expectedAnimalWithoutVaccines = new AnimalDao();
        expectedAnimalWithoutVaccines.setId(1L);
        expectedAnimalWithoutVaccines.setName("Bigotes");
        expectedAnimalWithoutVaccines.setBreed("Siames");
        expectedAnimalWithoutVaccines.setGender("Male");
        expectedAnimalWithoutVaccines.setVaccinated(false);
    }

    @Test
    public void testGetAll() {
        List<AnimalDao> expectedAnimalList = new ArrayList<AnimalDao>();
        expectedAnimalList.add(expectedAnimalWithoutVaccines);
        Mockito.when(animalRepository.findAll()).thenReturn(expectedAnimalList);

        List<Animal> actualAnimalList = animalServiceImp.getAll();

        assertThat(actualAnimalList, hasSize(expectedAnimalList.size()));
        Animal actualAnimal = actualAnimalList.get(0);
        assertThat(actualAnimal.getId(), equalTo(expectedAnimalWithoutVaccines.getId()));
        assertThat(actualAnimal.getName(), equalTo(expectedAnimalWithoutVaccines.getName()));
        assertThat(actualAnimal.getGender(), equalTo(expectedAnimalWithoutVaccines.getGender()));
        assertThat(actualAnimal.getBreed(), equalTo(expectedAnimalWithoutVaccines.getBreed()));
        assertThat(actualAnimal.isVaccinated(), equalTo(expectedAnimalWithoutVaccines.isVaccinated()));
    }

    @Test
    public void testGetByName_whenAnimalDoesNotExist(){
        Mockito.when(animalRepository.findByName(any(String.class))).thenReturn(null);
        assertThrows(AnimalNotFoundException.class, () -> animalServiceImp.get("Manchas"));
    }

    @Test
    public void testGetByName_whenAnimalExist(){
        Mockito.when(animalRepository.findByName(any(String.class))).thenReturn(expectedAnimalWithoutVaccines);

        Animal actualAnimal = animalServiceImp.get("Manchas");

        assertThat(actualAnimal.getId(), equalTo(expectedAnimalWithoutVaccines.getId()));
        assertThat(actualAnimal.getName(), equalTo(expectedAnimalWithoutVaccines.getName()));
        assertThat(actualAnimal.getGender(), equalTo(expectedAnimalWithoutVaccines.getGender()));
        assertThat(actualAnimal.getBreed(), equalTo(expectedAnimalWithoutVaccines.getBreed()));
        assertThat(actualAnimal.isVaccinated(), equalTo(expectedAnimalWithoutVaccines.isVaccinated()));

    }

    @Test
    public void testDelete_whenAnimalDoesNotExist(){
        Mockito.when(animalRepository.findByName(any(String.class))).thenReturn(null);
        assertThrows(AnimalNotFoundException.class, () -> animalServiceImp.delete("Manchas"));
    }

    @Test
    public void testDelete_whenAnimalExists(){
        Mockito.when(animalRepository.findByName(any(String.class))).thenReturn(expectedAnimalWithoutVaccines);

        animalServiceImp.delete("Bigotes");

        Mockito.verify(animalRepository).delete(expectedAnimalWithoutVaccines);
    }
}