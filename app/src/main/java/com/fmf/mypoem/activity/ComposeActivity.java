package com.fmf.mypoem.activity;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.fmf.mypoem.R;
import com.fmf.mypoem.data.MyPoem;
import com.fmf.mypoem.data.MyPoemDao;
import com.fmf.mypoem.fragment.DatePickerFragment;
import com.fmf.mypoem.model.Poem;
import com.fmf.mypoem.util.DateUtil;

import java.util.Calendar;
import java.util.Date;

public class ComposeActivity extends ActionBarActivity implements DatePickerFragment.OnDateSetListner {
    private EditText etTitle;
    private EditText etSubtitle;
    private EditText etContent;
    private EditText etAuthor;
    private EditText etCreated;
//    private int year;
//    private int month;
//    private int day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_compose);

//        getActionBar().setDisplayHomeAsUpEnabled(true);

        initViews();
    }

    private void initViews() {
        etTitle = (EditText) findViewById(R.id.et_title);
        etSubtitle = (EditText) findViewById(R.id.et_subtitle);
        etContent = (EditText) findViewById(R.id.et_content);
        etAuthor = (EditText) findViewById(R.id.et_author);
        etCreated = (EditText) findViewById(R.id.et_created);


        etCreated.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    showDatePickerDialog();
                }
                return true;
            }
        });

        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        setCreatedDate(year, month, day);

        // 只有获得焦点时，才能触发OnClickListner
//        etCreated.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showDatePickerDialog();
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_compose, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_compose:
                compose();
                break;

            case R.id.btn_craft:
                draft();

            default:
        }
    }

    private void draft() {
        savePoem(MyPoem.Poem.STATUS_DRAFT);
    }

    private void compose() {
        savePoem(MyPoem.Poem.STATUS_FINISHED);
    }


    private void savePoem(String status) {
        String content = etContent.getText().toString().trim();
        if (TextUtils.isEmpty(content)){
            Toast.makeText(this, getString(R.string.tip_no_content), Toast.LENGTH_SHORT).show();
            return ;
        }

        String title = etTitle.getText().toString().trim();
        if (TextUtils.isEmpty(title)){
            title = getString(R.string.tip_default_title);
        }

        String subtitle = etSubtitle.getText().toString().trim();
        String author = etAuthor.getText().toString().trim();
        String created = etCreated.getText().toString().trim();
        String updated = DateUtil.formatDatetimeToSqlite(new Date());

        Poem poem = new Poem();
        poem.setTitle(title);
        poem.setSubtitle(subtitle);
        poem.setContent(content);
        poem.setAuthor(author);
        poem.setCreated(created);
        poem.setUpdated(updated);
        poem.setStatus(status);

        new PoemAsynTask().execute(poem);
    }

    private void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    private void showDatePickerDialog() {
        DialogFragment datePickerFragment = new DatePickerFragment();
//        Bundle args = new Bundle();
//        if (year != 0 && month != 0 && year != 0) {
//            args.putInt(DatePickerFragment.YEAR, year);
//            args.putInt(DatePickerFragment.MONTH, month);
//            args.putInt(DatePickerFragment.DAY, day);
//        }
//        datePickerFragment.setArguments(args);
        datePickerFragment.show(getFragmentManager(), "datePicker");
    }

    public void setCreatedDate(int year, int monthOfYear, int dayOfMonth) {
//        this.year = year;
//        this.month = monthOfYear;
//        this.day = dayOfMonth;
        etCreated.setText(DateUtil.formatDateToMyPome(year, monthOfYear, dayOfMonth));
    }

    @Override
    public void onDateSet(int year, int monthOfYear, int dayOfMonth) {
        setCreatedDate(year, monthOfYear, dayOfMonth);
    }

    public final class PoemAsynTask extends AsyncTask<Poem, Void, String> {
//        private Context context;

        public PoemAsynTask() {

        }
//        public PoemAsynTask(Context context) {
//            this.context = context;
//        }

        @Override
        protected String doInBackground(Poem... poems) {
            String result = "操作失败";
            Poem poem = poems[0];
            if (poem == null) {
                return result;
            }

            MyPoemDao dao = new MyPoemDao(ComposeActivity.this);
            dao.create(poem);

            if (MyPoem.Poem.STATUS_DRAFT.equals(poem.getStatus())) {
                result = "成功保存草稿";
            } else if (MyPoem.Poem.STATUS_FINISHED.equals(poem.getStatus())) {
                result = "成功保存创作";
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(ComposeActivity.this, result, Toast.LENGTH_SHORT).show();
            ComposeActivity.this.finish();
        }
    }
}
