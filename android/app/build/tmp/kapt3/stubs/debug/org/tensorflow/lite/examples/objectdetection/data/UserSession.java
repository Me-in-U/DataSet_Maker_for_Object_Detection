package org.tensorflow.lite.examples.objectdetection.data;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tJ\u0010\u0010\n\u001a\u0004\u0018\u00010\u00042\u0006\u0010\b\u001a\u00020\tJ\u0016\u0010\u000b\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2 = {"Lorg/tensorflow/lite/examples/objectdetection/data/UserSession;", "", "()V", "PREFS_NAME", "", "USERNAME", "clearUsername", "", "context", "Landroid/content/Context;", "getUsername", "saveUsername", "username", "app_debug"})
public final class UserSession {
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String PREFS_NAME = "UserSessionPrefs";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String USERNAME = "username";
    @org.jetbrains.annotations.NotNull
    public static final org.tensorflow.lite.examples.objectdetection.data.UserSession INSTANCE = null;
    
    private UserSession() {
        super();
    }
    
    public final void saveUsername(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    java.lang.String username) {
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getUsername(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        return null;
    }
    
    public final void clearUsername(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
    }
}