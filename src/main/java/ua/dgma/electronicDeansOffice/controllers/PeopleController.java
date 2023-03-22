package ua.dgma.electronicDeansOffice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.dgma.electronicDeansOffice.exceptions.CustomException;
import ua.dgma.electronicDeansOffice.exceptions.ErrorResponse;
import ua.dgma.electronicDeansOffice.exceptions.people.NotFoundException;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.person.*;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.collections.PersonListMapper;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces.PersonMapper;
import ua.dgma.electronicDeansOffice.models.Person;
import ua.dgma.electronicDeansOffice.services.impl.PersonService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/people")
public class PeopleController {

    private final PersonService personService;
    private final PersonMapper personMapper;
    private final PersonListMapper personListMapper;

    @Autowired
    public PeopleController(PersonService personService,
                            PersonMapper personMapper,
                            PersonListMapper personListMapper) {
        this.personService = personService;
        this.personMapper = personMapper;
        this.personListMapper = personListMapper;
    }

    @GetMapping("/findByUid")
    @ResponseStatus(HttpStatus.FOUND)
    public PersonGetDTO findPersonByUid(@RequestParam("uid") Long uid) {
        return personMapper.toPersonGetDTO(personService.findByUid(uid));
    }

    @GetMapping("/slim/findByUid")
    @ResponseStatus(HttpStatus.FOUND)
    public PersonSlimGetDTO findSlimPersonByUid(@RequestParam("uid") Long uid) {
        return personMapper.toPersonSlimGetDTO(personService.findByUid(uid));
    }

    @GetMapping("/findByEmail")
    @ResponseStatus(HttpStatus.FOUND)
    public PersonGetDTO findPersonByEmail(@RequestParam("email") String email) {
        return personMapper.toPersonGetDTO(personService.findByEmail(email));
    }

    @GetMapping("/slim/findByEmail")
    @ResponseStatus(HttpStatus.FOUND)
    public PersonSlimGetDTO findSlimPersonByEmail(@RequestParam("email") String email) {
        return personMapper.toPersonSlimGetDTO(personService.findByEmail(email));
    }

    @GetMapping("/findBySurname")
    @ResponseStatus(HttpStatus.FOUND)
    public List<PersonGetDTO> findPersonBySurname(@RequestParam("surname") String surname) {
        return personListMapper.toPeopleGetDTO(personService.findBySurname(surname));
    }

    @GetMapping("/slim/findBySurname")
    @ResponseStatus(HttpStatus.FOUND)
    public List<PersonSlimGetDTO> findSlimPersonBySurname(@RequestParam("surname") String surname) {
        return personListMapper.toPeopleSlimGetDTO(personService.findBySurname(surname));
    }

    @GetMapping()
    public List<PersonGetDTO> findAllPeople(@RequestParam(value = "page", required = false) Integer page,
                                            @RequestParam(value = "people_per_page", required = false) Integer peoplePerPage) {
        return personListMapper.toPeopleGetDTO(personService.findAllWithPaginationOrWithout(page, peoplePerPage));
    }

    @GetMapping("/slim")
    public List<PersonSlimGetDTO> findAllSlimPeople(@RequestParam(value = "page", required = false) Integer page,
                                                    @RequestParam(value = "people_per_page", required = false) Integer peoplePerPage) {
        return personListMapper.toPeopleSlimGetDTO(personService.findAllWithPaginationOrWithout(page, peoplePerPage));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerNewPerson(@RequestBody @Valid PersonPostDTO newPostPerson,
                                                      BindingResult bindingResult) {
        Person newPerson = personMapper.toPerson(newPostPerson);

        personService.registerNew(newPerson, bindingResult);
    }

    @PatchMapping("/update")
    public void updatePerson(@RequestParam("uid") Long uid,
                             @RequestBody @Valid  PersonPostDTO updatedPostPerson,
                                                  BindingResult bindingResult) {
        Person updatedPerson = personMapper.toPerson(updatedPostPerson);

        personService.updateByUid(uid, updatedPerson, bindingResult);
    }

    @DeleteMapping("/delete")
    public void deletePerson(@RequestParam("uid") Long uid) {
        personService.deleteByUId(uid);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(CustomException e) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    private ResponseEntity<ErrorResponse> handleException(NotFoundException e) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}
