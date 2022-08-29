package my.edu.utem.ftmk.msmd5113.razkyapplication.Caretaker.MyProfile;

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
import my.edu.utem.ftmk.msmd5113.razkyapplication.DataModel.DonationDetailsDataEntity;
import my.edu.utem.ftmk.msmd5113.razkyapplication.Donor.MyProfile.DonationHistoryDataEntity;
import my.edu.utem.ftmk.msmd5113.razkyapplication.MainActivity;
import my.edu.utem.ftmk.msmd5113.razkyapplication.MainCaretakerActivity;
import my.edu.utem.ftmk.msmd5113.razkyapplication.R;

public class MyProfileAdapter extends RecyclerView.Adapter<MyProfileAdapter.MyProfileAdapterViewHolder> {

    private List<DonationDetailsDataEntity> donationDetailsDataEntities;
    private Context context;

    public MyProfileAdapter(Context context, List<DonationDetailsDataEntity> donationDetailsDataEntityList) {
        this.context = context;
        this.donationDetailsDataEntities = donationDetailsDataEntityList;
    }

    public void setAdapter(List<DonationDetailsDataEntity> donationDetailsDataEntityList){
        this.donationDetailsDataEntities = donationDetailsDataEntityList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyProfileAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_completed_donation_history, parent, false);
        return new MyProfileAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyProfileAdapterViewHolder holder, int i) {
        holder.orphanage_name.setText(donationDetailsDataEntities.get(i).getOrphanageName());
        holder.donation_time.setText(donationDetailsDataEntities.get(i).getEffectiveDate());
//        holder.donation_amount.setText("RM" + donationDetailsDataEntities.get(i).getDonationAmount());
    }

    @Override
    public int getItemCount() {
        return donationDetailsDataEntities.size();
    }

    public class MyProfileAdapterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.view1)
        View view;
        @BindView(R.id.donation_amount)
        TextView donation_amount;
        @BindView(R.id.donation_time)
        TextView donation_time;
        @BindView(R.id.orphanage_name)
        TextView orphanage_name;
        public MyProfileAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(context instanceof MainCaretakerActivity) {
                        ((MainCaretakerActivity) context).navigateToDonationCompletedFragment();
                        ((MainCaretakerActivity) context).setDonationDetails(donationDetailsDataEntities.get(getAdapterPosition()));
                    }
                }
            });
        }
    }
}
