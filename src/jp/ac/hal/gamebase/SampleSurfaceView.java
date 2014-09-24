package jp.ac.hal.gamebase;

import java.util.ArrayList;

import java.util.List;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

import jp.ac.hal.gamebase.R;
import jp.ac.hal.gamebase.R.drawable;
import jp.ac.hal.gamebase.R.raw;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Toast;
import android.graphics.PorterDuff;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;

public class SampleSurfaceView implements
		SurfaceHolder.Callback, Runnable, OnTouchListener, SensorEventListener {

	private SurfaceHolder m_holder = null;
	private Thread m_thread = null;
	private boolean m_isAttached = true;
	public int m_width = 0;
	public int m_height = 0;
	private long t1, t2;
	public SoundPool m_soundPool;
	public int[] m_soundIds = new int[11];
	
	private Bitmap m_bgImage;
	private float[] m_values = new float[4];

	private SensorManager m_sensorManager;
	
	public SampleSurfaceView(SurfaceView view) {
		SurfaceHolder holder = view.getHolder();
		holder.addCallback(this);
		// 画像
	    Resources res = view.getContext().getResources();
	    m_bgImage = BitmapFactory.decodeResource(res, R.drawable.bg1);
	    // イベント
	    view.setOnTouchListener(this);
		// 加速度
		m_sensorManager = (SensorManager)view.getContext().getSystemService(Context.SENSOR_SERVICE);
	}
	
	public void onSeekChanged(float[] values){
		m_values = values;
	}
	
	public void onResume(Activity activity){
		// Sound
		m_soundPool = new SoundPool(8, AudioManager.STREAM_MUSIC, 0);
		m_soundIds[0] = m_soundPool.load(activity, R.raw.b10, 0);
		m_soundIds[1] = m_soundPool.load(activity, R.raw.b1, 0);
		m_soundIds[2] = m_soundPool.load(activity, R.raw.b2, 0);
		m_soundIds[3] = m_soundPool.load(activity, R.raw.b3, 0);
		m_soundIds[4] = m_soundPool.load(activity, R.raw.b4, 0);
		m_soundIds[5] = m_soundPool.load(activity, R.raw.b5, 0);
		m_soundIds[6] = m_soundPool.load(activity, R.raw.b6, 0);
		m_soundIds[7] = m_soundPool.load(activity, R.raw.b7, 0);
		m_soundIds[8] = m_soundPool.load(activity, R.raw.b8, 0);
		m_soundIds[9] = m_soundPool.load(activity, R.raw.b9, 0);
		// Listenerの登録
		List<Sensor> sensors = m_sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
		if(sensors.size() > 0){
			Sensor s = sensors.get(0);
			m_sensorManager.registerListener(this, s, SensorManager.SENSOR_DELAY_UI);
		}
	}
	public void onPause(Activity activity){
		m_soundPool.release();
		// Listenerの登録解除
		m_sensorManager.unregisterListener(this);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			break;
		default:
			break;
		}
		return true;
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		this.m_holder = holder;
		m_thread = new Thread(this);
		m_thread.start();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		m_width = width;
		m_height = height;
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		m_isAttached = false;
		m_thread = null;
	}

	
	// 初期化
	private void gameInit(){
		// 新しい種類のボール
	}

	class Ball{
		// ボール座標
		private float mX;
		private float mY;
		// 速度
		private float mMx = 8;
		private float mMy = 4;
		//
		public Ball(){
			// 位置と速度をランダムに散らばせる
			Random r = new Random();
			mX = 10 + r.nextInt(100);
			mY = 10 + r.nextInt(100);
			mMx = -2 + r.nextInt(5);
			mMy = -2 + r.nextInt(5);
		}
		public void onFrame(){
			// 跳ね返り処理
			if(mX >= m_width || mX <= 0){
				mMx *= -0.8;
				mX += mMx;
			}
			if(mY >= m_height || mY <= 0){
				mMy *= -0.8;
				mY += mMy;
			}
			// 重力処理
			mMy += 0.8;
			// 移動
			mX += mMx;
			mY += mMy;
		}
		public void draw(Canvas canvas){
			Paint paint = new Paint();
			paint.setColor(Color.rgb(255, 255, 0));
			canvas.drawCircle(
				mX,
				(float)mY,
				(float)20, // 半径
				paint);
		}
	}
	Ball[] balls ={
		new Ball(),
		new Ball(),
		new Ball(),
		new Ball(),
		new Ball()
	};
	
	// フレーム処理
	private void gameFrame(){
		// オブジェクト計算を行う
		// （位置・速度・ダメージ計算等等）
		for(Ball ball : balls){
			ball.onFrame();
		}
	}
	
	private void gameRender(Canvas canvas){
		// 背景
		canvas.drawColor(0, PorterDuff.Mode.CLEAR);
		Rect src = new Rect(0, 0, 2000, 900);
		Rect dst = new Rect(0, 0, 1000, 800);
		canvas.drawBitmap(m_bgImage, src, dst, null);
		// 新ボール描画
		for(Ball ball : balls){
			ball.draw(canvas);
		}
	}
	
	@Override
	public void run() {
		// 初期化
		gameInit();
		
		// メインループ
		while (m_isAttached) {
			t1 = System.currentTimeMillis();
			// フレーム処理
			gameFrame();

			// 描画処理
			Canvas canvas = m_holder.lockCanvas();
			if(canvas != null){
				gameRender(canvas);
				m_holder.unlockCanvasAndPost(canvas); // 描画確定
			}

			// スリープ
			t2 = System.currentTimeMillis();
			long sleeptime = 33 - (t2 - t1); // 1000ms / 30fps = 33
			if (sleeptime >= 0) {
				try {
					Thread.sleep(sleeptime);
				} catch (InterruptedException ex) {
				}
			}
		}
	}

	// -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- //
	// 加速度
	// -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- //
	@Override
	public void onSensorChanged(SensorEvent event) {
	}

	// 精度が変わったとき
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}

}
