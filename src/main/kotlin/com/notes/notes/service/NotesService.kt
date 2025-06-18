package com.notes.notes.service

import com.notes.notes.model.Note
import com.notes.notes.repository.NotesRepository
import org.springframework.stereotype.Service

@Service
class NotesService(private val repository: NotesRepository) {

    fun getAll(): List<Note> = repository.findAll()

    fun getById(id: Long): Note? = repository.findById(id).orElse(null)

    fun create(note: Note): Note = repository.save(note)

    fun deleteById(id: Long) = repository.deleteById(id)
}