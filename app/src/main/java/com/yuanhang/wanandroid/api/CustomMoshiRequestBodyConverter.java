package com.yuanhang.wanandroid.api;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonWriter;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import retrofit2.Converter;

/**
 * created by yuanhang on 2022/3/25
 * description:
 */
final class CustomMoshiRequestBodyConverter<T> implements Converter<T, RequestBody> {
    private static final MediaType MEDIA_TYPE = MediaType.get("application/json; charset=UTF-8");

    private final JsonAdapter<T> adapter;

    CustomMoshiRequestBodyConverter(JsonAdapter<T> adapter) {
        this.adapter = adapter;
    }

    @Override
    public RequestBody convert(T value) throws IOException {
        Buffer buffer = new Buffer();
        JsonWriter writer = JsonWriter.of(buffer);
        adapter.toJson(writer, value);
        return RequestBody.create(MEDIA_TYPE, buffer.readByteString());
    }
}

