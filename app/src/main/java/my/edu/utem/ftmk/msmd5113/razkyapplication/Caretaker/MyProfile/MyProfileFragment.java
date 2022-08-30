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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import my.edu.utem.ftmk.msmd5113.razkyapplication.DataModel.CaretakerDataEntity;
import my.edu.utem.ftmk.msmd5113.razkyapplication.DataModel.DonationDetailsDataEntity;
import my.edu.utem.ftmk.msmd5113.razkyapplication.DataModel.DonationItem;
import my.edu.utem.ftmk.msmd5113.razkyapplication.Donor.MyProfile.DonationHistoryDataEntity;
import my.edu.utem.ftmk.msmd5113.razkyapplication.MainActivity;
import my.edu.utem.ftmk.msmd5113.razkyapplication.MainCaretakerActivity;
import my.edu.utem.ftmk.msmd5113.razkyapplication.R;

public class MyProfileFragment extends Fragment {

    @BindView(R.id.rv_history_list)
    RecyclerView recyclerView;
    @BindView(R.id.scrollView)
    NestedScrollView nestedScrollView;
    @BindView(R.id.switch_persona)
    TextView tvSwitchRole;
    @BindView(R.id.user_name)
    TextView userName;

    MyProfileAdapter myProfileAdapter;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    String email;

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

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            email = currentUser.getEmail();
            fetchUserData(email);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setNestedScrollingEnabled(false);
        nestedScrollView.setNestedScrollingEnabled(true);

    }

    private void fetchUserData(String email) {
        db = FirebaseFirestore.getInstance();
        db.collection("caretakerDetails").whereEqualTo("email", email).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    CaretakerDataEntity caretakerDataEntity = new CaretakerDataEntity();
                    for(DocumentSnapshot document:task.getResult().getDocuments()){
                        caretakerDataEntity.setCaretakerName(String.valueOf(document.getData().get("caretakerName")));
                        caretakerDataEntity.setEmail(String.valueOf(document.getData().get("email")));
                        caretakerDataEntity.setOrphanageName(String.valueOf(document.getData().get("orphanageName")));
                    }

                    userName.setText(caretakerDataEntity.getCaretakerName());
                    fetchHistoryData(caretakerDataEntity.getOrphanageName());
                }
            }
        });
    }

    private void fetchHistoryData(String orphanageName) {
        db = FirebaseFirestore.getInstance();
        db.collection("completedDonationDetails").whereEqualTo("orphanageName", orphanageName).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    List<DonationDetailsDataEntity> donationDetailsDataEntityList = new ArrayList<>();
                    for(DocumentSnapshot document : task.getResult().getDocuments()){
                        DonationDetailsDataEntity donationDetailsDataEntity = new DonationDetailsDataEntity();
                        donationDetailsDataEntity.setDonatorEmail(String.valueOf(document.getData().get("donatorEmail")));
                        donationDetailsDataEntity.setOrphanageName(String.valueOf(document.getData().get("orphanageName")));
                        donationDetailsDataEntity.setDonorName(String.valueOf(document.getData().get("donorName")));
                        donationDetailsDataEntity.setEffectiveDate(String.valueOf(document.getData().get("effectiveDate")));
                        donationDetailsDataEntity.setNoItemsDonated(String.valueOf(document.getData().get("noItemsDonated")));
                        donationDetailsDataEntity.setDonationItem((List<DonationItem>) document.getData().get("donationItem"));
                        donationDetailsDataEntityList.add(donationDetailsDataEntity);
                    }
                    setRecycleView(donationDetailsDataEntityList);
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
