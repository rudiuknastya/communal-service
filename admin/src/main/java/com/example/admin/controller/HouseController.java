package com.example.admin.controller;

import com.example.admin.entity.HouseStatus;
import com.example.admin.model.general.SelectSearchRequest;
import com.example.admin.model.houses.*;
import com.example.admin.service.ChairmanService;
import com.example.admin.service.HouseService;
import com.example.admin.validation.general.groups.Create;
import com.example.admin.validation.general.groups.Edit;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/houses")
public class HouseController {
    private final ChairmanService chairmanService;
    private final HouseService houseService;

    public HouseController(ChairmanService chairmanService, HouseService houseService) {
        this.chairmanService = chairmanService;
        this.houseService = houseService;
    }

    @GetMapping("")
    public ModelAndView getHousesPage() {
        return new ModelAndView("houses/houses");
    }

    @GetMapping("/get")
    public @ResponseBody Page<TableHouseResponse> getHouses(FilterRequest filterRequest) {
        return houseService.getHouseResponsesForTable(filterRequest);
    }

    @GetMapping("/new")
    public ModelAndView getCreateHousePage() {
        return new ModelAndView("houses/create-house");
    }

    @PostMapping("/new")
    public @ResponseBody ResponseEntity<?> createHouse(@Validated(Create.class) @ModelAttribute
                                                       HouseRequest houseRequest) {
        houseService.createHouse(houseRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/get-statuses")
    public @ResponseBody HouseStatus[] getStatuses() {
        return HouseStatus.values();
    }

    @GetMapping("/get-chairmen")
    public @ResponseBody Page<ChairmanNameResponse> getChairmen(SelectSearchRequest selectSearchRequest) {
        return chairmanService.getChairmanNameResponses(selectSearchRequest);
    }

    @GetMapping("/edit/{id}")
    public ModelAndView getEditHousePage() {
        return new ModelAndView("houses/edit-house");
    }

    @PostMapping("/edit/{id}")
    public @ResponseBody ResponseEntity<?> updateHouse(@PathVariable Long id,
                                                       @Validated(Edit.class) @ModelAttribute
                                                       HouseRequest houseRequest) {
        houseService.updateHouse(houseRequest, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/edit/get/{id}")
    public @ResponseBody HouseResponse getCreateHousePage(@PathVariable Long id) {
        return houseService.getHouseResponse(id);
    }

    @DeleteMapping("/delete/{id}")
    public @ResponseBody ResponseEntity<?> deleteHouse(@PathVariable Long id) {
        houseService.deleteHouse(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/check-delete/{id}")
    public @ResponseBody boolean checkIfPossibleToDelete(@PathVariable Long id) {
        return houseService.checkIfPossibleToDelete(id);
    }
}
