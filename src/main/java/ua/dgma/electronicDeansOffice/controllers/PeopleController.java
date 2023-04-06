package ua.dgma.electronicDeansOffice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.dgma.electronicDeansOffice.exceptions.CustomException;
import ua.dgma.electronicDeansOffice.exceptions.ErrorResponse;
import ua.dgma.electronicDeansOffice.exceptions.NotFoundException;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.person.*;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.collections.PersonListMapper;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces.PersonMapper;
import ua.dgma.electronicDeansOffice.models.Person;
import ua.dgma.electronicDeansOffice.services.impl.PersonServiceImpl;
import ua.dgma.electronicDeansOffice.services.interfaces.PeopleService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/people")
public class PeopleController {
    private final PeopleService<Person> personService;
    private final PersonMapper personMapper;
    private final PersonListMapper personListMapper;

    @Autowired
    public PeopleController(PersonServiceImpl personService,
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
    public List<PersonGetDTO> findPersonByEmail(@RequestParam("email") String email) {
        return personListMapper.toPeopleGetDTO(personService.findByEmail(email));
    }

    @GetMapping("/slim/findByEmail")
    @ResponseStatus(HttpStatus.FOUND)
    public List<PersonSlimGetDTO> findSlimPersonByEmail(@RequestParam("email") String email) {
        return personListMapper.toPeopleSlimGetDTO(personService.findByEmail(email));
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
    public List<PersonSlimGetDTO> findAllSlimPeople(@RequestParam(value = "page", required = false) Integer page,
                                                    @RequestParam(value = "peoplePerPage", required = false) Integer peoplePerPage,
                                                    @RequestParam(value = "isDeleted", required = false, defaultValue = "false") Boolean isDeleted,
                                                    @RequestParam(value = "faculty", required = false) String facultyName) {
        return personListMapper.toPeopleSlimGetDTO(personService.findAllPeople(page, peoplePerPage, isDeleted, facultyName));
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
                             @RequestBody @Valid  PersonPatchDTO personPatchDTO,
                                                  BindingResult bindingResult) {
        Person updatedPerson = personMapper.toPerson(personPatchDTO);

        personService.updateByUid(uid, updatedPerson, bindingResult);
    }

    @DeleteMapping("/delete")
    public void deletePerson(@RequestParam("uid") Long uid) {
        personService.deleteByUId(uid);
    }

    @DeleteMapping("/soft/delete")
    public void softDeletePerson(@RequestParam("uid") Long uid) {
        personService.softDeleteByUId(uid);
    }

}
