package model;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ImageView;

import androidx.core.content.FileProvider;

import java.io.File;
import java.util.List;
import java.util.UUID;

import activity.MainActivity;

import static activity.MainActivity.CAMERA_REQUEST_CODE;

public class Camera {

	private MainActivity mMainActivity;
	private File mPhotoFile;

	public Camera(MainActivity mainActivity){
		mMainActivity = mainActivity;
		mPhotoFile = this.getPhotoFile(mMainActivity);
	}

	public UUID getId(){
		UUID iD = null;
		return iD.randomUUID();
	}

	public String getPhotoFilename(){
		return "IMG_" + getId().toString() + ".jpg";
	}

	public File getPhotoFile(MainActivity mainActivity){
		File filesDir = mainActivity.getFilesDir();
		return new File(filesDir,getPhotoFilename());
	}

	public void startCamera(){
		final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		Uri uri = FileProvider.getUriForFile(mMainActivity,
				"com.example.studytimer.fileprovider",
				mPhotoFile);
		captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);

		List<ResolveInfo> cameraActivities = mMainActivity
				.getPackageManager().queryIntentActivities(captureImage,
						PackageManager.MATCH_DEFAULT_ONLY);

		for (ResolveInfo activity : cameraActivities) {
			mMainActivity.grantUriPermission(activity.activityInfo.packageName,
					uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
		}

		mMainActivity.startActivityForResult(captureImage, CAMERA_REQUEST_CODE);
	}

	public void updatePhotoView(){
		if(mPhotoFile == null || !mPhotoFile.exists()){
			mMainActivity.setImageView(null);
		}
		else{
			Bitmap bitmap = PictureUtils.getScaledBitmap(
					mPhotoFile.getPath(), mMainActivity);
			mMainActivity.setImageView(bitmap);

		}
	}

	public File getMPhotoFile() {
		return mPhotoFile;
	}
}
