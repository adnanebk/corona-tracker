package com.adnanbk.coronatracker.Controllers;


import com.adnanbk.coronatracker.models.Statistic;
import com.adnanbk.coronatracker.services.CoronaDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/stat/")
public class RestController {
    private CoronaDataService coronaDataService;

   @Autowired
    public RestController(CoronaDataService coronaDataService) {

        this.coronaDataService = coronaDataService;
    }


    @GetMapping
    public ResponseEntity<Map<String, Statistic>> GatStatis(@RequestParam(value = "date") String date, @RequestParam(value = "name",defaultValue = "morocco") String coutryName,@RequestParam(value ="startDay",defaultValue = "7") int startDay){

        try {
            if (date==null || date.isEmpty())
            return  ResponseEntity.ok(coronaDataService.GetStatList(LocalDate.now(),coutryName,startDay));
            else
            return  ResponseEntity.ok(coronaDataService.GetStatList(LocalDate.parse(date),coutryName,startDay));
        } catch (IOException e) {
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }

    }
    @GetMapping("/world")
    public ResponseEntity<List<Statistic>> WorldStatis(){
        return  ResponseEntity.ok(coronaDataService.GetWorldStatList());
    }

}
