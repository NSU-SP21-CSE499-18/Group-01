package com.ece.nsu.spring2021.cse499.arschoolbook.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentOnAttachListener;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.ece.nsu.spring2021.cse499.arschoolbook.R;
import com.ece.nsu.spring2021.cse499.arschoolbook.utils.AlertDialogUtil;
import com.ece.nsu.spring2021.cse499.arschoolbook.utils.ResourceFetcherUtil;
import com.google.ar.core.AugmentedImage;
import com.google.ar.core.AugmentedImageDatabase;
import com.google.ar.core.Config;
import com.google.ar.core.Frame;
import com.google.ar.core.Session;
import com.google.ar.core.TrackingState;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.Sceneform;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.BaseArFragment;
import com.ece.nsu.spring2021.cse499.arschoolbook.utils.ArUtil;
import com.google.ar.sceneform.ux.TransformableNode;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

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

        showArImageExplanationDialog();

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

    private void showArImageExplanationDialog() {
        AlertDialogUtil.showAlertDialogWithSingleButtons(this,
                getString(R.string.ar_image_dialog_title),
                getString(R.string.ar_image_dialog_message),
                getString(R.string.ar_dialog_ok), () -> {
                    // do nothing
                });
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
            finishGracefully();
        }
    }

    // Every time new image is processed by ARCore and ready, this method is called
    public void onUpdate(FrameTime frameTime) {

        // If there are both images already detected, for better CPU usage we do not need scan for them
        if (mArImageDetected) return;

        Frame frame = this.mArFragment.getArSceneView().getArFrame();
        try {
            // This is collection of all images from our AugmentedImageDatabase
            // that are currently detected by ARCore in this session
            Collection<AugmentedImage> augmentedImageCollection = frame.getUpdatedTrackables(AugmentedImage.class);

            for (AugmentedImage image : augmentedImageCollection) {

                Log.d(TAG, "onUpdate: image detected?");

                if (image.getTrackingState() == TrackingState.TRACKING) {

                    Log.d(TAG,
                            "onUpdate: image index = "+image.getIndex()+" image name = "+image.getName()
                    );

                    // hide the ar-camera user prompt
                    mArFragment.getPlaneDiscoveryController().hide();

                    // detect only one Augemented Image at a time for better performance
                    if(mArImageDetected) break;

                    mArImageDetected = true;

                    // get the 3d model's .glb file uri from assets
                    String modelUri = ResourceFetcherUtil.getModelAssetsFileForDetectedImageIndex(image.getIndex());
                    Log.d(TAG, "onUpdate: modelUri = "+modelUri);
                    if(modelUri==null || modelUri.isEmpty()) {
                        // 3d model not found for detected image
                        showToast("Sorry, no 3D view to show for detected image");
                        mArImageDetected = false;
                        return;
                    }

                    // init variables for showing AR model
                    WeakReference<AugmentedImagesActivity> weakActivity = new WeakReference<>(this);
                    CompletableFuture<ModelRenderable> modelRenderableCompletableFuture = ModelRenderable.builder()
                            .setSource(this, Uri.parse(modelUri))
                            .setIsFilamentGltf(true)
                            .build();

                    modelRenderableCompletableFuture.thenAccept(model -> {
                        AugmentedImagesActivity activity = weakActivity.get();
                        if (activity != null) {

                            // Setting anchor to the center of AR tag
                            AnchorNode anchorNode = new AnchorNode(image.createAnchor(image.getCenterPose()));

                            mArFragment.getArSceneView().getScene().addChild(anchorNode);

                            TransformableNode modelNode = new TransformableNode(mArFragment.getTransformationSystem());

                            // modelNode.setOnTapListener((hitTestResult, motionEvent) -> AugmentedImagesActivity.this.onCovidModelTap(modelNode));

                            modelNode.select();

                            // set initial size
                            modelNode.setLocalScale(new Vector3(0.05f, 0.05f, 0.05f));
                            // set max, min size
                            modelNode.getScaleController().setMaxScale(0.10f);
                            modelNode.getScaleController().setMinScale(0.025f);
//                            // rotate model around x-axis (upwards) by 100 degrees
//                            modelNode.setLocalRotation(
//                                    Quaternion.axisAngle(new Vector3(1.0f, 0.0f, 0.0f), -100.0f)
//                            );

                            modelNode.setParent(anchorNode);
                            modelNode.setRenderable(model);

                            if(modelNode.getRenderableInstance() != null){
                                // Removing shadows
                                modelNode.getRenderableInstance().setShadowCaster(true);
                                modelNode.getRenderableInstance().setShadowReceiver(true);
                            }
                        }
                    }).exceptionally(throwable -> {
                        Log.d(TAG, "onUpdate: error while showing model: "+throwable.getMessage());

                        showToast("Sorry, unable to show 3D model, please try again");
                        mArImageDetected = false;
                        return null;
                    });

                }
            }
        } catch (Exception e) {
            Log.d(TAG, "onUpdate: error-> "+e.getMessage());
            mArImageDetected = false;
        }
    }

    /**
     * unexpected error occured finish the activity gracefully
     */
    private void finishGracefully() {
        showToast("An unexpected event occurred, please try again.");
        finish();
    }

    /**
     * show toast
     * @param message string to show in toast
     */
    private void showToast(String message) {
        // TODO: set messages from strings.xml
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}