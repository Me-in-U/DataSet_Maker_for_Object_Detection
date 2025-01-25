package org.tensorflow.lite.examples.objectdetection.ui.editPhoto;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0019\u001a\u00020\u00042\u0006\u0010\u001d\u001a\u00020\u0018H\u0002J\u000e\u0010\u001e\u001a\u00020\u00182\u0006\u0010\u001f\u001a\u00020\u0011J\b\u0010 \u001a\u00020\u001cH\u0002J\u0010\u0010!\u001a\u00020\u001c2\u0006\u0010\u0019\u001a\u00020\u0004H\u0002J\"\u0010\"\u001a\u00020\u001c2\u0006\u0010#\u001a\u00020\u00042\u0006\u0010$\u001a\u00020\u00042\b\u0010%\u001a\u0004\u0018\u00010&H\u0015J\u0012\u0010\'\u001a\u00020\u001c2\b\u0010(\u001a\u0004\u0018\u00010)H\u0014J\u0010\u0010*\u001a\u00020\u001c2\u0006\u0010+\u001a\u00020\u0011H\u0002J\u001e\u0010,\u001a\u00020\u001c2\u0006\u0010-\u001a\u00020.2\u0006\u0010\u001f\u001a\u00020\u00112\u0006\u0010/\u001a\u00020\u0018J\b\u00100\u001a\u00020\u001cH\u0002J\b\u00101\u001a\u00020\u001cH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u00062"}, d2 = {"Lorg/tensorflow/lite/examples/objectdetection/ui/editPhoto/MakePhoto;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "REQUEST_IMAGE_CAPTURE", "", "addClassButton", "Landroid/widget/Button;", "checkCordi", "imageView", "Landroid/widget/ImageView;", "isPhotoTaken", "", "newObjectName", "Landroid/widget/EditText;", "rectView", "Lorg/tensorflow/lite/examples/objectdetection/ui/editPhoto/CordinateBox;", "resizedBitmap", "Landroid/graphics/Bitmap;", "spinner", "Landroid/widget/Spinner;", "takePhotoButton", "textViewCoordinates", "Landroid/widget/TextView;", "topic", "", "topicID", "uploadButton", "addClass", "", "classname", "bitmapToBase64", "bitmap", "dispatchTakePictureIntent", "getClasses", "onActivityResult", "requestCode", "resultCode", "data", "Landroid/content/Intent;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "resizeAndCropImage", "original", "saveBitmapToGallery", "context", "Landroid/content/Context;", "fileName", "updataCordinates", "uploadPictureData", "app_debug"})
public final class MakePhoto extends androidx.appcompat.app.AppCompatActivity {
    private final int REQUEST_IMAGE_CAPTURE = 1;
    private android.widget.Spinner spinner;
    private android.widget.ImageView imageView;
    private org.tensorflow.lite.examples.objectdetection.ui.editPhoto.CordinateBox rectView;
    private android.widget.TextView textViewCoordinates;
    private android.graphics.Bitmap resizedBitmap;
    private android.widget.Button checkCordi;
    private android.widget.Button uploadButton;
    private android.widget.Button takePhotoButton;
    private android.widget.EditText newObjectName;
    private android.widget.Button addClassButton;
    private int topicID = -1;
    @org.jetbrains.annotations.NotNull
    private java.lang.String topic = "None";
    private boolean isPhotoTaken = false;
    
    public MakePhoto() {
        super();
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override
    @java.lang.Deprecated
    protected void onActivityResult(int requestCode, int resultCode, @org.jetbrains.annotations.Nullable
    android.content.Intent data) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String bitmapToBase64(@org.jetbrains.annotations.NotNull
    android.graphics.Bitmap bitmap) {
        return null;
    }
    
    private final void uploadPictureData() {
    }
    
    private final void updataCordinates() {
    }
    
    private final void addClass(int topicID, java.lang.String classname) {
    }
    
    private final void getClasses(int topicID) {
    }
    
    private final void dispatchTakePictureIntent() {
    }
    
    private final void resizeAndCropImage(android.graphics.Bitmap original) {
    }
    
    public final void saveBitmapToGallery(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    android.graphics.Bitmap bitmap, @org.jetbrains.annotations.NotNull
    java.lang.String fileName) {
    }
}