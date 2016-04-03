package com.espressotutorial.ui.edit;


import com.espressotutorial.note.Note;
import com.espressotutorial.note.NoteRepository;

/**
 * Created by Uche on 2016-04-03.
 */
class EditPresenter {
    private View view;
    private NoteRepository noteRepository;
    private Note note;

    public interface View {
        void setNoteText(String text);

        void showNoteNotFound();
    }

    public EditPresenter(View view, NoteRepository noteRepository) {
        this.view = view;
        this.noteRepository = noteRepository;
    }

    public void attach(long noteId) {
        note = noteRepository.get(noteId);
        if (note == null)
            view.showNoteNotFound();
        else {
            view.setNoteText(note.getText());
        }
    }

    public void detach() {
        if (note == null)
            return;
        noteRepository.save(note);
    }

    public void setNoteText(String text) {
        note.setText(text);
    }

}
