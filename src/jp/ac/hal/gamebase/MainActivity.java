
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

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }




}
