package ua.dgma.electronicDeansOffice.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.Person.PersonDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.Person.PersonsResponseDTO;
import ua.dgma.electronicDeansOffice.models.Person;
import ua.dgma.electronicDeansOffice.services.impl.PersonServiceImpl;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/people")
public class PeopleController {

    private final PersonServiceImpl personService;

    @Autowired
    public PeopleController(PersonServiceImpl personService) {
        this.personService = personService;
    }

    @GetMapping("/hi")
    public String sayHello(){
        return "Hello";
    }

    @GetMapping("/uid")
    public PersonDTO findPersonByUid(@RequestParam(value = "uid") Long uid){
        return personService.findOneByUid(uid);
    }

    @GetMapping("/email")
    public PersonDTO findPersonByEmail(@RequestParam(value = "email") String email){
        return personService.findOneByEmail(email);
    }

    @GetMapping("/surname")
    public PersonDTO findPersonBySurname(@RequestParam("surname") String surname){
        return personService.findOneBySurname(surname);
    }

    @GetMapping()
    public PersonsResponseDTO findAllPeople(){
        return new PersonsResponseDTO(personService.findAll());
    }

    @PostMapping
    public void registerNewPerson(@RequestBody @Valid PersonDTO personDTO){
        personService.registerNewPerson(personDTO);
    }

}
