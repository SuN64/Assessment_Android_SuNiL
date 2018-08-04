package tliknowledge.sunil_technology_assessment.webservices;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import tliknowledge.sunil_technology_assessment.model.M_NewsArticle;
import tliknowledge.sunil_technology_assessment.utils.API_CallBack_IF;

public class API_NyTimes {
    Web_Helper web_Helper= new Web_Helper();
    public API_NyTimes()
    {

    }
    public interface NewsArticle_If{
        @Headers("Content-Type: application/json; charset=utf-8")
        @GET("all-sections/1.json?apikey=0025151598054d1285242e22104b1043")
        Call<M_NewsArticle> getNewsArticles();
    }

    public void getDataFromServer(final API_CallBack_IF aPI_CallBack_IF){
        NewsArticle_If newsArticle_If = web_Helper.getRetrofit().create(NewsArticle_If.class);
        Call<M_NewsArticle> mCall = newsArticle_If.getNewsArticles();
        mCall.enqueue(new Callback<M_NewsArticle>() {
            @Override
            public void onResponse(Call<M_NewsArticle> call, retrofit2.Response<M_NewsArticle> response) {
                if(response.isSuccessful()){
                    aPI_CallBack_IF.onRS_Success(response);
                }
            }

            @Override
            public void onFailure(Call<M_NewsArticle> call, Throwable t) {
                aPI_CallBack_IF.onRS_Failure(t.getMessage()+"");
            }
        });
    }


}
