package com.shelter.animalback.contract.api.animal;

import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import au.com.dius.pact.provider.junitsupport.loader.PactBrokerAuth;
import au.com.dius.pact.provider.spring.junit5.MockMvcTestTarget;
import com.shelter.animalback.controller.AnimalController;
import com.shelter.animalback.service.interfaces.AnimalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import au.com.dius.pact.provider.junitsupport.State;
import com.shelter.animalback.domain.Animal;
import org.mockito.Mockito;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@Provider("CatShelterBack")
@PactBroker(
        url = "${PACT_BROKER_BASE_URL}",
        authentication = @PactBrokerAuth(token = "${PACT_BROKER_TOKEN}") )
public class AnimalTest {

    @Mock
    private AnimalService animalService;

    @InjectMocks //controller real
    private AnimalController animalController;

    @BeforeEach
    public void changeContext(PactVerificationContext context) {
        System.setProperty("pact.verifier.publishResults", "true");
        MockMvcTestTarget testTarget = new MockMvcTestTarget();
        testTarget.setControllers(animalController);
        context.setTarget(testTarget);
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void pactVerificationTestTemplate(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @State("has animals")
    public void listAnimals() {
        Animal animal = new Animal();
        animal.setName("Bigotes");
        animal.setBreed("Siames");
        animal.setGender("Male");
        animal.setVaccinated(false);
        List<Animal> animals = new ArrayList<Animal>();
        animals.add(animal);
        Mockito.when(animalService.getAll()).thenReturn(animals);
    }

    @State("there are no animals")
    public void saveAnimals() {
        Animal animal = new Animal();
        animal.setName("Loli");
        animal.setBreed("Birmano");
        animal.setGender("Male");
        animal.setVaccinated(true);
        Mockito.when(animalService.save(Mockito.any(Animal.class))).thenReturn(animal);
    }
}
