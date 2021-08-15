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
import com.ece.nsu.spring2021.cse499.arschoolbook.utils.ArUtil;
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

    private void showArModel(HitResult hitResult) {
        // only allow single AR model to show up
        if(mArModelShown) return;

        mArModelShown = true;
        // hide plane indicating dots
        mArFragment.getArSceneView().getPlaneRenderer().setVisible(false);

        String modelUriPath;

        if (mFigureName.equals(getString(R.string.fig_no_1_1))) {
            modelUriPath = ArUtil.VIRUS_MODEL_FILE;
        }

        else if(mFigureName.equals(getString(R.string.fig_no_1_6))) {
            showToast("Sorry, model not available");
            finish();
            return;
        }

        else if(mFigureName.equals(getString(R.string.fig_no_1_7))) {
            showToast("Sorry, model not available");
            finish();
            return;
        }

        else if(mFigureName.equals(getString(R.string.fig_no_2_1a))) {
            modelUriPath = ArUtil.PLANT_CELL_MODEL_FILE;
        }

        else if(mFigureName.equals(getString(R.string.fig_no_2_1b))) {
            modelUriPath = ArUtil.ANIMAL_CELL_MODEL_FILE;
        }

        else if(mFigureName.equals(getString(R.string.fig_no_2_3))) {
            showToast("Sorry, model not available");
            finish();
            return;
        }

        else if(mFigureName.equals(getString(R.string.fig_no_2_6))) {
            modelUriPath = ArUtil.NEURON_MODEL_FILE;
        }

        else if(mFigureName.equals(getString(R.string.fig_no_4_3))) {
            modelUriPath = ArUtil.LUNG_MODEL_FILE;
        }

        else if(mFigureName.equals(getString(R.string.fig_no_5_1))) {
            modelUriPath = ArUtil.DIGESTIVE_MODEL_FILE;
        }

        else if(mFigureName.equals(getString(R.string.fig_no_12_1))) {
            modelUriPath = ArUtil.SOLAR_SYSTEM_MODEL_FILE;
        }

        else {
            Log.d(TAG, "showArModel: invalid figure name");
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