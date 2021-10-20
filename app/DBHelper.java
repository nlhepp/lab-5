public class DBHelper{

    SQLiteDatabase sqLiteDatabase;

    public DBHelper(SQLiteDatabase sqLiteDatabase){
        this.sqLiteDatabase = sqLiteDatabase;
    }

    public void createTable(){
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS notes "+
                "(id Integer PRIMARY KEY, username TEXT, date TEXT, title TEXT, content TEXT, src TEXT)");
    }

    public ArrayList<Note> readNotes(String username){
        createTable();
        Cursor c = sqLiteDatabase.rawQuery(String.format("SELECT * from notes where username like '%s'", username), null);

        int dateIndex = c.getColumnIndex("date");
        int titleIndex = c.getColumnIndex("title");
        int contentIndex = c.getColumnIndex("content");

        c.moveToFirst();

        ArrayList<Note> notesList = new ArrayList<>();

        while (!c.isAfterLast()){
            String title = c.getString(titleIndex);
            String date = c.getString(dateIndex);
            String content = c.getString(contentIndex);

            Note note = new Note(date,username, title, content);
            notesList.add(note);
            c.moveToNext();
        }
        c.close();
        sqLiteDatabase.close();
        return notesList;

    }

    public void saveNotes(String username, String title, String content,String date){
        createTable();
        sqLiteDatabase.execSQL(String.format("INSERT INTO notes (username, date, title, content) VALUES ('%s', '%s', '%s', '%s')",
                username, date, title, content));
    }


}