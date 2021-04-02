package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.FileForm;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.service.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.websocket.server.PathParam;


@Controller
@RequestMapping("/")//navigates the root url to home if logged in, to login page if not logged in yet
public class HomeController {
    private final UserService userService;
    private final SimpleFileService simpleFileService;
    private final NoteService noteService;
    private final CredentialService credentialService;
    private final EncryptionService encryptionService;

    public HomeController(UserService userService,
                          SimpleFileService simpleFileService,
                          NoteService noteService,
                          CredentialService credentialService,
                          EncryptionService encryptionService) {
        this.userService = userService;
        this.simpleFileService = simpleFileService;
        this.noteService = noteService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
    }

    @ModelAttribute
    public FileForm fileForm() {
        return new FileForm();
    }

    @ModelAttribute
    public Note note() {
        return new Note();
    }

    @ModelAttribute
    public Credential credential(){
        return new Credential();
    }
    @GetMapping("/home")
    public String viewHomePage(Authentication auth, Model model, @PathParam("noteActive")boolean noteActive, @PathParam("credentialActive") boolean credentialActive) {
        User user = userService.getUser(auth.getName());
        //Redirects to login page if the authentication expires
        if(user== null) return "redirect:/login";
        model.addAttribute("files", simpleFileService.getAllFiles(user.getUserId()));
        model.addAttribute("notes", noteService.getAllNotes(userService.getUser(auth.getName()).getUserId()));
        model.addAttribute("credentials", credentialService.getAllCredentials(userService.getUser(auth.getName()).getUserId()));
        model.addAttribute("encryptionService", encryptionService);
        //adds to verify which tab it should go
        //Activates and shows Files tab by default (when both {noteActive} and {credentialActive} are false)
        //Activates and shows Notes tab if {noteActive} is true;
        //Activates and shows Credential tab if {credentialActive} is true;
        model.addAttribute("noteActive", noteActive);
        model.addAttribute("credentialActive", credentialActive);
        return "home";
    }
}
