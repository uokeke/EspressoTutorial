package com.espressotutorial.ui.list;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.espressotutorial.note.NoteRepository;
import com.espressotutorial.note.R;
import com.espressotutorial.ui.edit.EditActivity;
import com.espressotutorial.utils.Dependency;

import java.util.Collection;


/**
 * Created by Uche on 2016-04-03.
 */
public class ListActivity extends AppCompatActivity implements ListPresenter.View {

    private ListView listView;
    private ArrayAdapter adapter;
    private ListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.note_list_activity);

        Dependency dependency = Dependency.getInstance();
        NoteRepository noteRepository = dependency.get(NoteRepository.class);
        presenter = new ListPresenter(this, noteRepository);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.notes_title);
        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.list_view);
        adapter = new ArrayAdapter<ListItem>(this, android.R.layout.simple_list_item_1) {
            @Override
            public long getItemId(int position) {
                return getItem(position).getId();
            }
        };
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                navigateToNoteEditor(id);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, final long id) {
                deleteNote(id);
                return true;
            }
        });
        View addButton = findViewById(R.id.add_note_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.addNewNote();
            }
        });

        presenter.attach();
    }

    private void deleteNote(final long noteId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ListActivity.this);
        builder.setTitle(getString(R.string.delete_note_title));
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                presenter.deleteNote(noteId);
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }

    @Override
    public void setList(final Collection<ListItem> notes) {
        adapter.clear();
        adapter.addAll(notes);
    }

    @Override
    public void navigateToNoteEditor(long id) {
        Intent intent = EditActivity.getStartIntent(this, id);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detach();
    }
}
