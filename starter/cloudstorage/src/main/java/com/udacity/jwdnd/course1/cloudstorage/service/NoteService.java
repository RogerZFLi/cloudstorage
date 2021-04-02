package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public void createOrUpdateNote(Note note){
        if(note.getNoteId() < 1)
            noteMapper.insertNote(note);
        else
            noteMapper.updateNote(note);
    }

    public List<Note> getAllNotes(Integer userId) {
        return noteMapper.getAllNotes(userId);
    }

    public List<Note> getNotesByUserId(Integer userId) {
        return noteMapper.getNotesByUserId(userId);
    }

    public List<Note> getNotesByTitle(String noteTitle, Integer userId){
        return noteMapper.getNotesByTitle(noteTitle, userId);
    }

    public Note getNoteById(Integer noteId, Integer userId) {
        return noteMapper.getNoteById(noteId, userId);
    }



    public void deleteNote(Integer noteId) {
        noteMapper.deleteNote(noteId);
    }


}
