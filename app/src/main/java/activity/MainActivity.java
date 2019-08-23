package activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.studytimer.R;

import model.Camera;
import model.Timer;

public class MainActivity extends AppCompatActivity {

	public static final int CAMERA_REQUEST_CODE = 10;
	private TextView mCountdownText;
	private ImageView mImageView;
	private Button mStartStopButton;
	private Timer mTimer;
	private Context mContext;
	private Camera mCamera;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toolbar myToolbar = findViewById(R.id.toolbar);
		setSupportActionBar(myToolbar);

		mCountdownText = findViewById(R.id.text_countdown);
		mStartStopButton = findViewById(R.id.button_start);
		Button resetButton = findViewById(R.id.button_reset);
		Button skipButton = findViewById(R.id.button_skip);

		mImageView = (ImageView) findViewById(R.id.photo_view);

		mContext = this.getApplicationContext();

		//TODO Glöm inte fixa onSaveInstancestate etc.
		mTimer = new Timer(this);
		mCamera = new Camera(this);



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
				//start camera
				new Camera(this).startCamera();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	@Override //TODO Jobba med att skriva bilden som kommer till enhetens minne istället. Kapitel 16
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode,data);

		if(resultCode == RESULT_OK && requestCode == CAMERA_REQUEST_CODE){

			Uri uri = FileProvider.getUriForFile(this,
					"com.example.studytimer.fileprovider",
					mCamera.getMPhotoFile());

			this.revokeUriPermission(uri,Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

			mCamera.updatePhotoView();

			//Result from camera
//			Bitmap cameraImage = (Bitmap) data.getExtras().get(MediaStore.EXTRA_OUTPUT); // rad 101

//			mImageView.setVisibility(View.INVISIBLE);//TODO FUNKAR


		}
	}


	public void setCountdownText(String countdownText) {
		mCountdownText.setText(countdownText);

	}

	public void setStartStopButtonText(int buttonTextId){
		mStartStopButton.setText(buttonTextId);
	}

	public void setImageView(Bitmap imageView) {
		mImageView.setImageBitmap(imageView);
	}
}
