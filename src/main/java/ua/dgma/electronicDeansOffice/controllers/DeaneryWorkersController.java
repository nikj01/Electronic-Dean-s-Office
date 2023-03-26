package ua.dgma.electronicDeansOffice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.dgma.electronicDeansOffice.exceptions.CustomException;
import ua.dgma.electronicDeansOffice.exceptions.ErrorResponse;
import ua.dgma.electronicDeansOffice.exceptions.NotFoundException;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.deaneryWorker.*;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.collections.DeaneryWorkerListMapper;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces.DeaneryWorkerMapper;
import ua.dgma.electronicDeansOffice.models.DeaneryWorker;
import ua.dgma.electronicDeansOffice.services.impl.DeaneryWorkerServiceImpl;
import ua.dgma.electronicDeansOffice.services.interfaces.PeopleService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/deaneryWorkers")
public class DeaneryWorkersController {

    private final DeaneryWorkerServiceImpl deaneryWorkerService;
    private final DeaneryWorkerMapper deaneryWorkerMapper;
    private final DeaneryWorkerListMapper deaneryWorkerListMapper;

    @Autowired
    public DeaneryWorkersController(DeaneryWorkerServiceImpl deaneryWorkerService,
                                    DeaneryWorkerMapper deaneryWorkerMapper,
                                    DeaneryWorkerListMapper deaneryWorkerListMapper) {
        this.deaneryWorkerService = deaneryWorkerService;
        this.deaneryWorkerMapper = deaneryWorkerMapper;
        this.deaneryWorkerListMapper = deaneryWorkerListMapper;
    }

    @GetMapping("/findByUid")
    @ResponseStatus(HttpStatus.FOUND)
    public DeaneryWorkerGetDTO findDeaneryWorkerByUid(@RequestParam("uid") Long uid) {
        return deaneryWorkerMapper.toDeaneryWorkerGetDTO(deaneryWorkerService.findByUid(uid));
    }

    @GetMapping("/slim/findByUid")
    @ResponseStatus(HttpStatus.FOUND)
    public DeaneryWorkerSlimGetDTO findSlimDeaneryWorkerByUid(@RequestParam("uid") Long uid) {
        return deaneryWorkerMapper.toDeaneryWorkerSlimGetDTO(deaneryWorkerService.findByUid(uid));
    }

    @GetMapping("/findByEmail")
    @ResponseStatus(HttpStatus.FOUND)
    public DeaneryWorkerGetDTO findDeaneryWorkerByEmail(@RequestParam("email") String email) {
        return deaneryWorkerMapper.toDeaneryWorkerGetDTO(deaneryWorkerService.findByEmail(email));
    }

    @GetMapping("/slim/findByEmail")
    @ResponseStatus(HttpStatus.FOUND)
    public DeaneryWorkerSlimGetDTO findSlimDeaneryWorkerByEmail(@RequestParam("email") String email) {
        return deaneryWorkerMapper.toDeaneryWorkerSlimGetDTO(deaneryWorkerService.findByEmail(email));
    }

    @GetMapping("/findBySurname")
    @ResponseStatus(HttpStatus.FOUND)
    public List<DeaneryWorkerGetDTO> findDeaneryWorkerBySurname(@RequestParam("surname") String surname) {
        return deaneryWorkerListMapper.toDeaneryWorkersGetDTO(deaneryWorkerService.findBySurname(surname));
    }

    @GetMapping("/slim/findBySurname")
    @ResponseStatus(HttpStatus.FOUND)
    public List<DeaneryWorkerSlimGetDTO> findSlimDeaneryWorkerBySurname(@RequestParam("surname") String surname) {
        return deaneryWorkerListMapper.toDeaneryWorkersSlimGetDTO(deaneryWorkerService.findBySurname(surname));
    }

    @GetMapping()
    public List<DeaneryWorkerGetDTO> findAllDeaneryWorkers(@RequestParam(value = "page", required = false) Integer page,
                                                           @RequestParam(value = "people_per_page", required = false) Integer peoplePerPage,
                                                           @RequestParam(value = "isDeleted", required = false, defaultValue = "false") Boolean isDeleted) {
        return deaneryWorkerListMapper.toDeaneryWorkersGetDTO(deaneryWorkerService.findAllWithPaginationOrWithout(page, peoplePerPage, isDeleted));
    }

    @GetMapping("/slim")
    public List<DeaneryWorkerSlimGetDTO> findAllSlimDeaneryWorkers(@RequestParam(value = "page", required = false) Integer page,
                                                                   @RequestParam(value = "people_per_page", required = false) Integer peoplePerPage,
                                                                   @RequestParam(value = "isDeleted", required = false, defaultValue = "false") Boolean isDeleted) {
        return deaneryWorkerListMapper.toDeaneryWorkersSlimGetDTO(deaneryWorkerService.findAllWithPaginationOrWithout(page, peoplePerPage, isDeleted));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerNewDeaneryWorker(@RequestBody @Valid DeaneryWorkerPostDTO newPostDeaneryWorker,
                                                             BindingResult bindingResult) {
        DeaneryWorker newDeaneryWorker = deaneryWorkerMapper.toDeaneryWorker(newPostDeaneryWorker);

        deaneryWorkerService.registerNew(newDeaneryWorker, bindingResult);
    }

    @PatchMapping("/update")
    public void updateDeaneryWorker(@RequestParam("uid") Long uid,
                                    @RequestBody @Valid  DeaneryWorkerPostDTO updatedPostDeaneryWorker,
                                                         BindingResult bindingResult) {
        DeaneryWorker updatedDeaneryWorker = deaneryWorkerMapper.toDeaneryWorker(updatedPostDeaneryWorker);

        deaneryWorkerService.updateByUid(uid, updatedDeaneryWorker, bindingResult);
    }

    @DeleteMapping("/delete")
    public void deleteDeaneryWorker(@RequestParam("uid") Long uid) {
        deaneryWorkerService.deleteByUId(uid);
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
