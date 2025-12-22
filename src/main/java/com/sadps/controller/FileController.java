//package com.sadps.controller;
//
//import com.sadps.entity.EncryptedFile;
//import com.sadps.services.FileEncryptionService;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/files")
//
//public class FileController {
//
//    private final FileEncryptionService fileService;
//
//    public FileController(FileEncryptionService fileService){
//        this.fileService = fileService;
//    }
//
//    @PostMapping("/upload")
//    @PreAuthorize("hasRole('USER')")
//    public String uploadFIle(...){
//        return fileService.upload(...);
//    }
//    @GetMapping("/my")
//    @PreAuthorize("hasRole('USER')")
//    public List<EncryptedFile> myFiles(){
//        return fileService.getFilesForCurrentUser();
//    }
//
//    @GetMapping("/all")
//    @PreAuthorize("hasRole('ADMIN')")
//    public List<EncryptedFile> allFiles(){
//        return fileService.getAllFiles();
//    }
//
//}
