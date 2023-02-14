package ua.dgma.electronicDeansOffice.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.dgma.electronicDeansOffice.dtos.PersonDTO;
import ua.dgma.electronicDeansOffice.models.Person;
import ua.dgma.electronicDeansOffice.services.impl.PersonServiceImpl;

@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonServiceImpl personService;
    private final ModelMapper modelMapper;

    @Autowired
    public PersonController(PersonServiceImpl personService,
                            ModelMapper modelMapper) {
        this.personService = personService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/{uid}")
    public PersonDTO findPersonByUid(@PathVariable("uid") Long uid){
        return convertToPersonDTO(personService.findOneByUid(uid));
    }

    @GetMapping("/hi")
    public String sayHello(){
        return "Hello";
    }

    private PersonDTO convertToPersonDTO(Person person){
        return modelMapper.map(person, PersonDTO.class);
    }

    private Person convertToPerson(PersonDTO personDTO){
        return modelMapper.map(personDTO, Person.class);
    }
}
