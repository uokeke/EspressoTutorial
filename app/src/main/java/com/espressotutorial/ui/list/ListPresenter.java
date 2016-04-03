package com.espressotutorial.ui.list;

import com.espressotutorial.note.Note;
import com.espressotutorial.note.NoteRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * Created by Uche on 2016-04-03.
 */
class ListPresenter implements NoteRepository.NoteRepositoryListener {


    public interface View {
        void setList(Collection<ListItem> notes);

        void navigateToNoteEditor(long id);
    }

    private View view;
    private NoteRepository noteRepository;

    public ListPresenter(View view, NoteRepository noteRepository) {
        this.view = view;
        this.noteRepository = noteRepository;
    }

    public void attach() {
        view.setList(getListItems(noteRepository.getAll()));
        noteRepository.addListener(this);
    }

    private ListItem getListItem(Note note) {
        ListItem result = new ListItem();
        result.setText(note.getText());
        result.setId(note.getId());
        return result;
    }

    private List<ListItem> getListItems(Collection<Note> notes) {
        List<ListItem> result = new ArrayList<>();
        for (Note note : notes) {
            result.add(getListItem(note));
        }
        return result;
    }

    public void addNewNote() {
        Note note = new Note();
        noteRepository.save(note);
        view.navigateToNoteEditor(note.getId());
    }

    public void deleteNote(long noteId) {
        noteRepository.delete(noteId);
    }

    public void detach() {
        noteRepository.removeListener(this);
    }

    @Override
    public void onNotesChanged() {
        view.setList(getListItems(noteRepository.getAll()));
    }
}
