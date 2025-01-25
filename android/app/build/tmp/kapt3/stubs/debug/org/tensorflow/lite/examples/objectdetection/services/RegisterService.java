package org.tensorflow.lite.examples.objectdetection.services;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\'\u00a8\u0006\u0007"}, d2 = {"Lorg/tensorflow/lite/examples/objectdetection/services/RegisterService;", "", "register", "Lretrofit2/Call;", "Lorg/tensorflow/lite/examples/objectdetection/data/NormalResponse;", "credentials", "Lorg/tensorflow/lite/examples/objectdetection/data/LoginCredentials;", "app_debug"})
public abstract interface RegisterService {
    
    @retrofit2.http.POST(value = "register.php")
    @org.jetbrains.annotations.NotNull
    public abstract retrofit2.Call<org.tensorflow.lite.examples.objectdetection.data.NormalResponse> register(@retrofit2.http.Body
    @org.jetbrains.annotations.NotNull
    org.tensorflow.lite.examples.objectdetection.data.LoginCredentials credentials);
}