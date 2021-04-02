package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.service.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class NotesController {
    private UserService userService;
    private NoteService noteService;

    public NotesController(UserService userService, NoteService noteService) {
        this.userService = userService;
        this.noteService = noteService;
    }

    @ModelAttribute
    public Note note(){
        return new Note();
    }

    @PostMapping("/notes")
    public String addOrEditNote(@ModelAttribute("note") Note note, Authentication auth, Model model, RedirectAttributes redirectAttributes){

        User user = userService.getUser(auth.getName());
        if(user== null) return "redirect:/login";
        String error = null;

        note.setUserId(user.getUserId());
        try {
            noteService.createOrUpdateNote(note);
            model.addAttribute("message", "The note was added or edited");
            model.addAttribute("success", true);
        }catch (Exception e){
            model.addAttribute("error", e.getMessage());
//            return "result";
        }
//        return "redirect:/home?noteActive=true&credentialActive=false";
        model.addAttribute("noteActive", true);
        model.addAttribute("credentialActive", false);
        return "result";

    }

    @GetMapping("/notes/{noteId}")
    public String deleteNote(@PathVariable("noteId") Integer noteId, Authentication auth, Model model, RedirectAttributes redirectAttributes) {
        User user = userService.getUser(auth.getName());
        if(user== null) return "redirect:/login";
        if (noteId > 0) {
            try {
                noteService.deleteNote(noteId);
                model.addAttribute("message", "The note was deleted");
                model.addAttribute("success", true);
            } catch (Exception e) {
                model.addAttribute("error", e.getMessage());
//                return "result";
            }
        }
//        return "redirect:/home?noteActive=true&credentialActive=false";
        model.addAttribute("noteActive", true);
        model.addAttribute("credentialActive", false);
        return "result";
    }
}
