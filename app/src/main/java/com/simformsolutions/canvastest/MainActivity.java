package com.simformsolutions.canvastest;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {
    TextView tvName;
    AvatarView avAvatar;
    int count = 0;
    String[] names = {"Chintan", "Rajan", "Birju", "Sagar", "Rushabh", "Vatsal"};
    String circleAvatar = "https://www.vzones.com/forums/styles/FLATBOOTS/theme/images/user4.png";
    String squareAvatar = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT9KdWjELleM7etPpp1r40_2S-zS4ofcWbKvpd2nBEHEHRzE7jztg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvName = findViewById(R.id.tvName);
        avAvatar = findViewById(R.id.avatarView2);
        Picasso.get().load(squareAvatar).placeholder(R.drawable.ic_launcher_background).fit().into(avAvatar);
    }

    public void changeText(View view) {

    }

    public void enableListener(View view) {
        avAvatar.enableTextChangeListener();
    }

    public void disableListener(View view) {
        avAvatar.disableTextChangeListener();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
