package com.siftscience;

import com.siftscience.model.UnlabelFieldSet;
import com.sun.istack.internal.NotNull;
import okhttp3.*;

import java.io.IOException;

public class UnlabelRequest extends SiftRequest<UnlabelResponse> {

    UnlabelRequest(HttpUrl baseUrl, OkHttpClient okClient, UnlabelFieldSet fields) {
        super(baseUrl, okClient, fields);
    }

    @Override
    protected HttpUrl path(HttpUrl baseUrl) {
        UnlabelFieldSet unlabelFields = (UnlabelFieldSet)fieldSet;
        HttpUrl.Builder builder = baseUrl.newBuilder()
                .addPathSegment("users")
                .addPathSegment(unlabelFields.getUserId())
                .addPathSegment("labels");
        builder.addQueryParameter("api_key", unlabelFields.getApiKey());
        if (unlabelFields.getAbuseType() != null) {
            builder.addQueryParameter("abuse_type", unlabelFields.getAbuseType());
        }
        return builder.build();
    }

    @Override
    protected void modifyRequestBuilder(Request.Builder builder) {
        builder.delete();
    }

    @Override
    UnlabelResponse buildResponse(@NotNull Response response, FieldSet requestFields)
            throws IOException {
        return new UnlabelResponse(response, requestFields);
    }
}
