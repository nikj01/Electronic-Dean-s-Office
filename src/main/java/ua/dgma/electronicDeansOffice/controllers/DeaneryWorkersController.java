package ua.dgma.electronicDeansOffice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.dgma.electronicDeansOffice.exceptions.CustomException;
import ua.dgma.electronicDeansOffice.exceptions.ErrorResponse;
import ua.dgma.electronicDeansOffice.exceptions.people.NotFoundException;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.deaneryWorker.*;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces.DeaneryWorkerMapper;
import ua.dgma.electronicDeansOffice.models.DeaneryWorker;
import ua.dgma.electronicDeansOffice.services.impl.DeaneryWorkerServiceImpl;

import javax.validation.Valid;

@RestController
@RequestMapping("/deaneryWorkers")
public class DeaneryWorkersController {

    private final DeaneryWorkerServiceImpl deaneryWorkerService;
    private final DeaneryWorkerMapper deaneryWorkerMapper;

    @Autowired
    public DeaneryWorkersController(DeaneryWorkerServiceImpl deaneryWorkerService, DeaneryWorkerMapper deaneryWorkerMapper) {
        this.deaneryWorkerService = deaneryWorkerService;
        this.deaneryWorkerMapper = deaneryWorkerMapper;
    }

    @GetMapping("/findByUid")
    @ResponseStatus(HttpStatus.FOUND)
    public DeaneryWorkerGetDTO findDeaneryWorkerByUid(@RequestParam("uid") Long uid) {
        return deaneryWorkerMapper.convertToDeaneryWorkerGetDTO(deaneryWorkerService.findByUid(uid));
    }

    @GetMapping("/slim/findByUid")
    @ResponseStatus(HttpStatus.FOUND)
    public DeaneryWorkerSlimGetDTO findSlimDeaneryWorkerByUid(@RequestParam("uid") Long uid) {
        return deaneryWorkerMapper.convertToDeaneryWorkerSlimGetDTO(deaneryWorkerService.findByUid(uid));
    }

    @GetMapping("/findByEmail")
    @ResponseStatus(HttpStatus.FOUND)
    public DeaneryWorkerGetDTO findDeaneryWorkerByEmail(@RequestParam("email") String email) {
        return deaneryWorkerMapper.convertToDeaneryWorkerGetDTO(deaneryWorkerService.findByEmail(email));
    }

    @GetMapping("/slim/findByEmail")
    @ResponseStatus(HttpStatus.FOUND)
    public DeaneryWorkerSlimGetDTO findSlimDeaneryWorkerByEmail(@RequestParam("email") String email) {
        return deaneryWorkerMapper.convertToDeaneryWorkerSlimGetDTO(deaneryWorkerService.findByEmail(email));
    }

    @GetMapping("/findBySurname")
    @ResponseStatus(HttpStatus.FOUND)
    public DeaneryWorkersGetDTO findDeaneryWorkerBySurname(@RequestParam("surname") String surname) {
        return deaneryWorkerMapper.convertToDeaneryWorkersGetDTO(deaneryWorkerService.findBySurname(surname));
    }

    @GetMapping("/slim/findBySurname")
    @ResponseStatus(HttpStatus.FOUND)
    public DeaneryWorkersSlimGetDTO findSlimDeaneryWorkerBySurname(@RequestParam("surname") String surname) {
        return deaneryWorkerMapper.convertToDeaneryWorkersSlimGetDTO(deaneryWorkerService.findBySurname(surname));
    }

    @GetMapping()
    public DeaneryWorkersGetDTO findAllDeaneryWorkers(@RequestParam(value = "page", required = false) Integer page,
                                                      @RequestParam(value = "people_per_page", required = false) Integer peoplePerPage) {
        return deaneryWorkerMapper.convertToDeaneryWorkersGetDTO(deaneryWorkerService.findAllWithPaginationOrWithout(page, peoplePerPage));
    }

    @GetMapping("/slim")
    public DeaneryWorkersSlimGetDTO findAllSlimDeaneryWorkers(@RequestParam(value = "page", required = false) Integer page,
                                                              @RequestParam(value = "people_per_page", required = false) Integer peoplePerPage) {
        return deaneryWorkerMapper.convertToDeaneryWorkersSlimGetDTO(deaneryWorkerService.findAllWithPaginationOrWithout(page, peoplePerPage));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerNewDeaneryWorker(@RequestBody @Valid DeaneryWorkerPostDTO newPostDeaneryWorker,
                                                             BindingResult bindingResult) {
        DeaneryWorker newDeaneryWorker = deaneryWorkerMapper.convertToDeaneryWorker(newPostDeaneryWorker);

        deaneryWorkerService.registerNew(newDeaneryWorker, bindingResult);
    }

    @PatchMapping("/update")
    public void updateDeaneryWorker(@RequestParam("uid") Long uid,
                                    @RequestBody @Valid  DeaneryWorkerPostDTO updatedPostDeaneryWorker,
                                                         BindingResult bindingResult) {
        DeaneryWorker updatedDeaneryWorker = deaneryWorkerMapper.convertToDeaneryWorker(updatedPostDeaneryWorker);

        deaneryWorkerService.updateByUid(uid, updatedDeaneryWorker, bindingResult);
    }

    @DeleteMapping("/delete")
    public void deleteStudent(@RequestParam("uid") Long uid) {
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
