package com.example.andrew.myfirstmvpunit;

import android.content.Context;

import com.example.andrew.myfirstmvpunit.main.activity.model.MainModel;
import com.example.andrew.myfirstmvpunit.data.DAO;
import com.example.andrew.myfirstmvpunit.main.activity.presenter.MainPresenter;
import com.example.andrew.myfirstmvpunit.models.Note;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;

import static junit.framework.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by Andrew on 2018/3/9.
 */

@RunWith(RobolectricGradleTestRunner.class)
//@Config(constants = BuildConfig.class, sdk = 21, manifest = "C:\\Users\\Andrew\\AndroidStudioProjects\\MyFirstMVPUnit\\app\\src\\main\\AndroidManifest.xml")
@Config(constants = BuildConfig.class, sdk = 21, manifest = "/src/main/AndroidManifest.xml")
public class MainModelTest {

    private MainModel mModel;
    private DAO mDAO;

    @Before
    public void setup() {
        Context context = RuntimeEnvironment.application;
        mDAO = new DAO(context);

        MainPresenter mockPresenter = Mockito.mock(MainPresenter.class);
        mModel = new MainModel(mockPresenter, mDAO);
        mModel.mNotes = new ArrayList<>();

        reset(mockPresenter);
    }


    private Note createNote(String text) {
        Note note = new Note();
        note.setText(text);
        note.setDate("some date");
        return note;
    }

    @Test
    public void loadData(){

        int notesSize = 10;
        for (int i =0; i<notesSize; i++){
            mDAO.insertNote(createNote("note_" + Integer.toString(i)));
        }

        mModel.loadData();
        assertEquals(mModel.mNotes.size(), notesSize);

    }

    @Test
    public void insertNote() {
        int pos = mModel.insertNote(createNote("noteText"));
        assertTrue(pos > -1);
    }

    @Test
    public void deleteNote() {
        Note note = createNote("testNote");
        Note insertedNote = mDAO.insertNote(note);
        mModel.mNotes = new ArrayList<>();
        mModel.mNotes.add(insertedNote);

        assertTrue(mModel.deleteNote(insertedNote, 0));

        Note fakeNote = createNote("fakeNote");
        assertFalse(mModel.deleteNote(fakeNote, 0));
    }
}
