package ua.dgma.electronicDeansOffice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.Person.PersonGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.Person.PersonsGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.impl.PersonMapperImpl;
import ua.dgma.electronicDeansOffice.models.Person;
import ua.dgma.electronicDeansOffice.services.impl.PersonServiceImpl;

import javax.validation.Valid;

@RestController
@RequestMapping("/people")
public class PeopleController {

    private final PersonServiceImpl personService;

    private final PersonMapperImpl personMapper;

    @Autowired
    public PeopleController(PersonServiceImpl personService, PersonMapperImpl personMapper) {
        this.personService = personService;
        this.personMapper = personMapper;
    }


    @GetMapping("/uid")
    public PersonGetDTO findPersonByUid(@RequestParam(value = "uid") Long uid){
        return personMapper.convertToPersonDTO(personService.findOnePersonByUid(uid));
    }

    @GetMapping("/email")
    public PersonGetDTO findPersonByEmail(@RequestParam(value = "email") String email){
        return personMapper.convertToPersonDTO(personService.findOnePersonByEmail(email));
    }

    @GetMapping("/surname")
    public PersonGetDTO findPersonBySurname(@RequestParam("surname") String surname){
        return personMapper.convertToPersonDTO(personService.findOnePersonBySurname(surname));
    }

    @GetMapping()
    public PersonsGetDTO findAllPeople(){
        return personMapper.convertPersonsToPersonsDTO(personService.findAllPeople());
    }

    @PostMapping
    public void registerNewPerson(@RequestBody @Valid PersonGetDTO personGetDTO,
                                                      BindingResult bindingResult){
        Person newPerson = personMapper.convertToPerson(personGetDTO);

        //validator

        personService.registerNewPerson(newPerson);
    }

    @PatchMapping("/update/uid")
    public void updatePersonByUid(@RequestParam("uid") Long uid,
                             @RequestBody @Valid PersonGetDTO personGetDTO){
        Person updatedPerson = personMapper.convertToPerson(personGetDTO);
        personService.updatePersonByUid(uid, updatedPerson);
    }

    @PatchMapping("/update/email")
    public void updatePersonByEmail(@RequestParam("email") String email,
                                  @RequestBody @Valid PersonGetDTO personGetDTO){
        Person updatedPerson = personMapper.convertToPerson(personGetDTO);
        personService.updatePersonByEmail(email, updatedPerson);
    }

}
