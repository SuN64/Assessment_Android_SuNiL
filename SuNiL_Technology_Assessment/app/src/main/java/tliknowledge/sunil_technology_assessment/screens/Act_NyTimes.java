package tliknowledge.sunil_technology_assessment.screens;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;
import tliknowledge.sunil_technology_assessment.R;
import tliknowledge.sunil_technology_assessment.adapter.Adp_NyTimes;
import tliknowledge.sunil_technology_assessment.custom.RotateLoading;
import tliknowledge.sunil_technology_assessment.model.M_NewsArticle;
import tliknowledge.sunil_technology_assessment.model.M_Result;
import tliknowledge.sunil_technology_assessment.utils.API_CallBack_IF;
import tliknowledge.sunil_technology_assessment.utils.RV_TouchListener;
import tliknowledge.sunil_technology_assessment.utils.Utils;
import tliknowledge.sunil_technology_assessment.webservices.API_NyTimes;
import tliknowledge.sunil_technology_assessment.webservices.Web_Helper;

public class Act_NyTimes extends AppCompatActivity {

    private RecyclerView rv_nyTimes_list;
    private Act_NyTimes act_nyTimes;
    private Adp_NyTimes adp_nyTimes;
    private ArrayList<M_Result> al_M_results= new ArrayList<>();
    private RotateLoading rotateLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_ny_times);

        init();

        findXmlIds();

        if (Utils.isNetworkAvailable(act_nyTimes)) {
            getNyTimesNewsArticles();
        } else {
            Utils.displayCenterToast(act_nyTimes, getString(R.string.common_error_network));
        }

        setClick();
    }

    private void setClick() {

        rv_nyTimes_list.addOnItemTouchListener(new RV_TouchListener(getApplicationContext(), rv_nyTimes_list, new RV_TouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                M_Result m_result = al_M_results.get(position);
                Intent mIntent = new Intent(act_nyTimes,Act_Detail.class);
                mIntent.putExtra(Utils.key_articleURL,m_result.getUrl());
                startActivity(mIntent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void init() {
        act_nyTimes=this;
    }

    private void findXmlIds() {
        rv_nyTimes_list = (RecyclerView) findViewById(R.id.rv_nyTimes_list);
        rotateLoading = (RotateLoading) findViewById(R.id.rotateloading);


    }

    private void getNyTimesNewsArticles() {
        rotateLoading.start();

        API_NyTimes api_nyTimes= new API_NyTimes();

        api_nyTimes.getDataFromServer(new API_CallBack_IF() {

            @Override
            public void onRS_Success(Object obj) {
                Response<M_NewsArticle> rs_M_NewsArticle = (Response<M_NewsArticle>) obj;
                M_NewsArticle m_NewsArticle = rs_M_NewsArticle.body();
                al_M_results = m_NewsArticle.getResults();
                adp_nyTimes = new Adp_NyTimes(act_nyTimes, al_M_results);
                rv_nyTimes_list.setHasFixedSize(true);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(act_nyTimes);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                rv_nyTimes_list.setLayoutManager(linearLayoutManager);
                rv_nyTimes_list.setAdapter(adp_nyTimes);
                rotateLoading.stop();

            }

            @Override
            public void onRS_Failure(String message) {
                rotateLoading.stop();
                Toast.makeText(act_nyTimes,"Something wrong with server connection..!",Toast.LENGTH_LONG).show();

            }
        });
    }
}
