package my.edu.utem.ftmk.msmd5113.razkyapplication.Donor.MyProfile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import my.edu.utem.ftmk.msmd5113.razkyapplication.DataModel.DonationDetailsDataEntity;
import my.edu.utem.ftmk.msmd5113.razkyapplication.DataModel.DonationItem;
import my.edu.utem.ftmk.msmd5113.razkyapplication.MainActivity;
import my.edu.utem.ftmk.msmd5113.razkyapplication.R;

public class DonationHistoryFragment extends Fragment {
    @BindView(R.id.reference_ID_val)
    TextView reference_ID_val;
    @BindView(R.id.donor_name_val)
    TextView donor_name_val;
    @BindView(R.id.donor_amount_val)
    TextView donor_amount_val;
    @BindView(R.id.recepient_name_val)
    TextView recepient_name_val;
    @BindView(R.id.date_val)
    TextView date_val;
    @BindView(R.id.rv_items)
    RecyclerView recyclerView;

    private DonationHistoryAdapter donationHistoryAdapter;
    List<DonationDetailsDataEntity> data = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_donation_completed, container, false);
        ButterKnife.bind(this, view);
        container.removeAllViews();
        initView();
        return view;
    }

    private void initView() {
        if(getActivity() != null && getActivity() instanceof MainActivity){
            data = ((MainActivity) getActivity()).getDonationHistory();

            for(DonationDetailsDataEntity details : data){
                if(details.getOrphanageName().equals(((MainActivity) getActivity()).getSelectedOrphanage())){
                    donor_name_val.setText(details.getDonorName());
                    recepient_name_val.setText(details.getOrphanageName());
                    date_val.setText(details.getEffectiveDate());
                    donor_amount_val.setText(details.getNoItemsDonated());
                    setRecycleView(details.getDonationItem());
                }
            }
        }
    }

    private void setRecycleView(List<DonationItem> donationItemList) {
        if(donationHistoryAdapter == null) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setHasFixedSize(true);
            donationHistoryAdapter = new DonationHistoryAdapter(getContext(), donationItemList);
        }else {
            donationHistoryAdapter.setAdapter(donationItemList);
        }
        recyclerView.setAdapter(donationHistoryAdapter);
    }
}
