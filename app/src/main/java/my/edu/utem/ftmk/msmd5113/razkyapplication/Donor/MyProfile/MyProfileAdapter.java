package my.edu.utem.ftmk.msmd5113.razkyapplication.Donor.MyProfile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import my.edu.utem.ftmk.msmd5113.razkyapplication.Donor.DonateNow.DonateNowDetailsAdapter;
import my.edu.utem.ftmk.msmd5113.razkyapplication.Donor.DonateNow.StockDataEntity;
import my.edu.utem.ftmk.msmd5113.razkyapplication.MainActivity;
import my.edu.utem.ftmk.msmd5113.razkyapplication.R;

public class MyProfileAdapter extends RecyclerView.Adapter<MyProfileAdapter.MyProfileAdapterViewHolder> {

    private List<DonationHistoryDataEntity> donationHistoryDataEntityList;
    private Context context;

    public MyProfileAdapter(Context context, List<DonationHistoryDataEntity> donationHistoryDataEntities) {
        this.context = context;
        this.donationHistoryDataEntityList = donationHistoryDataEntities;
    }

    public void setAdapter(List<DonationHistoryDataEntity> donationHistoryDataEntities){
        this.donationHistoryDataEntityList = donationHistoryDataEntities;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyProfileAdapter.MyProfileAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_donation_history, parent, false);
        return new MyProfileAdapter.MyProfileAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyProfileAdapter.MyProfileAdapterViewHolder myProfileAdapterViewHolder, int i) {
        myProfileAdapterViewHolder.orphanage_name.setText(donationHistoryDataEntityList.get(i).getOrphanageNamee());
        myProfileAdapterViewHolder.donation_time.setText(donationHistoryDataEntityList.get(i).getDonatedDate());
    }

    @Override
    public int getItemCount() {
        return donationHistoryDataEntityList.size();
    }

    public class MyProfileAdapterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.view1)
        View view;
        @BindView(R.id.orphanage_name)
        TextView orphanage_name;
        @BindView(R.id.donation_time)
        TextView donation_time;

        public MyProfileAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((MainActivity) context).navigateToDonationHistoryFragment();
                    ((MainActivity) context).setSelectedOrphanage(donationHistoryDataEntityList.get(getAdapterPosition()).getOrphanageNamee());
                }
            });
        }
    }
}
