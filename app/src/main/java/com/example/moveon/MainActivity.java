package com.example.moveon;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ImageView bgImage;
    private Button btnStart;
    private TextView tvFrase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = findViewById(R.id.btnStart);
        tvFrase = findViewById(R.id.id_iniText);

        // Animação sutil contínua no botão (efeito de respiração)
        animateButton();

        // Animação de zoom in/out na frase motivacional
        animateText();

        // Clique no botão com feedback visual
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Animação de feedback ao toque
                Animation clickAnim = new AlphaAnimation(1.0f, 0.5f);
                clickAnim.setDuration(150);
                clickAnim.setRepeatMode(Animation.REVERSE);
                clickAnim.setRepeatCount(1);
                btnStart.startAnimation(clickAnim);

                Toast.makeText(MainActivity.this, "Iniciar clicado!", Toast.LENGTH_SHORT).show();
                // startActivity(new Intent(MainActivity.this, PerfilActivity.class));
            }
        });
    }

    private void animateButton() {
        ScaleAnimation scaleAnim = new ScaleAnimation(
                1.0f, 1.05f,
                1.0f, 1.05f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        scaleAnim.setDuration(1500);
        scaleAnim.setRepeatCount(Animation.INFINITE);
        scaleAnim.setRepeatMode(Animation.REVERSE);

        btnStart.startAnimation(scaleAnim);
    }

    private void animateText() {
        ScaleAnimation textAnim = new ScaleAnimation(
                1.0f, 1.05f,
                1.0f, 1.05f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        textAnim.setDuration(2000);
        textAnim.setRepeatCount(Animation.INFINITE);
        textAnim.setRepeatMode(Animation.REVERSE);

        tvFrase.startAnimation(textAnim);
    }
}
