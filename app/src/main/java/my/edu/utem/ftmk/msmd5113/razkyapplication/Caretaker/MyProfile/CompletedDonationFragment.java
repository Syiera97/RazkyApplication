package my.edu.utem.ftmk.msmd5113.razkyapplication.Caretaker.MyProfile;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import my.edu.utem.ftmk.msmd5113.razkyapplication.DataModel.CaretakerDataEntity;
import my.edu.utem.ftmk.msmd5113.razkyapplication.DataModel.DonationDetailsDataEntity;
import my.edu.utem.ftmk.msmd5113.razkyapplication.DataModel.DonationItem;
import my.edu.utem.ftmk.msmd5113.razkyapplication.DataModel.DonorDataEntity;
import my.edu.utem.ftmk.msmd5113.razkyapplication.MainActivity;
import my.edu.utem.ftmk.msmd5113.razkyapplication.MainCaretakerActivity;
import my.edu.utem.ftmk.msmd5113.razkyapplication.R;

public class CompletedDonationFragment extends Fragment {
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

    private CompletedDonationAdapter completedDonationAdapter;
    private DonationDetailsDataEntity donationDetailsDataEntity;

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
        if(getActivity() != null && getActivity() instanceof MainCaretakerActivity){
            donationDetailsDataEntity = ((MainCaretakerActivity) getActivity()).getDonationDetails();
            donor_name_val.setText(donationDetailsDataEntity.getDonorName());
            recepient_name_val.setText(donationDetailsDataEntity.getOrphanageName());

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            try {
                Date date = format.parse(donationDetailsDataEntity.getEffectiveDate());
                format = new SimpleDateFormat("dd MMM yyyy 'at' hh:mm a");
                String strDate = format.format(date);
                date_val.setText(strDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            donor_amount_val.setText(donationDetailsDataEntity.getNoItemsDonated());
            setRecycleView(donationDetailsDataEntity.getDonationItem());
        }
    }

    private void setRecycleView(List<DonationItem> donationItemList){
        if(completedDonationAdapter == null) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setHasFixedSize(true);
            completedDonationAdapter = new CompletedDonationAdapter(getContext(), donationItemList);
        }else {
            completedDonationAdapter.setAdapter(donationItemList);
        }
        recyclerView.setAdapter(completedDonationAdapter);
    }

}
