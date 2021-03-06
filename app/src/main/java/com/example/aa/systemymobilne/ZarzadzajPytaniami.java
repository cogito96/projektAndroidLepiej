package com.example.aa.systemymobilne;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.aa.systemymobilne.database.PytaniaDbAdapter;
import com.example.aa.systemymobilne.model.PytaniaGra;

import java.util.ArrayList;
import java.util.List;

public class ZarzadzajPytaniami extends AppCompatActivity {

    RadioGroup odpowiedzRadioGrup;
    RadioButton odpowiedzRadioButton;

    RadioGroup kategoriaRadioGrup;
    RadioButton kategoriaRadioButton;

    private Button btnAddNew;
    private Button btnClearCompleted;
    private Button btnSave;
    private Button btnCancel;
    private EditText etNewTask , etNowaKategoria;
    private ListView lvTodos;

    private LinearLayout llControlButtons;
    private LinearLayout llNewTaskButtons;

    private PytaniaDbAdapter todoDbAdapter;
    private Cursor todoCursor;
    private List<PytaniaGra> tasks;
    private PytaniaAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zarzadzaj_pytaniami);

        initUiElements();
        initListView();
        initButtonsOnClickListeners();
    }

    private void initUiElements() {
        btnAddNew = (Button) findViewById(R.id.btnAddNew);
        btnClearCompleted = (Button) findViewById(R.id.btnClearCompleted);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        etNewTask = (EditText) findViewById(R.id.etNewTask);

        odpowiedzRadioGrup = (RadioGroup) findViewById(R.id.radioGroup);
        kategoriaRadioGrup = (RadioGroup) findViewById(R.id.kategoriaRadioGrup);

        lvTodos = (ListView) findViewById(R.id.lvTodos);
        llControlButtons = (LinearLayout) findViewById(R.id.llControlButtons);
        llNewTaskButtons = (LinearLayout) findViewById(R.id.llNewTaskButtons);
    }

    private void initListView() {
        fillListViewData();
        initListViewOnItemClick();
    }

    private void fillListViewData() {
        todoDbAdapter = new PytaniaDbAdapter(getApplicationContext());
        todoDbAdapter.open();
        getAllTasks();
        listAdapter = new PytaniaAdapter(this, tasks);
        lvTodos.setAdapter(listAdapter);
    }

    private void getAllTasks() {
        tasks = new ArrayList<PytaniaGra>();
        todoCursor = getAllEntriesFromDb();
        updateTaskList();
    }

    private Cursor getAllEntriesFromDb() {
        todoCursor = todoDbAdapter.getAllTodos();
        if(todoCursor != null) {
            startManagingCursor(todoCursor);
            todoCursor.moveToFirst();
        }
        return todoCursor;
    }

    private void updateTaskList() {
        if(todoCursor != null && todoCursor.moveToFirst()) {
            do {
                long id = todoCursor.getLong(PytaniaDbAdapter.ID_COLUMN);
                String description = todoCursor.getString(PytaniaDbAdapter.PYTANIE_KOLUMNA);
                String completed = todoCursor.getString(PytaniaDbAdapter.ODPOWIEDZ_KOLUMNA);
                String kategoria = todoCursor.getString(PytaniaDbAdapter.KATEGORIA_KOLUMNA);

                tasks.add(new PytaniaGra(id, description, completed , kategoria));
            } while(todoCursor.moveToNext());
        }
    }

    @Override
    protected void onDestroy() {
        if(todoDbAdapter != null)
            todoDbAdapter.close();
        super.onDestroy();
    }

    private void initListViewOnItemClick() {
        lvTodos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                PytaniaGra task = tasks.get(position);
                todoDbAdapter.deleteTodo(task.getId());
                updateListViewData();
            }
        });
    }

    private void updateListViewData() {
        todoCursor.requery();
        tasks.clear();
        updateTaskList();
        listAdapter.notifyDataSetChanged();
    }

    private void initButtonsOnClickListeners() {
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btnAddNew:
                        addNewTask();
                        break;
                    case R.id.btnSave:
                        saveNewTask();
                        break;
                    case R.id.btnCancel:
                        cancelNewTask();
                        break;
                    case R.id.btnClearCompleted:
                        clearCompletedTasks();
                        break;
                    default:
                        break;
                }
            }
        };
        btnAddNew.setOnClickListener(onClickListener);
        btnClearCompleted.setOnClickListener(onClickListener);
        btnSave.setOnClickListener(onClickListener);
        btnCancel.setOnClickListener(onClickListener);
    }

    private void showOnlyNewTaskPanel() {
        setVisibilityOf(llControlButtons, false);
        setVisibilityOf(llNewTaskButtons, true);
        setVisibilityOf(etNewTask, true);
        setVisibilityOf(odpowiedzRadioGrup, true);
        setVisibilityOf(kategoriaRadioGrup, true);


    }

    private void showOnlyControlPanel() {
        setVisibilityOf(llControlButtons, true);
        setVisibilityOf(llNewTaskButtons, false);
        setVisibilityOf(etNewTask, false);
        setVisibilityOf(odpowiedzRadioGrup, false);
        setVisibilityOf(kategoriaRadioGrup, false);

    }

    private void setVisibilityOf(View v, boolean visible) {
        int visibility = visible ? View.VISIBLE : View.GONE;
        v.setVisibility(visibility);
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etNewTask.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(odpowiedzRadioGrup.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(kategoriaRadioGrup.getWindowToken(), 0);

    }

    private void addNewTask(){
        showOnlyNewTaskPanel();
    }

    private void saveNewTask(){
        String taskDescription = etNewTask.getText().toString();
        String taskOdpowiedz = odpowiedzRadioButton.getText().toString();
        String taskKategoria = kategoriaRadioButton.getText().toString();


        if(taskDescription.equals("") || odpowiedzRadioButton.isChecked()==false){
            etNewTask.setError("Te pole nie może być puste");
        } else {
            todoDbAdapter.insertTodo(taskDescription , taskOdpowiedz , taskKategoria);

            etNewTask.setText("");
            odpowiedzRadioButton.setChecked(false);
            kategoriaRadioButton.setChecked(false);

            hideKeyboard();
            showOnlyControlPanel();
        }
        updateListViewData();
    }

    private void cancelNewTask() {
        etNewTask.setText("");
        odpowiedzRadioButton.setChecked(false);
        kategoriaRadioButton.setChecked(false);
        showOnlyControlPanel();
    }


    private void clearCompletedTasks(){
        if(todoCursor != null && todoCursor.moveToFirst()) {
            do {
                    long id = todoCursor.getLong(PytaniaDbAdapter.ID_COLUMN);
                    todoDbAdapter.deleteTodo(id);

            } while (todoCursor.moveToNext());
        }
        updateListViewData();
    }




    public void przycisnietyRadioGroup (View v)
    {
        int radioButtonID = odpowiedzRadioGrup.getCheckedRadioButtonId();
        odpowiedzRadioButton = (RadioButton) findViewById(radioButtonID);
    }

    public void przycisnietyRadioGroupKategoria(View v)
    {
        int radioButtonID = kategoriaRadioGrup.getCheckedRadioButtonId();
        kategoriaRadioButton = (RadioButton) findViewById(radioButtonID);
    }
    //    private void clearCompletedTasks(){
//        if(todoCursor != null && todoCursor.moveToFirst()) {
//            do {
//                if(todoCursor.getInt(PytaniaDbAdapter.ODPOWIEDZ_KOLUMNA) == 1) {
//                    long id = todoCursor.getLong(TodoDbAdapter.ID_COLUMN);
//                    todoDbAdapter.deleteTodo(id);
//                }
//            } while (todoCursor.moveToNext());
//        }
//        updateListViewData();
//    }

}
