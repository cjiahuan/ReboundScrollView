package cjh.reboundscrollview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import cjh.reboundscrollviewlibrary.ReboundScrollView;

public class NewReboundActivity extends AppCompatActivity {

    private int[] resId = new int[]{
            R.drawable.a,
            R.drawable.b,
            R.drawable.c,
            R.drawable.d,
            R.drawable.e,
            R.drawable.f,
            R.drawable.g,
            R.drawable.h
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ReboundScrollView reboundScrollView = new ReboundScrollView(this);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        for (int id : resId) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(id);
            linearLayout.addView(imageView);
        }
        reboundScrollView.addView(linearLayout);

        setContentView(reboundScrollView);
        Toast.makeText(this, "new ReboundScrollView", Toast.LENGTH_LONG);
    }
}
