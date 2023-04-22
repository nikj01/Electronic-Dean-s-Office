package ua.dgma.electronicDeansOffice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.deaneryWorker.DeaneryWorkerGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.deaneryWorker.DeaneryWorkerPatchDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.deaneryWorker.DeaneryWorkerPostDTO;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.deaneryWorker.DeaneryWorkerSlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.collections.DeaneryWorkerListMapper;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces.DeaneryWorkerMapper;
import ua.dgma.electronicDeansOffice.models.DeaneryWorker;
import ua.dgma.electronicDeansOffice.services.impl.data.FindAllData;
import ua.dgma.electronicDeansOffice.services.impl.data.person.RegisterPersonData;
import ua.dgma.electronicDeansOffice.services.impl.data.person.UpdatePersonData;
import ua.dgma.electronicDeansOffice.services.interfaces.PeopleService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/deaneryWorkers")
public class DeaneryWorkersController {
    private final PeopleService<DeaneryWorker> deaneryWorkerService;
    private final DeaneryWorkerMapper deaneryWorkerMapper;
    private final DeaneryWorkerListMapper deaneryWorkerListMapper;

    @Autowired
    public DeaneryWorkersController(PeopleService<DeaneryWorker> deaneryWorkerService,
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
    public List<DeaneryWorkerSlimGetDTO> findSlimDeaneryWorkerByEmail(@RequestParam("email") String email) {
        return deaneryWorkerListMapper.toDeaneryWorkersSlimGetDTO(deaneryWorkerService.findByEmail(email));
    }

    @GetMapping("/findBySurname")
    @ResponseStatus(HttpStatus.FOUND)
    public List<DeaneryWorkerSlimGetDTO> findSlimDeaneryWorkerBySurname(@RequestParam("surname") String surname) {
        return deaneryWorkerListMapper.toDeaneryWorkersSlimGetDTO(deaneryWorkerService.findBySurname(surname));
    }

    @GetMapping()
    public List<DeaneryWorkerSlimGetDTO> findAllSlimDeaneryWorkers(@RequestParam(value = "page", required = false) Integer page,
                                                                   @RequestParam(value = "peoplePerPage", required = false) Integer peoplePerPage,
                                                                   @RequestParam(value = "deleted", required = false, defaultValue = "false") Boolean deleted,
                                                                   @RequestParam(value = "faculty", required = false) Long facultyId) {
        return deaneryWorkerListMapper.toDeaneryWorkersSlimGetDTO(deaneryWorkerService.findAllPeople(new FindAllData(page, peoplePerPage, deleted, facultyId)));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerNewDeaneryWorker(@RequestBody @Valid DeaneryWorkerPostDTO deaneryWorkerPostDTO,
                                                             BindingResult bindingResult) {
        DeaneryWorker newDeaneryWorker = deaneryWorkerMapper.toDeaneryWorker(deaneryWorkerPostDTO);

        deaneryWorkerService.register(new RegisterPersonData<>(newDeaneryWorker, bindingResult));
    }

    @PatchMapping("/update")
    public void updateDeaneryWorker(@RequestParam("uid") Long uid,
                                    @RequestBody @Valid  DeaneryWorkerPatchDTO deaneryWorkerPatchDTO,
                                                         BindingResult bindingResult) {
        DeaneryWorker updatedDeaneryWorker = deaneryWorkerMapper.toDeaneryWorker(deaneryWorkerPatchDTO);

        deaneryWorkerService.update(new UpdatePersonData<>(uid, updatedDeaneryWorker, bindingResult));
    }

    @DeleteMapping("/delete")
    public void deleteDeaneryWorker(@RequestParam("uid") Long uid) {
        deaneryWorkerService.delete(uid);
    }

    @DeleteMapping("/soft/delete")
    public void softDeletePerson(@RequestParam("uid") Long uid) {
        deaneryWorkerService.softDelete(uid);
    }

}
