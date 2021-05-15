package com.nsu.ece.cse499a.sec18.group01.augmentedimagespractice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentOnAttachListener;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;

import com.google.ar.core.AugmentedImage;
import com.google.ar.core.AugmentedImageDatabase;
import com.google.ar.core.Config;
import com.google.ar.core.Frame;
import com.google.ar.core.Session;
import com.google.ar.core.TrackingState;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.HitTestResult;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.Sceneform;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.BaseArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import java.lang.ref.WeakReference;
import java.util.Collection;

public class MainActivity extends AppCompatActivity implements
        FragmentOnAttachListener, BaseArFragment.OnSessionConfigurationListener {

    private static final String TAG = "MA-debug";

    private ArFragment arFragment;

    private boolean covidImageDetected = false;
    private static final String COVID_IMAGE_AR_TAG = "covid-image";

    private AugmentedImageDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
    public void onAttachFragment(@NonNull @org.jetbrains.annotations.NotNull FragmentManager fragmentManager, @NonNull @org.jetbrains.annotations.NotNull Fragment fragment) {
        if (fragment.getId() == R.id.arFragment) {
            this.arFragment = (ArFragment) fragment;
            this.arFragment.setOnSessionConfigurationListener(this);
        }
    }

    @Override
    public void onSessionConfiguration(Session session, Config config) {

        // Hide plane indicating dots
        this.arFragment.getArSceneView().getPlaneRenderer().setVisible(false);

        // Disable plane detection
        config.setPlaneFindingMode(Config.PlaneFindingMode.DISABLED);

        // Images to be detected by our AR need to be added in AugmentedImageDatabase
        // This is how database is created at runtime
        // You can also prebuild database in you computer and load it directly (see: https://developers.google.com/ar/develop/java/augmented-images/guide#database)
        database = new AugmentedImageDatabase(session);

        Bitmap covidImage = BitmapFactory.decodeResource(getResources(), R.drawable.corona_virus);
        // Every image has to have its own unique String identifier
        database.addImage(COVID_IMAGE_AR_TAG, covidImage);

        config.setAugmentedImageDatabase(database);

        // Check for image detection
        this.arFragment.getArSceneView().getScene().addOnUpdateListener(this::onUpdate);
    }

    // Every time new image is processed by ARCore and ready, this method is called
    public void onUpdate(FrameTime frameTime) {
        // If there are both images already detected, for better CPU usage we do not need scan for them
        if (covidImageDetected)
            return;

        Frame frame = this.arFragment.getArSceneView().getArFrame();
        try {
            // This is collection of all images from our AugmentedImageDatabase that are currently detected by ARCore in this session
            Collection<AugmentedImage> augmentedImageCollection = frame.getUpdatedTrackables(AugmentedImage.class);

            for (AugmentedImage image : augmentedImageCollection) {
                if (image.getTrackingState() == TrackingState.TRACKING) {
                    this.arFragment.getPlaneDiscoveryController().hide();

                    // If covid model haven't been placed yet and detected image has String identifier of "covid-image"
                    // This is also example of model loading and placing at runtime
                    if (!covidImageDetected && image.getName().equals(COVID_IMAGE_AR_TAG)) {
                        covidImageDetected = true;
                        Toast.makeText(this, "Covid image detected", Toast.LENGTH_LONG).show();

                        WeakReference<MainActivity> weakActivity = new WeakReference<>(this);
                        ModelRenderable.builder()
                                .setSource(this, Uri.parse("corona.glb"))
                                .setIsFilamentGltf(true)
                                .build()
                                .thenAccept(covidModel -> {
                                    MainActivity activity = weakActivity.get();
                                    if (activity != null) {

                                        // Setting anchor to the center of AR tag
                                        AnchorNode anchorNode = new AnchorNode(image.createAnchor(image.getCenterPose()));

                                        arFragment.getArSceneView().getScene().addChild(anchorNode);

                                        TransformableNode modelNode = new TransformableNode(arFragment.getTransformationSystem());

                                        // set initial size
                                        modelNode.setLocalScale(new Vector3(0.05f, 0.05f, 0.05f));
                                        // set max, min size
                                        modelNode.getScaleController().setMaxScale(0.10f);
                                        modelNode.getScaleController().setMinScale(0.025f);

                                        modelNode.setParent(anchorNode);
                                        modelNode.setRenderable(covidModel);

                                        // Removing shadows
                                        modelNode.getRenderableInstance().setShadowCaster(true);
                                        modelNode.getRenderableInstance().setShadowReceiver(true);
                                    }
                                })
                                .exceptionally(
                                        throwable -> {
                                            Toast.makeText(this, "Unable to load covid model", Toast.LENGTH_LONG).show();
                                            return null;
                                        });
                    }
                }
            }
        } catch (Exception e) {
            Log.d(TAG, "onUpdate: error-> "+e.getMessage());
        }
    }
}