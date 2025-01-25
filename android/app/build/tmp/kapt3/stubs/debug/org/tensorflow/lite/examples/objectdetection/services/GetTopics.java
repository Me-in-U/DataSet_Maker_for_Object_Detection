package org.tensorflow.lite.examples.objectdetection.services;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u001e\u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u00032\b\b\u0001\u0010\u0006\u001a\u00020\u0007H\'\u00a8\u0006\b"}, d2 = {"Lorg/tensorflow/lite/examples/objectdetection/services/GetTopics;", "", "getTopics", "Lretrofit2/Call;", "", "Lorg/tensorflow/lite/examples/objectdetection/data/Item;", "usernameData", "Lorg/tensorflow/lite/examples/objectdetection/data/UsernameData;", "app_debug"})
public abstract interface GetTopics {
    
    @retrofit2.http.POST(value = "getTopics.php")
    @org.jetbrains.annotations.NotNull
    public abstract retrofit2.Call<java.util.List<org.tensorflow.lite.examples.objectdetection.data.Item>> getTopics(@retrofit2.http.Body
    @org.jetbrains.annotations.NotNull
    org.tensorflow.lite.examples.objectdetection.data.UsernameData usernameData);
}