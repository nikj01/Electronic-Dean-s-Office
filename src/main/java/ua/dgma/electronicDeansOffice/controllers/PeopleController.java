package ua.dgma.electronicDeansOffice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.dgma.electronicDeansOffice.exceptions.CustomException;
import ua.dgma.electronicDeansOffice.exceptions.ErrorResponse;
import ua.dgma.electronicDeansOffice.exceptions.person.PersonNotFoundException;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.person.*;
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

    @GetMapping("/findByUid")
    @ResponseStatus(HttpStatus.FOUND)
    public PersonGetDTO findPersonByUid(@RequestParam(value = "uid") Long uid){
        return personMapper.convertToPersonGetDTO(personService.findByUid(uid));
    }

    @GetMapping("/slim/findByUid")
    @ResponseStatus(HttpStatus.FOUND)
    public PersonSlimGetDTO findSlimPersonByUid(@RequestParam(value = "uid") Long uid){
        return personMapper.convertToPersonSlimGetDTO(personService.findByUid(uid));
    }

    @GetMapping("/findByEmail")
    @ResponseStatus(HttpStatus.FOUND)
    public PersonGetDTO findPersonByEmail(@RequestParam(value = "email") String email){
        return personMapper.convertToPersonGetDTO(personService.findByEmail(email));
    }

    @GetMapping("/slim/findByEmail")
    @ResponseStatus(HttpStatus.FOUND)
    public PersonSlimGetDTO findSlimPersonByEmail(@RequestParam(value = "email") String email){
        return personMapper.convertToPersonSlimGetDTO(personService.findByEmail(email));
    }

    @GetMapping("/findBySurname")
    @ResponseStatus(HttpStatus.FOUND)
    public PeopleGetDTO findPersonBySurname(@RequestParam("surname") String surname){
        return personMapper.convertToPeopleGetDTO(personService.findBySurname(surname));
    }

    @GetMapping("/slim/findBySurname")
    @ResponseStatus(HttpStatus.FOUND)
    public PeopleSlimGetDTO findSlimPersonBySurname(@RequestParam("surname") String surname){
        return personMapper.convertToPeopleSlimGetDTO(personService.findBySurname(surname));
    }

    @GetMapping()
    public PeopleGetDTO findAllPeople(@RequestParam(value = "page", required = false) Integer page,
                                      @RequestParam(value = "people_per_page", required = false) Integer peoplePerPage){
        return personMapper.convertToPeopleGetDTO(personService.findAllWithPaginationOrWithout(page, peoplePerPage));
    }

    @GetMapping("/slim")
    public PeopleSlimGetDTO findAllSlimPeople(@RequestParam(value = "page", required = false)            Integer page,
                                              @RequestParam(value = "people_per_page", required = false) Integer peoplePerPage){
        return personMapper.convertToPeopleSlimGetDTO(personService.findAllWithPaginationOrWithout(page, peoplePerPage));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerNewPerson(@RequestBody @Valid PersonPostDTO newPostPerson,
                                                      BindingResult bindingResult){
        Person newPerson = personMapper.convertToPerson(newPostPerson);

        personService.registerNew(newPerson, bindingResult);
    }

    @PatchMapping("/update")
    public void updatePerson(@RequestParam("uid") Long uid,
                             @RequestBody @Valid  PersonPostDTO updatedPostPerson,
                                                  BindingResult bindingResult){
        Person updatedPerson = personMapper.convertToPerson(updatedPostPerson);

        personService.updateByUid(uid, updatedPerson, bindingResult);
    }

    @DeleteMapping("/delete")
    public void deletePerson(@RequestParam("uid") Long uid){
        personService.deleteByUId(uid);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(CustomException e){
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PersonNotFoundException.class)
    private ResponseEntity<ErrorResponse> handleExeption(PersonNotFoundException e){
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}
