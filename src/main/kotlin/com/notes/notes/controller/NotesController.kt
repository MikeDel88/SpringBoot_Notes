package com.notes.notes.controller

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class NotesController(private val repository: NotesRepository) {

    @GetMapping("/notes")
    fun getNotes(): List<Note> {
        return repository.findAll();
    }

    @PostMapping("/notes"): Note
    fun setNotes(@RequestBody note: Note) {
        return repository.save(note)
    }
}