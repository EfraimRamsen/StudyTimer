package activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.studytimer.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import model.Camera;
import model.Timer;
import static model.Camera.FILE;

public class MainActivity extends AppCompatActivity {

	public static final int CAMERA_REQUEST_CODE = 10;
	private TextView mCountdownText;
	private ImageView mImageView;
	private Button mStartStopButton;
	private Timer mTimer;
	private Context mContext;
	private Camera mCamera;

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState){
		super.onSaveInstanceState(savedInstanceState);
		savedInstanceState.putSerializable(FILE,mCamera.getCameraFile());

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mTimer = new Timer(this);
		mCamera = new Camera(this);

		if(savedInstanceState != null){
			mCamera.setCameraFile((File)savedInstanceState.getSerializable(FILE)) ;
		}

		setContentView(R.layout.activity_main);
		mImageView = findViewById(R.id.photo_view);

//		if(mCamera.getCameraFile() != null){
//			mCamera.updateImageViewFromFile();
//		}

		Toolbar myToolbar = findViewById(R.id.toolbar);
		setSupportActionBar(myToolbar);

		mCountdownText = findViewById(R.id.text_countdown);
		mStartStopButton = findViewById(R.id.button_start);
		Button resetButton = findViewById(R.id.button_reset);
		Button skipButton = findViewById(R.id.button_skip);


		mContext = this.getApplicationContext();

		//TODO Glöm inte fixa onSaveInstancestate etc.




		mStartStopButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mTimer.pressStartStopButton();
			}
		});

		resetButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mTimer.pressResetButton();
			}
		});

		skipButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mTimer.pressSkipButton();
			}
		});

		mTimer.updateCountdownText();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		getMenuInflater().inflate(R.menu.my_menu,menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()){
			case R.id.button_camera:

				mCamera.startCamera();

				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	public void setCountdownText(String countdownText) {
		mCountdownText.setText(countdownText);

	}

	public void setStartStopButtonText(int buttonTextId){
		mStartStopButton.setText(buttonTextId);
	}

	public ImageView getImageView() {
		return mImageView;
	}

	public void onWindowFocusChanged(boolean hasFocus){
		super.onWindowFocusChanged(hasFocus);
		if(mCamera.getCameraFile() != null && mCamera.getCameraFile().exists() && hasFocus)
			mCamera.updateImageViewFromFile();
			mImageView.setBackgroundResource(android.R.color.transparent);//TODO fixa så den här raden bara körs när ny bild laddas in!
	}

//	protected void onActivityResult(int requestCode,
//	                                int resultCode,
//	                                Intent data) {
//		if (requestCode == CAMERA_REQUEST_CODE) {
//			if (resultCode == RESULT_OK) {
//				//Bilden sparad till den plats vi angav i intentetIntent. Bilden kommer bytas ut
//				//Då aktiviteten blir synlig
//
//
//				/* Hade vi inte angivit platsen så hade vi istället kunnat
//				 * göra som följer. Vi kan dock få en bild av sämre kvalitet
//				 * imView.setImageBitmap((Bitmap) data.getExtras().get("data"));
//				 */
//			} else if (resultCode == RESULT_CANCELED) {
//				// Användaren valde att inte ta en bild
//			} else {
//				mCamera.setCameraFile(null); // Något galet inträffade skippa att byta bilden till en egen
//			}
//		}
//
//	}
}
