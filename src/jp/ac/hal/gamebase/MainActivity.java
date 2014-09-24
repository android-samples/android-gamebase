
package jp.ac.hal.gamebase;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends Activity implements
        SeekBar.OnSeekBarChangeListener {

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    
    SampleSurfaceView m_view;
    SeekBar[] m_seeks = new SeekBar[4];
    TextView[] m_texts = new TextView[4];
    float[] m_values = new float[4];

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- //
    // メニューUI等
    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- //
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        // return super.onOptionsItemSelected(item);
        int id = item.getItemId();
        switch (id) {
        case R.id.action_settings:
            break;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Surface
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.surfaceView1);
        m_view = new SampleSurfaceView(surfaceView);
        // m_view = new SampleSurfaceView(this);
        // setContentView(m_view);

        // Progress
        m_seeks[0] = (SeekBar) findViewById(R.id.seekBar1);
        m_seeks[1] = (SeekBar) findViewById(R.id.seekBar2);
        m_seeks[2] = (SeekBar) findViewById(R.id.seekBar3);
        m_seeks[3] = (SeekBar) findViewById(R.id.seekBar4);
        m_texts[0] = (TextView) findViewById(R.id.textView1);
        m_texts[1] = (TextView) findViewById(R.id.textView2);
        m_texts[2] = (TextView) findViewById(R.id.textView3);
        m_texts[3] = (TextView) findViewById(R.id.textView4);

        // -- -- 初期値 -- -- //
        ColorController.initialColorValues(m_values);
        
        // 表示
        for (int i = 0; i < 4; i++) {
            m_texts[i].setText(String.format("%.2f", m_values[i]));
        }
        float[] originalValues = m_values.clone();
        m_seeks[0].setProgress((int) (m_values[0] * 100 / 255));
        m_values = originalValues.clone();
        m_seeks[1].setProgress((int) (m_values[1] * 100 / 359));
        m_values = originalValues.clone();
        m_seeks[2].setProgress((int) (m_values[2] * 100));
        m_values = originalValues.clone();
        m_seeks[3].setProgress((int) (m_values[3] * 100));
        m_values = originalValues.clone();

        // 受け渡し
        m_view.onSeekChanged(m_values);

        // イベント
        for (int i = 0; i < 4; i++) {
            m_seeks[i].setOnSeekBarChangeListener(this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onResume() {
        if (m_view != null) {
            m_view.onResume(this);
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        if (m_view != null) {
            m_view.onPause(this);
        }
        super.onPause();
    }
    
	@Override
	protected void onStop() {
		super.onStop();
	}


    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- //
    // SeekBar //
    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- //
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress,
            boolean fromUser) {
        // a
        m_values[0] = m_seeks[0].getProgress() * 255 / 100;
        // h
        m_values[1] = m_seeks[1].getProgress() * 359 / 100;
        // s
        m_values[2] = m_seeks[2].getProgress() / 100.0f;
        // v
        m_values[3] = m_seeks[3].getProgress() / 100.0f;
        // 表示
        for (int i = 0; i < 4; i++) {
            m_texts[i].setText(String.format("%.2f", m_values[i]));
        }
        // 受け渡し
        m_view.onSeekChanged(m_values);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }




}
