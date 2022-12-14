package my.edu.utem.ftmk.msmd5113.razkyapplication.Donor.DonateNow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import my.edu.utem.ftmk.msmd5113.razkyapplication.Caretaker.MyProfile.CompletedDonationAdapter;
import my.edu.utem.ftmk.msmd5113.razkyapplication.DataModel.DonationDetailsDataEntity;
import my.edu.utem.ftmk.msmd5113.razkyapplication.DataModel.DonationItem;
import my.edu.utem.ftmk.msmd5113.razkyapplication.DataModel.DonorDataEntity;
import my.edu.utem.ftmk.msmd5113.razkyapplication.DataModel.OrphanageDataEntity;
import my.edu.utem.ftmk.msmd5113.razkyapplication.DataModel.StockDetails;
import my.edu.utem.ftmk.msmd5113.razkyapplication.DataModel.UserComments;
import my.edu.utem.ftmk.msmd5113.razkyapplication.MainActivity;
import my.edu.utem.ftmk.msmd5113.razkyapplication.R;

public class DonationCompletedFragment extends Fragment {

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
    List<StockDetails> stockDataEntityList;
    List<DonationItem> donationItemList = new ArrayList<>();
    OrphanageDataEntity orphanageDataEntity;
    CompletedDonationAdapter completedDonationAdapter;
    DonorDataEntity donorDataEntity;
    private FirebaseFirestore db;


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
            orphanageDataEntity = ((MainActivity) getActivity()).getOrphanageDetails();
            stockDataEntityList = ((MainActivity) getActivity()).getStockDetails();
            donorDataEntity = ((MainActivity) getActivity()).getDonorDataEntity();
            donor_name_val.setText(donorDataEntity.getDonatorName());
            donor_amount_val.setText(orphanageDataEntity.getTotalItemsRequire());
            recepient_name_val.setText(orphanageDataEntity.getOrphanageName());
            date_val.setText(orphanageDataEntity.getUpdatedDate());
            for(int i = 0; i < stockDataEntityList.size(); i++){
                DonationItem donationItem = new DonationItem();
                StockDetails data = new StockDetails((Map<String, String>) stockDataEntityList.get(i));
                donationItem.setProductName(data.getProductName());
                donationItem.setQuantity(data.getStockRequire());
                donationItem.setCurrentStock(data.getCurrentStock());
                donationItem.setMinStock(data.getMinStock());
                donationItem.setProductImage("-");
                donationItemList.add(donationItem);
            }

            setRecycleView(donationItemList);
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

        saveDonation();
        deleteOrphanageData();
    }

    private void saveDonation() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = sdf.format(c.getTime());

        DonationDetailsDataEntity donationDetailsDataEntity = new DonationDetailsDataEntity(orphanageDataEntity.getOrphanageName(), donorDataEntity.getDonatorName(),
                false, donorDataEntity.getPhoneNo(), donationItemList,  strDate, donorDataEntity.getDonatorEmail(), orphanageDataEntity.getTotalItemsRequire());
        db = FirebaseFirestore.getInstance();

        CollectionReference dbDonor = db.collection("completedDonationDetails");
        dbDonor.add(donationDetailsDataEntity).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getContext(), "Data added into Firebase Firestore successfully", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Fail to add course \n" + e, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteOrphanageData(){
        db.collection("orphanageDetails").document(orphanageDataEntity.getDbKey()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getContext(), "Data has been deleted from Database.", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(getContext(), "Fail to delete the course. ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
