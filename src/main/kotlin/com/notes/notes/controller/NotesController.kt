package com.notes.notes.controller

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*
import org.springframework.http.ResponseEntity

// Import de ton modèle et repo
import com.notes.notes.model.Note
import com.notes.notes.service.NotesService


@RestController
@RequestMapping("/notes")
class NotesController(private val service: NotesService) {

    @GetMapping
    fun getNotes(): List<Note> {
        return service.getAll();
    }

    @PostMapping
    fun setNotes(@RequestBody note: Note): Note? {
        return service.create(note)
    }

    @GetMapping("/{id}")
    fun getNote(@PathVariable id: Long): ResponseEntity<Note> {
        return service.getById(id)?.let {
            ResponseEntity.ok(it)
        } ?: ResponseEntity.notFound().build()
    }

    @DeleteMapping("/{id}")
    fun deleteNote(@PathVariable id: Long): ResponseEntity<Void> {
        return try {
            service.deleteById(id)
            ResponseEntity.noContent().build()
        } catch(e: Exception) {
            ResponseEntity.notFound().build()
        }
    }
}