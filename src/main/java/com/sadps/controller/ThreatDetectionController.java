//package com.sadps.controller;
//
//import com.sadps.entity.ThreatEvent;
//import com.sadps.services.ThreatService;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/threats")
//
//public class ThreatDetectionController {
//
//    private final ThreatService threatService;
//
//    public ThreatDetectionController(ThreatService threatService){
//        this.threatService = threatService;
//    }
//
//    @GetMapping
//    @PreAuthorize("hasRole('ADMIN')")
//    public List<ThreatEvent> getThreats(){
//        return threatService.getAllThreats();
//    }
//
//}
