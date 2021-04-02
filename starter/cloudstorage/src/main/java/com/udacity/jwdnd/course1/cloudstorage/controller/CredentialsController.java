package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.FileForm;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.service.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.service.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CredentialsController {
    private UserService userService;
    private CredentialService credentialService;
    private EncryptionService encryptionService;

    public CredentialsController(UserService userService, CredentialService credentialService, EncryptionService encryptionService) {
        this.userService = userService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
    }


    @ModelAttribute
    public Credential credential() {
        return new Credential();
    }

    @ModelAttribute
    public FileForm fileForm(){return new FileForm();
    }

    @ModelAttribute
    public Note note(){return new Note();
    }

    @PostMapping("/credentials")
    public String addOrEditCredential(Authentication auth, @ModelAttribute("credential") Credential credential, Model model, RedirectAttributes redirectAttributes) {
        User user = userService.getUser(auth.getName());

        //Redirects to login page if authentication expires
        if (user==null) return "redirect:/login";
        String error = null;
        if (credentialService.credentialExists(credential,user.getUserId())){
            error = "Credential with the same username aready exists";
        }
        if(error == null) {
            model.addAttribute("encryptionService", encryptionService);
            credential.setUserId(user.getUserId());
            try {
                credentialService.createOrUpdateCredential(credential);
                model.addAttribute("success",true);
                model.addAttribute("message", "Credential add/edit successfully");

                //Redirects to home page if action succeeds and error free
                //Passes 2 boolean variables to decide which tab to activate and show
                //Activates and shows Files tab after redirecting to home page if {credentialActive} is true and {noteActive} is false;

//                return "redirect:/home?noteActive=false&credentialActive=true";
            }catch (Exception e){
                error = e.getMessage();
                model.addAttribute("error", error);
                //Goes to result page and display the details if error happens
//                return "result";
            }
        }
        model.addAttribute("credentialActive", true);
        model.addAttribute("noteActive", false);
        return "result";

    }
    @GetMapping("/credentials/{credentialId}")
    public String deleteCredential(@PathVariable("credentialId") int credentialId, Authentication auth, Model model, RedirectAttributes redirectAttributes) {
        User user = userService.getUser(auth.getName());
        //Redirects to login page if authentication expires
        if(user== null) return "redirect:/login";

        if(credentialId > 0)
            try {
                credentialService.deleteCredential(credentialId);
                model.addAttribute("message", "The credential was deleted");
                model.addAttribute("success", true);
            }catch (Exception e) {
                model.addAttribute("error", e.getMessage());
                //Goes to result page and display the details if error happens
//                return "result";
            }
        //Redirects to home page if action succeeds and error free
        //Passes 2 boolean variables to decide which tab to activate and show
        //Activates and shows Files tab after redirecting to home page if {credentialActive} is true and {noteActive} is false;

//        return "redirect:/home?noteActive=false&credentialActive=true";
        model.addAttribute("credentialActive", true);
        model.addAttribute("noteActive", false);
        return "result";
    }

}
