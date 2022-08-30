package my.edu.utem.ftmk.msmd5113.razkyapplication.Donor.MyProfile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import my.edu.utem.ftmk.msmd5113.razkyapplication.Caretaker.MyProfile.CompletedDonationAdapter;
import my.edu.utem.ftmk.msmd5113.razkyapplication.DataModel.DonationItem;
import my.edu.utem.ftmk.msmd5113.razkyapplication.R;

public class DonationHistoryAdapter extends RecyclerView.Adapter<DonationHistoryAdapter.DonationHistoryViewHolder> {
    private List<DonationItem> donationItems;
    private Context context;

    public DonationHistoryAdapter(Context context, List<DonationItem> donationItemList) {
        this.context = context;
        this.donationItems = donationItemList;
    }

    public void setAdapter(List<DonationItem> donationItemList) {
        this.donationItems = donationItemList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DonationHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_donation_items, parent, false);
        return new DonationHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonationHistoryViewHolder holder, int position) {
        DonationItem data = new DonationItem((Map<String, String>) donationItems.get(position));
        holder.item_name.setText(data.getProductName());
        holder.quantity.setText(data.getQuantity() + " qty");
    }

    @Override
    public int getItemCount() {
        return donationItems.size();
    }

    public class DonationHistoryViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_name)
        TextView item_name;
        @BindView(R.id.quantity)
        TextView quantity;
        public DonationHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
