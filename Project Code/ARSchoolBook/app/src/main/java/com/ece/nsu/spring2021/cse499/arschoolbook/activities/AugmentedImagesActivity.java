package com.ece.nsu.spring2021.cse499.arschoolbook.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentOnAttachListener;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.ece.nsu.spring2021.cse499.arschoolbook.R;
import com.google.ar.core.AugmentedImage;
import com.google.ar.core.AugmentedImageDatabase;
import com.google.ar.core.Config;
import com.google.ar.core.Frame;
import com.google.ar.core.Session;
import com.google.ar.core.TrackingState;
import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.Sceneform;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.BaseArFragment;

import java.io.InputStream;
import java.util.Collection;

public class AugmentedImagesActivity extends AppCompatActivity implements
        FragmentOnAttachListener, BaseArFragment.OnSessionConfigurationListener {

    private static final String TAG = "AIA-debug";

    // ui
    private ArFragment mArFragment;

    // ar
    private AugmentedImageDatabase mAugmentedImageDatabase;
    private boolean mArImageDetected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_augmented_images);

        init(savedInstanceState);
    }

    private void init(Bundle savedInstanceState) {

        getSupportFragmentManager().addFragmentOnAttachListener(this);

        if (savedInstanceState == null) {
            if (Sceneform.isSupported(this)) {
                // setup the ARFragment
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.arFragment, ArFragment.class, null)
                        .commit();
            }
            else Toast.makeText(this, "Your device does not support AR", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAttachFragment(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment) {
        if (fragment.getId() == R.id.arFragment) {
            this.mArFragment = (ArFragment) fragment;
            this.mArFragment.setOnSessionConfigurationListener(this);
        }
    }

    @Override
    public void onSessionConfiguration(Session session, Config config) {

        Log.d(TAG, "onSessionConfiguration: initializing AR settings");
        
        // Hide plane indicating dots
        this.mArFragment.getArSceneView().getPlaneRenderer().setVisible(false);
        // Disable plane detection
        config.setPlaneFindingMode(Config.PlaneFindingMode.DISABLED);

        try {
            // Images to be detected by our AR need to be added in AugmentedImageDatabase
            // create augmented images database from pre-compiled .imgdb file
            InputStream inputStream = getAssets().open("ar_schoolbook_jpgs_imgdb.imgdb");
            mAugmentedImageDatabase = AugmentedImageDatabase.deserialize(session, inputStream);
            config.setAugmentedImageDatabase(mAugmentedImageDatabase);
            // Check for image detection
            this.mArFragment.getArSceneView().getScene().addOnUpdateListener(this::onUpdate);

            Log.d(TAG, "onSessionConfiguration: "+mAugmentedImageDatabase.getNumImages()+" augmented images added");

        } catch (Exception e){
            Log.d(TAG, "onSessionConfiguration: failed to create augmented images database, error:"+e.getMessage());
            finish(); // TODO: handle this gracefully
        }
    }

    // Every time new image is processed by ARCore and ready, this method is called
    public void onUpdate(FrameTime frameTime) {

        // If there are both images already detected, for better CPU usage we do not need scan for them
        if (mArImageDetected) return;

        Frame frame = this.mArFragment.getArSceneView().getArFrame();
        try {
            // This is collection of all images from our AugmentedImageDatabase that are currently detected by ARCore in this session
            Collection<AugmentedImage> augmentedImageCollection = frame.getUpdatedTrackables(AugmentedImage.class);

            for (AugmentedImage image : augmentedImageCollection) {

                Log.d(TAG, "onUpdate: image detected?");

                if (image.getTrackingState() == TrackingState.TRACKING) {
                    mArFragment.getPlaneDiscoveryController().hide();

                    if(mArImageDetected) break;

                    mArImageDetected = true;
                    switch (image.getIndex()){

                        default:
                            Log.d(TAG,
                                    "onUpdate: image index = "+image.getIndex()+" image name = "+image.getName()
                            );
                    }
                }
            }
        } catch (Exception e) {
            Log.d(TAG, "onUpdate: error-> "+e.getMessage());
        }
    }
}