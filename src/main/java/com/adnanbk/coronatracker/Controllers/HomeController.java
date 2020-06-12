package com.adnanbk.coronatracker.Controllers;

import com.adnanbk.coronatracker.services.CoronaDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {


    private final CoronaDataService coronaDataService;

   @Autowired
    public HomeController(CoronaDataService coronaDataService) {

        this.coronaDataService = coronaDataService;
    }

    @GetMapping("/")
    public String GetStaistics(Model model){
        model.addAttribute("moroccoStat",coronaDataService.GetMoroccoStatData());
        return "index";
    }

}
