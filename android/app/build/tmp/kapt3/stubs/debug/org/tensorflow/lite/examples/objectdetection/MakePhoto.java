package org.tensorflow.lite.examples.objectdetection;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J\b\u0010\u0012\u001a\u00020\u000eH\u0002J\u0010\u0010\u0013\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0004H\u0002J\"\u0010\u0014\u001a\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u00042\u0006\u0010\u0016\u001a\u00020\u00042\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0014J\u0012\u0010\u0019\u001a\u00020\u000e2\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0014J\u0010\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001dH\u0002J\b\u0010\u001f\u001a\u00020\u000eH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006 "}, d2 = {"Lorg/tensorflow/lite/examples/objectdetection/MakePhoto;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "REQUEST_IMAGE_CAPTURE", "", "imageView", "Landroid/widget/ImageView;", "rectView", "Lorg/tensorflow/lite/examples/objectdetection/ResizableRectangleImageView;", "spinnershowClasses", "Landroid/widget/Spinner;", "textViewCoordinates", "Landroid/widget/TextView;", "addClass", "", "topicID", "classname", "", "dispatchTakePictureIntent", "getClasses", "onActivityResult", "requestCode", "resultCode", "data", "Landroid/content/Intent;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "resizeAndCropImage", "Landroid/graphics/Bitmap;", "original", "updataCordinates", "app_debug"})
public final class MakePhoto extends androidx.appcompat.app.AppCompatActivity {
    private final int REQUEST_IMAGE_CAPTURE = 1;
    private android.widget.Spinner spinnershowClasses;
    private android.widget.ImageView imageView;
    private org.tensorflow.lite.examples.objectdetection.ResizableRectangleImageView rectView;
    private android.widget.TextView textViewCoordinates;
    
    public MakePhoto() {
        super();
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    private final void updataCordinates() {
    }
    
    private final void addClass(int topicID, java.lang.String classname) {
    }
    
    private final void getClasses(int topicID) {
    }
    
    private final void dispatchTakePictureIntent() {
    }
    
    @java.lang.Override
    protected void onActivityResult(int requestCode, int resultCode, @org.jetbrains.annotations.Nullable
    android.content.Intent data) {
    }
    
    private final android.graphics.Bitmap resizeAndCropImage(android.graphics.Bitmap original) {
        return null;
    }
}