package com.noteIt.notes;

import com.noteIt.data.Note;
import com.noteIt.data.Task;
import com.noteIt.data.local.NoteRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adria on 24.04.2018.
 */

public final class NotePresenter implements NoteContract.Presenter {
    private final int ADD_NOTE = 1;

    private NoteRepository mNoteRepository;

    private NoteContract.View mFragment;

    NotePresenter(NoteContract.View fragment, NoteRepository noteRepository) {
        mFragment = fragment;
        mNoteRepository = noteRepository;

        mFragment.setPresenter(this);
    }

    @Override
    public void refreshNoteList() {
        List<Note> list = mNoteRepository.getNotesList();
        if (list.size() != 0)
        {

            mFragment.updateNoteList(list);
        }

    }

    @Override
    public List<Note> getNotes() {
        return mNoteRepository.getNotesList();
    }

    @Override
    public ArrayList<Task> getNoteTasks(String noteId) {
        return mNoteRepository.getNoteTasks(noteId);
    }

    @Override
    public void deleteNotes(ArrayList<Note> notes) {
        mNoteRepository.deleteNotes(notes);
    }

    @Override
    public void updateNotes(ArrayList<Note> noteList) {
        mNoteRepository.updateNotes(noteList);
    }

    @Override
    public void swapNotesPositions(Note fromNote, Note toNote) {
        int position = fromNote.getPosition();
        fromNote.setPosition(toNote.getPosition());
        toNote.setPosition(position);

        mNoteRepository.updateNote(fromNote);
        mNoteRepository.updateNote(toNote);

    }
}
