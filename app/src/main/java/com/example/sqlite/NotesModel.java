package com.example.sqlite;

import java.io.Serializable;

public class NotesModel  implements Serializable {
    public NotesModel(String nameNote, int idNote) {
        NameNote = nameNote;
        this.idNote = idNote;
    }

    public void setIdNote(int idNote) {
        this.idNote = idNote;
    }

    public void setNameNote(String nameNote) {
        NameNote = nameNote;
    }

    public int getIdNote() {
        return idNote;
    }

    public String getNameNote() {
        return NameNote;
    }

    private int idNote;
    private String NameNote;
}
