package com.cainiao5.cainiaomusic.ui.play;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

import com.cainiao5.cainiaomusic.R;
import com.cainiao5.cainiaomusic.common.utils.RxBus;
import com.cainiao5.cainiaomusic.model.event.PlayingUpdateEvent;
import com.facebook.common.logging.FLog;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.image.QualityInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.lang.ref.WeakReference;

import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

public class RoundFragment extends Fragment {

    private WeakReference<ObjectAnimator> animatorWeakReference;
    private SimpleDraweeView sdv;
    private long musicId = -1;
    private ObjectAnimator animator;
    private String albumPath;
    private CompositeSubscription allSubscription = new CompositeSubscription();

    public static RoundFragment newInstance(String albumpath) {
        RoundFragment fragment = new RoundFragment();
        Bundle bundle = new Bundle();
        bundle.putString("album", albumpath);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        allSubscription.add(RxBus.getDefault()
                .toObservable(PlayingUpdateEvent.class).subscribe(new Action1<PlayingUpdateEvent>() {
                    @Override
                    public void call(PlayingUpdateEvent event) {
                        onEvent(event);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                    }
                }));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_roundimage, container, false);
        ((ViewGroup) rootView).setAnimationCacheEnabled(false);
        if (getArguments() != null) {
            albumPath = getArguments().getString("album");
        }

        sdv = (SimpleDraweeView) rootView.findViewById(R.id.sdv);


        //初始化圆角圆形参数对象
        RoundingParams rp = new RoundingParams();
        //设置图像是否为圆形
        rp.setRoundAsCircle(true);
        rp.setBorder(Color.BLACK, 6);

        //获取GenericDraweeHierarchy对象
        GenericDraweeHierarchy hierarchy = GenericDraweeHierarchyBuilder.newInstance(getResources())
                .setRoundingParams(rp)
                .setFadeDuration(300)
                .build();


        //设置Hierarchy
        sdv.setHierarchy(hierarchy);

        ControllerListener controllerListener = new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(String id, @Nullable ImageInfo imageInfo, @Nullable Animatable anim) {
                if (imageInfo == null) {
                    return;
                }
                QualityInfo qualityInfo = imageInfo.getQualityInfo();
                FLog.d("Final image received! " +
                                "Size %d x %d",
                        "Quality level %d, good enough: %s, full quality: %s",
                        imageInfo.getWidth(),
                        imageInfo.getHeight(),
                        qualityInfo.getQuality(),
                        qualityInfo.isOfGoodEnoughQuality(),
                        qualityInfo.isOfFullQuality());
            }

            @Override
            public void onIntermediateImageSet(String id, @Nullable ImageInfo imageInfo) {
                //FLog.d("Intermediate image received");
            }

            @Override
            public void onFailure(String id, Throwable throwable) {
                sdv.setImageURI(Uri.parse("res:/" + R.drawable.placeholder_disk_play_song));
            }
        };
        if (albumPath == null) {
            sdv.setImageURI(Uri.parse("res:/" + R.drawable.placeholder_disk_play_song));
        } else {
            try {

                ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(albumPath)).build();

                DraweeController controller = Fresco.newDraweeControllerBuilder()
                        .setOldController(sdv.getController())
                        .setImageRequest(request)
                        .setControllerListener(controllerListener)
                        .build();

                sdv.setController(controller);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        animatorWeakReference = new WeakReference(ObjectAnimator.ofFloat(getView(), "rotation", new float[]{0.0F, 360.0F}));
        animator = animatorWeakReference.get();
        animator.setRepeatCount(Integer.MAX_VALUE);
        animator.setDuration(25000L);
        animator.setInterpolator(new LinearInterpolator());

        if (getView() != null)
            getView().setTag(R.id.tag_animator, this.animator);
    }

    @Override
    public void onResume() {
        super.onResume();

    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //相当于Fragment的onResume

        } else {
            //相当于Fragment的onPause

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (animator != null) {
            animator = null;
        }
        if (!allSubscription.isUnsubscribed()) {
            allSubscription.unsubscribe();
        }
    }

    public void onEvent(PlayingUpdateEvent event) {
        animator = (ObjectAnimator) getView().getTag(R.id.tag_animator);
        boolean isPlaying = event.getIsPlaying();
        if (isPlaying) {
            animator.start();
            Log.e("onEvent: ", "true: ");
        } else {
            Log.e("onEvent: ", "false: ");
            animator.cancel();
            float valueAvatar = (float) animator.getAnimatedValue();
            animator.setFloatValues(valueAvatar, 360f + valueAvatar);
            Log.e("onEvent: ", "valueAvatar: " + valueAvatar);

        }
    }

}