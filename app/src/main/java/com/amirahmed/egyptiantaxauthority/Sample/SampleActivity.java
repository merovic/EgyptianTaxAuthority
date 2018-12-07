package com.amirahmed.egyptiantaxauthority.Sample;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;

import com.amirahmed.egyptiantaxauthority.R;

import java.util.ArrayList;
import java.util.List;

public class SampleActivity extends AppCompatActivity {

    RecyclerView rv;
    List<MessageItem> messageItemList;

    EditText editText;
    Button button;

    SampleAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        messageItemList = new ArrayList<>();

        rv = findViewById(R.id.rvmessages);

        rv.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(mLayoutManager);


        editText = findViewById(R.id.edit);
        button = findViewById(R.id.button);

        button.setOnClickListener(v -> {

            messageItemList.add(new MessageItem(editText.getText().toString()));
            adapter.notifyDataSetChanged();

            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                messageItemList.add(new MessageItem("اهلا بك يا " + editText.getText().toString()));
                adapter.notifyDataSetChanged();
            }, 2000);

        });

        setList();
        setAdapter();

    }


    private void setList() {

        messageItemList = new ArrayList<>();
        messageItemList.add(new MessageItem("مرحبًا بكِ ، أنا هنا لمساعدتك على تخطي أي صعوبات تواجهك في تنفيذ النشاط التعليمي"));
        messageItemList.add(new MessageItem("ما اسمك ؟"));
    }

    private void setAdapter() {

        adapter = new SampleAdapter(messageItemList);
        rv.setAdapter(adapter);

    }
}
