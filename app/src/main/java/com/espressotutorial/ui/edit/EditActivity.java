package com.espressotutorial.ui.edit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.espressotutorial.note.NoteRepository;
import com.espressotutorial.note.R;
import com.espressotutorial.utils.Dependency;


/**
 * Created by Uche on 2016-04-03.
 */
public class EditActivity extends AppCompatActivity implements EditPresenter.View {

    private static final String NOTE_ID_EXTRA = "noteId";
    private EditPresenter presenter;
    private EditText editText;


    public static Intent getStartIntent(Context context, long noteId) {
        return new Intent(context, EditActivity.class).putExtra(NOTE_ID_EXTRA, noteId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Dependency dependency = Dependency.getInstance();
        NoteRepository noteRepository = dependency.get(NoteRepository.class);
        presenter = new EditPresenter(this, noteRepository);

        long noteId = getIntent().getLongExtra(NOTE_ID_EXTRA, 0);

        setContentView(R.layout.note_edit_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.note_edit_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editText = (EditText) findViewById(R.id.edit_text_view);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                presenter.setNoteText(s.toString());
            }
        });
        Button doneButton = (Button) findViewById(R.id.done_button);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        presenter.attach(noteId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detach();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setNoteText(final String text) {
        editText.setText(text);
    }

    @Override
    public void showNoteNotFound() {
        Toast.makeText(this, getString(R.string.note_edit_error), Toast.LENGTH_LONG).show();
        finish();
    }
}
