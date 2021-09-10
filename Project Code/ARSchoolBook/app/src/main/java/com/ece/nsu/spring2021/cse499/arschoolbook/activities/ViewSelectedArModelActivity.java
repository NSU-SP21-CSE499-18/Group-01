package com.ece.nsu.spring2021.cse499.arschoolbook.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentOnAttachListener;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.ece.nsu.spring2021.cse499.arschoolbook.R;
import com.ece.nsu.spring2021.cse499.arschoolbook.utils.AlertDialogUtil;
import com.ece.nsu.spring2021.cse499.arschoolbook.utils.ArUtil;
import com.ece.nsu.spring2021.cse499.arschoolbook.utils.ResourceFetcherUtil;
import com.google.ar.core.HitResult;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.Sceneform;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

/**
 * Activity to show user selected AR model
 */
public class ViewSelectedArModelActivity extends AppCompatActivity implements FragmentOnAttachListener {

    private static final String TAG = "VSAMA-debug";

    // ui
    private ArFragment mArFragment;

    private String mFigureName;
    private boolean mArModelShown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_selected_ar_model);

        init(savedInstanceState);
    }

    private void init(Bundle savedInstanceState){

        showArUiExplanation();

        mFigureName = getIntent().getStringExtra("Figure-Name");
        Log.d(TAG, "init: figure name from intent = "+mFigureName);

        getSupportFragmentManager().addFragmentOnAttachListener((fragmentManager, fragment) -> {
            if (fragment.getId() == R.id.arFragment) {
                mArFragment = (ArFragment) fragment;
                mArFragment.setOnTapArPlaneListener(
                        (hitResult, plane, motionEvent) -> showArModel(hitResult)
                );
            }
        });

        if (savedInstanceState == null) {
            if (Sceneform.isSupported(this)) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.arFragment, ArFragment.class, null)
                        .commit();
            }
            else showToast("Your device does not support AR");
        }
    }

    private void showArUiExplanation() {
        AlertDialogUtil.showAlertDialogWithTwoButtons(this,
                getString(R.string.ar_dialog_title),
                getString(R.string.ar_dialog_message),
                getString(R.string.ar_dialog_ok), () -> {
                    // do nothing
                },
                getString(R.string.ar_dialog_show3d_instead), ViewSelectedArModelActivity.this::showIn3dViewInstead);
    }

    private void showIn3dViewInstead(){

        String modelUrl = ResourceFetcherUtil.getModelUrlBasedOnFigureName(mFigureName, this);

        if(modelUrl == null || modelUrl.isEmpty()){
            showToast("Sorry, model not available");
            finish();
            return;
        }

        Intent sceneViewerIntent = new Intent(Intent.ACTION_VIEW);
        Uri intentUri =
                Uri.parse("https://arvr.google.com/scene-viewer/1.0").buildUpon()
                        .appendQueryParameter("file", modelUrl)
                        .appendQueryParameter("mode", "3d_only")
                        .build();
        sceneViewerIntent.setData(intentUri);
        sceneViewerIntent.setPackage("com.google.ar.core");
        startActivity(sceneViewerIntent);

        // don't show this activity on back button press
        finish();
    }

    private void showArModel(HitResult hitResult) {
        // only allow single AR model to show up
        if(mArModelShown) return;

        mArModelShown = true;
        // hide plane indicating dots
        mArFragment.getArSceneView().getPlaneRenderer().setVisible(false);

        String modelUriPath = ResourceFetcherUtil.getModelUriFromFigureName(mFigureName, this);

        if(modelUriPath==null || modelUriPath.isEmpty()){
            showToast("Sorry, model not available");
            finish();
            return;
        }

        Log.d(TAG, "showArModel: showing model = "+modelUriPath);

        ModelRenderable.builder()
                .setSource(this, Uri.parse(modelUriPath))
                .setIsFilamentGltf(true)
                .build()
                .thenAccept(model -> {
                    // setting anchorNode
                    AnchorNode anchorNode = new AnchorNode(hitResult.createAnchor());
                    mArFragment.getArSceneView().getScene().addChild(anchorNode);

                    TransformableNode modelNode = new TransformableNode(mArFragment.getTransformationSystem());

                    modelNode.select();

                    // set initial size
                    modelNode.setLocalScale(new Vector3(0.05f, 0.05f, 0.05f));
                    // set max, min size
                    modelNode.getScaleController().setMaxScale(0.10f);
                    modelNode.getScaleController().setMinScale(0.025f);

                    modelNode.setParent(anchorNode);
                    modelNode.setRenderable(model);

                    if(modelNode.getRenderableInstance() != null){
                        // removing shadows
                        modelNode.getRenderableInstance().setShadowCaster(true);
                        modelNode.getRenderableInstance().setShadowReceiver(true);
                    }
                })
                .exceptionally(throwable -> {
                    Log.d(TAG, "showArModel: error while showing model:"+throwable.getMessage());

                    showToast("Failed to load AR model, please try again");
                    finish();

                    return null;
                });
    }

    @Override
    public void onAttachFragment(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment) {
        if (fragment.getId() == R.id.arFragment) {
            mArFragment = (ArFragment) fragment;
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}