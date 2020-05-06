package com.newcoder.community.controller;

import com.newcoder.community.service.DataService;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

@Controller
public class DataController {
    @Autowired
    private DataService dataService;

    @RequestMapping(path = "/data",method = {RequestMethod.GET ,RequestMethod.POST})
    public String getDataPage() {
        return "/site/admin/data";
    }

    @RequestMapping(path ="/data/uv" ,method = RequestMethod.POST)
    public String getUV(@DateTimeFormat(pattern = "yyyy-MM-dd") Date start, @DateTimeFormat(pattern = "yyyy-MM-dd")Date end, Model model) {
        long uv = dataService.calculateUV(start, end);
        model.addAttribute("uvResult",uv);
        model.addAttribute("uvStartDate",start);
        model.addAttribute("uvEndDate",end);
        return "/site/admin/data";
    }


}
