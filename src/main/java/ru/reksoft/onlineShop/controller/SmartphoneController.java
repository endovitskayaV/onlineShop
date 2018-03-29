//package ru.reksoft.onlineShop.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import ru.reksoft.onlineShop.domain.entity.ItemEntity;
//import ru.reksoft.onlineShop.domain.service.SmartphoneService;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
//@Controller
//public class SmartphoneController {
//
//    private SmartphoneService smartphoneService;
//
//    @Autowired
//    SmartphoneController(SmartphoneService smartphoneService){
//        this.smartphoneService=smartphoneService;
//    }
//
//    @RequestMapping(value = "/", method = RequestMethod.GET)
//    public String index(Model model) {
//        List<ItemEntity> s=new ArrayList<>();
//        ItemEntity sm=new ItemEntity();
//        sm.setName("name1");
//
//        ItemEntity sm2=new ItemEntity();
//        sm2.setName("name2");
//        s.add(sm);
//        s.add(sm2);
//        model.addAttribute("smartphones", s);
//       // model.addAttribute("smartphones",  smartphoneService.getAll());
//        return "index";
//    }
//
//    @ModelAttribute("")
//    public List<ItemEntity> smartphones(){
//        return smartphoneService.getAll();
//    }
//
//
//}
