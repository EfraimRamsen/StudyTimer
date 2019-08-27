package activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
		savedInstanceState.putString("currentPhotoPath",currentPhotoPath);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if(savedInstanceState != null){
			currentPhotoPath = savedInstanceState.getString("currentPhotoPath");
		}

		setContentView(R.layout.activity_main);
		Toolbar myToolbar = findViewById(R.id.toolbar);
		setSupportActionBar(myToolbar);

		mCountdownText = findViewById(R.id.text_countdown);
		mStartStopButton = findViewById(R.id.button_start);
		Button resetButton = findViewById(R.id.button_reset);
		Button skipButton = findViewById(R.id.button_skip);

		mImageView = findViewById(R.id.photo_view);

		mContext = this.getApplicationContext();

		//TODO Glöm inte fixa onSaveInstancestate etc.
		mTimer = new Timer(this);
//		mCamera = new Camera(this);



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
//				new Camera(this).startCamera();
				dispatchTakePictureIntent();
				setPic();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	//TODO Gör om till egna klasser nedan
	private void dispatchTakePictureIntent() {
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// Ensure that there's a camera activity to handle the intent
		if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
			// Create the File where the photo should go
			File photoFile = null;
			try {
				photoFile = createImageFile();
			} catch (IOException ex) {
				// Error occurred while creating the File
				System.out.println("ERROR creating the file in dispatchTakePictureIntent()");
			}
			// Continue only if the File was successfully created
			if (photoFile != null) {
				Uri photoURI = FileProvider.getUriForFile(this,
						"com.example.android.fileprovider",
						photoFile);
				takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
				startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
			}
		}
	}


	String currentPhotoPath;

	private File createImageFile() throws IOException {
		// Create an image file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String imageFileName = "JPEG_" + timeStamp + "_";
		File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
		File image = File.createTempFile(
				imageFileName,  /* prefix */
				".jpg",         /* suffix */
				storageDir      /* directory */
		);

		// Save a file: path for use with ACTION_VIEW intents
		currentPhotoPath = image.getAbsolutePath();
		return image;
	}

	private void setPic() {
		// Get the dimensions of the View
		int targetW = mImageView.getWidth();
		int targetH = mImageView.getHeight();

		// Get the dimensions of the bitmap
		BitmapFactory.Options bmOptions = new BitmapFactory.Options();
		bmOptions.inJustDecodeBounds = true;

		int photoW = bmOptions.outWidth;
		int photoH = bmOptions.outHeight;

		// Determine how much to scale down the image
		int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

		// Decode the image file into a Bitmap sized to fill the View
		bmOptions.inJustDecodeBounds = false;
		bmOptions.inSampleSize = scaleFactor;
		bmOptions.inPurgeable = true;

		Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath/*, bmOptions*/);
		mImageView.setImageBitmap(bitmap);
	}


	//TODO *** Kamerakod slut ***


	public void setCountdownText(String countdownText) {
		mCountdownText.setText(countdownText);

	}

	public void setStartStopButtonText(int buttonTextId){
		mStartStopButton.setText(buttonTextId);
	}
//
//	public void setImageView(Bitmap imageView) {
//		mImageView.setImageBitmap(imageView);
//	}
}
