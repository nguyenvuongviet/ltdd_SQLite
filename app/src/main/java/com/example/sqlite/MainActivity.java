package com.example.sqlite;

import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DatabaseHandler databaseHandler;
    ListView listView;
    ArrayList<NotesModel> arrayList;
    NotesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ ListView và gọi adapter
        listView = findViewById(R.id.listView1);
        arrayList = new ArrayList<>();
        adapter = new NotesAdapter(this, R.layout.row_notes, arrayList);
        listView.setAdapter(adapter);
        // Ánh xạ và thiết lập Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Tạo database và lấy dữ liệu
        InitDatabaseSQLite();
        //insertData(); // Thêm dữ liệu vào database
        databaseSQLite();
    }

    private void InitDatabaseSQLite() {
        // Tạo database
        databaseHandler = new DatabaseHandler(this, "notes.sqlite", null, 1);
        databaseHandler.QueryData("CREATE TABLE IF NOT EXISTS Notes(Id INTEGER PRIMARY KEY AUTOINCREMENT, NameNotes VARCHAR(200))");
    }

    private void insertData() {
        // Thêm dữ liệu vào bảng Notes
        databaseHandler.QueryData("INSERT INTO Notes VALUES(null, 'Ví dụ SQLite 1')");
        databaseHandler.QueryData("INSERT INTO Notes VALUES(null, 'Ví dụ SQLite 2')");
        databaseHandler.QueryData("INSERT INTO Notes VALUES(null, 'Ví dụ SQLite 3')");
    }

    private void databaseSQLite() {
        // Lấy dữ liệu từ database
        Cursor cursor = databaseHandler.GetData("SELECT * FROM Notes");
        arrayList.clear(); // Xóa danh sách cũ để tránh trùng lặp

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            arrayList.add(new NotesModel(name, id));
        }

        // Cập nhật giao diện
        adapter.notifyDataSetChanged();
    }
    // Tạo menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Bắt sự kiện khi chọn menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuAddNotes) {
            DialogThem();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Hàm hiển thị Dialog thêm Notes
    private void DialogThem() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_add_note);
        dialog.setTitle("Thêm Notes");

        EditText editText = dialog.findViewById(R.id.editTextName);
        Button buttonAdd = dialog.findViewById(R.id.buttonEdit);
        Button buttonCancel = dialog.findViewById(R.id.buttonHuy);

        buttonAdd.setOnClickListener(view -> {
            String noteName = editText.getText().toString().trim();
            if (!noteName.isEmpty()) {
                databaseHandler.QueryData("INSERT INTO Notes VALUES(null, '" + noteName + "')");
                databaseSQLite(); // Cập nhật danh sách
                Toast.makeText(this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            } else {
                Toast.makeText(this, "Vui lòng nhập tên Notes!", Toast.LENGTH_SHORT).show();
            }
        });

        buttonCancel.setOnClickListener(view -> dialog.dismiss());

        dialog.show();
    }
    public void DialogCapNhatNotes(String name, int id) {
        // Khởi tạo Dialog
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_update_notes);

        // Ánh xạ view
        EditText editText = dialog.findViewById(R.id.editTextName);
        Button buttonEdit = dialog.findViewById(R.id.buttonEdit);
        Button buttonHuy = dialog.findViewById(R.id.buttonHuy);

        // Gán giá trị hiện tại của Notes vào EditText
        editText.setText(name);

        // Bat su kien
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name =editText.getText().toString().trim();
                databaseHandler.QueryData("UPDATE Notes SET NameNotes = '"+ name+"' WHERE Id = '"+id+"'");
                Toast.makeText(MainActivity.this, "Đã cập nhat thành công", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                databaseSQLite();
            }
        });


        // Sự kiện hủy
        buttonHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    public void DialogDelete(String name, final int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Bạn có muốn xóa Notes \"" + name + "\" này không?");

        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                databaseHandler.QueryData("DELETE FROM Notes WHERE Id = " + id);
                Toast.makeText(MainActivity.this, "Đã xóa Notes \"" + name + "\" thành công", Toast.LENGTH_SHORT).show();
                databaseSQLite(); // Gọi hàm load lại dữ liệu
            }
        });

        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); // Đóng hộp thoại khi chọn "Không"
            }
        });

        builder.show();
    }


}
