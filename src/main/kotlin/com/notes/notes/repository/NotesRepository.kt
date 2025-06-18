package com.notes.notes.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import com.notes.notes.model.Note

@Repository
interface NotesRepository : JpaRepository<Note, Long>
