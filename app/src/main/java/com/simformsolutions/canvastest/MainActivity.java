package com.simformsolutions.canvastest;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView tvName;
    AvatarView avAvatar;
    int count = 0;
    String[] names = {"Chintan", "Rajan", "Birju", "Sagar", "Rushabh", "Vatsal"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvName = findViewById(R.id.tvName);
        avAvatar = findViewById(R.id.avatarView2);
        avAvatar.setBackgroundColor(Color.GREEN);
    }

    public void changeText(View view) {
        tvName.setText(names[count++]);
    }

    public void enableListener(View view) {
        avAvatar.enableTextChangeListener();
    }

    public void disableListener(View view) {
        avAvatar.disableTextChangeListener();
    }
}
