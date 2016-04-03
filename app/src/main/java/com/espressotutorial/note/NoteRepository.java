package com.espressotutorial.note;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Uche on 2016-04-03.
 */
public class NoteRepository {

    public interface NoteRepositoryListener {
        void onNotesChanged();
    }

    private Map<Long, Note> allNotes;
    private ConcurrentLinkedQueue<NoteRepositoryListener> listeners;
    private AtomicLong idGenerator;

    public NoteRepository() {
        allNotes = new HashMap<>();
        listeners = new ConcurrentLinkedQueue<>();
        idGenerator = new AtomicLong();
    }

    public void addListener(NoteRepositoryListener listener) {
        listeners.add(listener);
    }

    public void removeListener(NoteRepositoryListener listener) {
        listeners.remove(listener);
    }

    public Collection<Note> getAll() {
        return allNotes.values();
    }

    public Note get(long noteId) {
        return allNotes.get(noteId);
    }

    public void save(Note note) {
        if (note.getId() == 0)
            note.setId(idGenerator.incrementAndGet());

        allNotes.put(note.getId(), note);

        for (NoteRepositoryListener listener : listeners) {
            listener.onNotesChanged();
        }
    }

    public void delete(long noteId) {
        allNotes.remove(noteId);

        for (NoteRepositoryListener listener : listeners) {
            listener.onNotesChanged();
        }
    }
}
