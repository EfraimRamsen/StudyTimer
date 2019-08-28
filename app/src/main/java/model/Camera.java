package model;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.core.content.FileProvider;

import java.io.File;

import activity.MainActivity;

import static activity.MainActivity.CAMERA_REQUEST_CODE;

public class Camera {

	private File mCameraFile;
	public final static String FILE="com.example.studytimer.FILE";
	private MainActivity mMainActivity;
	public Camera(MainActivity mainActivity){
		mMainActivity = mainActivity;

	}

	public File getCameraFile(){
		return mCameraFile;
	}

	public void setCameraFile(File cameraFile){
		mCameraFile = cameraFile;
	}

	public void setFileDir(){
		File dir = mMainActivity.getFilesDir();
		mCameraFile = new File(dir, "mypic.jpg");
	}

	public void	startCamera() {
		// Skapa ett intent för att ta en bild
		Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		/* Bestäm vart filen ska sparas
		 * Observera att Kamera-appen måste kunna skriva till platsen
		 * där filen finns
		 */
		Uri uri;
		if (Build.VERSION.SDK_INT > 21) {
			//Om nog sen version spara filen i vår local
			//storage med en content-url detta är säkrare än den gamla metoden med
			// file-url:er. Dels låter de oss spara filen var som helst utan att behöva
			// bry os om att kameraappen har rättighet at skriva dit. Dessutom har vi
			// om vi sparar filen på ett ställe vi har koll på kontroll över att ingen
			// annan app sabbar något

//			File dir = mMainActivity.getFilesDir();
//			mCameraFile = new File(dir, "mypic.jpg");
			setFileDir();

			uri = FileProvider.getUriForFile(mMainActivity.getApplicationContext(),
					mMainActivity.getPackageName() + ".fileprovider", mCameraFile);
			i.putExtra(MediaStore.EXTRA_OUTPUT, uri);
			i.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
		} else {
			//Annars spara till Pictures-katalogen i external storage mha en file-url
			//Anlernativ finns men är lite bökiga
			//Man kan ev i daxläget fundera på att höja minSDKVersion för att undvika detta
			File dir = mMainActivity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
			mCameraFile = new File(dir, "mypic.jpg");
			uri = Uri.fromFile(mCameraFile);
			i.putExtra(MediaStore.EXTRA_OUTPUT, uri);

		}
		//Starta aktiviteten
		mMainActivity.startActivityForResult(
				i, CAMERA_REQUEST_CODE);
	}

	public void updateImageViewFromFile() {

		// Get the dimensions of the View
		int targetW = mMainActivity.getImageView().getWidth();
		int targetH = mMainActivity.getImageView().getHeight();

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

		Bitmap bitmap = BitmapFactory.decodeFile(mCameraFile.getAbsolutePath(), bmOptions);
		mMainActivity.getImageView().setImageBitmap(bitmap);

	}
}
