package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.service.SimpleFileService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller

public class FilesController {
    private final UserService userService;
    private final SimpleFileService simpleFileService;

    public FilesController(UserService userService,
                           SimpleFileService simpleFileService) {
        this.userService = userService;
        this.simpleFileService = simpleFileService;


    }

    @ModelAttribute
    public FileForm fileForm() {
        return new FileForm();
    }
//    @ModelAttribute
//    public Note note() {
//        return new Note();
//    }
//
//    @ModelAttribute
//    public Credential credential(){
//        return new Credential();
//    }


    @PostMapping("/files")
    public String uploadFile(@ModelAttribute("file") MultipartFile file, Authentication auth, Model model, RedirectAttributes redirectAttributes) {
        User user = userService.getUser(auth.getName());
        //Redirects to login page if authentication expires
        if(user== null) return "redirect:/login";
        String error = null;
        if (file==null)
            error = "Please choose a file before uploading";
        assert file != null;
        if (file.getSize() == 0)
            error = "The chosen file is empty";
        if(simpleFileService.fileExists(file,user.getUserId())){
            error="The file with the same name already exists";
        }
        if(error==null) {
            int fileId = simpleFileService.uploadFile(file, user.getUserId());
            if (fileId < 0)
                error = "File upload failed";
        }
        if (error == null) {
            model.addAttribute("success", true);
            model.addAttribute("message", "File uploaded successfully");
            //Redirects to home page if action succeeds and error free
            //Passes 2 boolean variables to decide which tab to activate and show
            //Activates and shows Files tab after redirecting to home page if {noteActive} and {credentialActive} are both false;
//            return "redirect:/home?noteActive=false&credentialActive=false";
        }else
            model.addAttribute("error", error);
        //Goes to result page if error happens during file uploading process
        model.addAttribute("noteActive", false);
        model.addAttribute("credentialActive", false);
        return "result";
    }

    @GetMapping("/files/view/{fileId}")
    public ResponseEntity<Resource> viewOrDownloadFile(@PathVariable() Integer fileId, Authentication auth, Model model) {
        User user = userService.getUser(auth.getName());
        if(user== null) return null;
        SimpleFile file = null;
        try {
            file = simpleFileService.downloadFile(fileId);

        }catch (Exception e){
            model.addAttribute("error", e.getMessage());
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContentType())).header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""
                        + file.getFileName() + "\"").body(new
                        ByteArrayResource(file.getFileData()));
    }

    @GetMapping("/files/{fileId}")
    public String deleteFile(@PathVariable("fileId") Integer fileId, Authentication auth, Model model, RedirectAttributes redirectAttributes){
        User user = userService.getUser(auth.getName());
        if(user== null) return "redirect:/login";
        if(fileId > 0)
            try {
                simpleFileService.deleteFile(fileId);
                model.addAttribute("message", "The file was deleted");
                model.addAttribute("success", true);
            }catch (Exception e){
                model.addAttribute("error", e.getMessage());
                model.addAttribute("noteActive", false);
                model.addAttribute("credentialActive", false);
                return "result";
            }
        //Redirects to home page if action succeeds and error free
        //Passes 2 boolean variables to decide which tab to activate and show
        //Activates and shows Files tab after redirecting to home page if {noteActive} and {credentialActive} are both false;
        return "redirect:/home?noteActive=false&credentialActive=false";
    }
}
