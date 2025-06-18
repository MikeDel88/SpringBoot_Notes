package com.notes.notes.controller

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*
import org.springframework.http.ResponseEntity

// Import de ton modèle et repo
import com.notes.notes.model.Note
import com.notes.notes.repository.NotesRepository


@RestController
class NotesController(private val repository: NotesRepository) {

    @GetMapping("/notes")
    fun getNotes(): List<Note> {
        return repository.findAll();
    }

    @PostMapping("/notes")
    fun setNotes(@RequestBody note: Note): Note? {
        return repository.save(note)
    }
}