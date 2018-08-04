package tliknowledge.sunil_technology_assessment.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import tliknowledge.sunil_technology_assessment.R;
import tliknowledge.sunil_technology_assessment.model.M_Media;
import tliknowledge.sunil_technology_assessment.model.M_Result;
import tliknowledge.sunil_technology_assessment.utils.Transformer_Circle;

public class Adp_NyTimes extends RecyclerView.Adapter<Adp_NyTimes.VH_NyTimes> {

    private Context mContext;
    private List<M_Result> al_M_Result=new ArrayList<>();

    public class VH_NyTimes extends RecyclerView.ViewHolder
    {
        private TextView txt_heading, txt_subtitle,txt_date,txt_copyright;
        private ImageView img_pic;

        private VH_NyTimes(View view) {
            super(view);
            txt_heading = (TextView) view.findViewById(R.id.txt_heading);
            txt_subtitle = (TextView) view.findViewById(R.id.txt_subtitle);
            txt_date  = (TextView) view.findViewById(R.id.txt_date);
            txt_copyright  = (TextView) view.findViewById(R.id.txt_copyright);
            img_pic = (ImageView) view.findViewById(R.id.img_pic);
        }
    }


    public Adp_NyTimes(Context mContext, List<M_Result> results) {
        this.mContext = mContext;
        this.al_M_Result = results;
    }

    @Override
    public int getItemCount() {
        return al_M_Result.size();
    }

    @Override
    public VH_NyTimes onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_rv_ny_times, parent, false);

        return new VH_NyTimes(itemView);
    }

    @Override
    public void onBindViewHolder(VH_NyTimes vH_NyTimes, int position) {
        M_Result m_result = al_M_Result.get(position);
        List<M_Media> list_M_media = m_result.getMedia();
        M_Media m_Media = list_M_media.get(0);

        vH_NyTimes.txt_heading.setText(m_result.getTitle());
        vH_NyTimes.txt_subtitle.setText(m_result.getByline());
        vH_NyTimes.txt_date.setText(m_result.getPublishedDate());
        vH_NyTimes.txt_copyright.setText(m_Media.getCopyright());
        Picasso.with(mContext).load(m_Media.getMediaMetadata().get(0).getUrl()).transform(new Transformer_Circle()).into(vH_NyTimes.img_pic);



    }

}