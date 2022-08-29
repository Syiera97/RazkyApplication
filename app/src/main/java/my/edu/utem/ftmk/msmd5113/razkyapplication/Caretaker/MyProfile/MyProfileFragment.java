package my.edu.utem.ftmk.msmd5113.razkyapplication.Caretaker.MyProfile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import my.edu.utem.ftmk.msmd5113.razkyapplication.DataModel.DonationDetailsDataEntity;
import my.edu.utem.ftmk.msmd5113.razkyapplication.DataModel.DonationItem;
import my.edu.utem.ftmk.msmd5113.razkyapplication.Donor.MyProfile.DonationHistoryDataEntity;
import my.edu.utem.ftmk.msmd5113.razkyapplication.R;

public class MyProfileFragment extends Fragment {

    @BindView(R.id.rv_history_list)
    RecyclerView recyclerView;
    @BindView(R.id.scrollView)
    NestedScrollView nestedScrollView;
    @BindView(R.id.switch_persona)
    TextView tvSwitchRole;

    MyProfileAdapter myProfileAdapter;
    private FirebaseFirestore db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_profile_caretaker, container, false);
        ButterKnife.bind(this, view);
        container.removeAllViews();
        initView();
        return view;
    }

    private void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setNestedScrollingEnabled(false);
        nestedScrollView.setNestedScrollingEnabled(true);

        fetchData();
    }

    private void fetchData() {
        db = FirebaseFirestore.getInstance();
        db.collection("donationDetails").document("pK9RlGyf0cW7poXlnL2t")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        try{
                            List<DonationDetailsDataEntity> donationDetailsDataEntityList;
                            List<DonationDetailsDataEntity> donationDetailsDataEntities = new ArrayList<>();
                            List<DonationItem> donationItemList = new ArrayList<>();
                            Gson gson = new Gson();
                            donationDetailsDataEntityList = (List<DonationDetailsDataEntity>) document.getData().get("data");
                            JsonElement jsonElement = gson.toJsonTree(donationDetailsDataEntityList);
                            JSONArray response = new JSONArray(jsonElement.toString());
                            int length = response.length();
                            for(int i = 0; i < length; i++){
                                JSONObject jsonObject = response.getJSONObject(i);
                                DonationDetailsDataEntity donationDetailsDataEntity = new DonationDetailsDataEntity();
                                donationDetailsDataEntity.setDonorName(jsonObject.getString("donatorName"));
//                                donationDetailsDataEntity.setDonationAmount(jsonObject.getString("donationAmt"));
                                donationDetailsDataEntity.setAnonymous(jsonObject.getBoolean("isAnonymous"));
//                                donationDetailsDataEntity.setDonatorID(jsonObject.getString("donatorID"));
                                donationDetailsDataEntity.setEffectiveDate(jsonObject.getString("effectiveDate"));
                                donationDetailsDataEntity.setPhoneNumber(jsonObject.getString("phoneNo"));
                                donationDetailsDataEntity.setOrphanageName(jsonObject.getString("orphanageName"));
//                                donationDetailsDataEntity.setReferenceID(jsonObject.getString("referenceID"));
                                donationDetailsDataEntity.setDonatorEmail(jsonObject.getString("donatorEmail"));
                                JSONArray donationData = jsonObject.getJSONArray("donationItem");
                                int donationItemLength = donationData.length();
                                for(int a = 0; a <donationItemLength; a++) {
                                    DonationItem donationItem = new DonationItem();
                                    JSONObject jsonObject1 = donationData.getJSONObject(a);
                                    donationItem.setProductName(jsonObject1.getString("productName"));
                                    donationItem.setQuantity(jsonObject1.getString("quantity"));
//                                    donationItem.setPrice(jsonObject1.getString("price"));
                                    donationItemList.add(donationItem);
                                }
                                donationDetailsDataEntity.setDonationItem(donationItemList);
                                donationDetailsDataEntities.add(donationDetailsDataEntity);
                            }
                            setRecycleView(donationDetailsDataEntities);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

        });

    }


    private void setRecycleView(List<DonationDetailsDataEntity> donationDetailsDataEntityList){
        if(myProfileAdapter == null) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setHasFixedSize(true);
            myProfileAdapter = new MyProfileAdapter(getContext(), donationDetailsDataEntityList);
        }else {
            myProfileAdapter.setAdapter(donationDetailsDataEntityList);
        }
        recyclerView.setAdapter(myProfileAdapter);
    }
}
