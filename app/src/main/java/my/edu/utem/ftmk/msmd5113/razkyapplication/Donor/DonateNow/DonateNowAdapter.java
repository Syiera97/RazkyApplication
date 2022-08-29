package my.edu.utem.ftmk.msmd5113.razkyapplication.Donor.DonateNow;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import android.app.Fragment;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import my.edu.utem.ftmk.msmd5113.razkyapplication.DataModel.OrphanageDataEntity;
import my.edu.utem.ftmk.msmd5113.razkyapplication.MainActivity;
import my.edu.utem.ftmk.msmd5113.razkyapplication.MainCaretakerActivity;
import my.edu.utem.ftmk.msmd5113.razkyapplication.R;

public class DonateNowAdapter extends RecyclerView.Adapter<DonateNowAdapter.DonateNowAdapterViewHolder>{

    private List<OrphanageDataEntity> orphanagesDataEntityList;
    private Context context;

    public DonateNowAdapter(Context context, List<OrphanageDataEntity> oprhanagesDataEntities) {
        this.context = context;
        this.orphanagesDataEntityList = oprhanagesDataEntities;
    }

    public void setAdapter(List<OrphanageDataEntity> oprhanagesDataEntities){
        this.orphanagesDataEntityList = oprhanagesDataEntities;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DonateNowAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_donate_now, parent, false);
        return new DonateNowAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonateNowAdapterViewHolder holder, int i) {
        if(orphanagesDataEntityList != null){
            OrphanageDataEntity data = orphanagesDataEntityList.get(i);
            holder.orphanage_name.setText(data.getOrphanageName());
            holder.created_updated_date_time.setText(data.getCreatedDate());
            if(data.getAlertIcon().equals("G")){
                holder.exclaimation_icon.setImageResource(R.drawable.ic_exclaim_mark_green);
            }else if(data.getAlertIcon().equals("R")) {
                holder.exclaimation_icon.setImageResource(R.drawable.ic_exclaim_mark_red);
            }else if(data.getAlertIcon().equals("Y")){
                holder.exclaimation_icon.setImageResource(R.drawable.ic_exclaim_mark_yellow);
            }
            holder.inventory_value.setText(data.getInventoryLeftValue());
            holder.stock_out_day_value.setText(data.getStockOutDays());
            holder.total_item_require.setText(data.getTotalItemsRequire());
        }
    }

    @Override
    public int getItemCount() {
        return orphanagesDataEntityList.size();
    }

    public class DonateNowAdapterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.view1)
        View view;
        @BindView(R.id.orphanage_name)
        TextView orphanage_name;
        @BindView(R.id.created_updated_date_time)
        TextView created_updated_date_time;
        @BindView(R.id.exclaimation_icon)
        ImageView exclaimation_icon;
        @BindView(R.id.stock_out_day_value)
        TextView stock_out_day_value;
        @BindView(R.id.inventory_value)
        TextView inventory_value;
        @BindView(R.id.total_item_require)
        TextView total_item_require;

        public DonateNowAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(context instanceof MainActivity) {
                        ((MainActivity) context).navigateToDetailsFragment();
                        ((MainActivity) context).setStocks(orphanagesDataEntityList.get(getAdapterPosition()).getStockDetails());
                        ((MainActivity) context).setOrphanageDetails(orphanagesDataEntityList.get(getAdapterPosition()));
                    }
                }
            });
        }
    }
}
