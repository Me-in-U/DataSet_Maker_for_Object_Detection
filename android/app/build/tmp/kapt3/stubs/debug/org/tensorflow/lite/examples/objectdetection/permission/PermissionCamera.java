package org.tensorflow.lite.examples.objectdetection.permission;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J;\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u000e\u0010\t\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u000b0\n2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000b\u00a2\u0006\u0002\u0010\u000fJ\u001e\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u000b2\u0006\u0010\u0007\u001a\u00020\b\u00a8\u0006\u0012"}, d2 = {"Lorg/tensorflow/lite/examples/objectdetection/permission/PermissionCamera;", "", "()V", "handleRequestPermissionResult", "", "activity", "Landroid/app/Activity;", "requestCode", "", "permissions", "", "", "grantResults", "", "requiredPermission", "(Landroid/app/Activity;I[Ljava/lang/String;[ILjava/lang/String;)V", "requestPermission", "permission", "app_debug"})
public final class PermissionCamera {
    @org.jetbrains.annotations.NotNull
    public static final org.tensorflow.lite.examples.objectdetection.permission.PermissionCamera INSTANCE = null;
    
    private PermissionCamera() {
        super();
    }
    
    public final void requestPermission(@org.jetbrains.annotations.NotNull
    android.app.Activity activity, @org.jetbrains.annotations.NotNull
    java.lang.String permission, int requestCode) {
    }
    
    public final void handleRequestPermissionResult(@org.jetbrains.annotations.NotNull
    android.app.Activity activity, int requestCode, @org.jetbrains.annotations.NotNull
    java.lang.String[] permissions, @org.jetbrains.annotations.NotNull
    int[] grantResults, @org.jetbrains.annotations.NotNull
    java.lang.String requiredPermission) {
    }
}