package tliknowledge.sunil_technology_assessment.webservices;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public class Web_Helper {

    private static final String Ws_Root = "http://api.nytimes.com/svc/mostpopular/v2/mostviewed/";

    private HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    private TokenInterceptor tokenInterceptor = new TokenInterceptor();
    private OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).addInterceptor(tokenInterceptor).build();

    private Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .create();

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Ws_Root)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    public Retrofit getRetrofit() {
        return retrofit;
    }

    private class TokenInterceptor implements Interceptor {

        private TokenInterceptor() {

        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request initialRequest = chain.request();
            String sToken = "";
            if (sToken != null) {
                initialRequest = initialRequest.newBuilder()
                        .addHeader("Accept", "application/json; charset=utf-8")
                        .build();
            }

            Response response = chain.proceed(initialRequest);
            return response;
        }
    }


}
