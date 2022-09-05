package my.edu.utem.ftmk.msmd5113.razkyapplication.Donor.MyProfile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import my.edu.utem.ftmk.msmd5113.razkyapplication.DataModel.DonationDetailsDataEntity;
import my.edu.utem.ftmk.msmd5113.razkyapplication.DataModel.DonationItem;
import my.edu.utem.ftmk.msmd5113.razkyapplication.DataModel.DonorDataEntity;
import my.edu.utem.ftmk.msmd5113.razkyapplication.MainActivity;
import my.edu.utem.ftmk.msmd5113.razkyapplication.R;
import my.edu.utem.ftmk.msmd5113.razkyapplication.SplashCaretakerActivity;

public class MyProfileFragment extends Fragment {

    @BindView(R.id.rv_history_list)
    RecyclerView recyclerView;
    @BindView(R.id.scrollView)
    NestedScrollView nestedScrollView;
    @BindView(R.id.switch_persona)
    TextView tvSwitchRole;
    @BindView(R.id.user_name)
    TextView user_name;
    @BindView(R.id.tv_no_data)
    TextView tvNoData;
    @BindView(R.id.img_no_data)
    ImageView imgNoData;

    DonorDataEntity donorDataEntity;
    MyProfileAdapter myProfileAdapter;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    String email;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_profile, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        if(getActivity() != null && getActivity() instanceof MainActivity) {
            donorDataEntity = ((MainActivity) getActivity()).getDonorDataEntity();
        }

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            email = currentUser.getEmail();
            fetchHistoryData(email);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setNestedScrollingEnabled(false);
        nestedScrollView.setNestedScrollingEnabled(true);

        switchBtnClicked();
    }

    private void fetchHistoryData(String email) {
        db = FirebaseFirestore.getInstance();
        db.collection("completedDonationDetails").whereEqualTo("donatorEmail", email).get().
                addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
                    bindUi(donationDetailsDataEntityList);
                    ((MainActivity) getActivity()).setDonationDetailsDataEntityList(donationDetailsDataEntityList);
                }
            }
        });
    }

    private void bindUi(List<DonationDetailsDataEntity> donationDetailsDataEntityList) {
        if(donationDetailsDataEntityList != null && donationDetailsDataEntityList.size() > 1) {
            tvNoData.setVisibility(View.GONE);
            imgNoData.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            List<DonationHistoryDataEntity> donationHistoryDataEntityList = new ArrayList<>();
            for (DonationDetailsDataEntity donationDetailsDataEntity1 : donationDetailsDataEntityList) {
                user_name.setText(donationDetailsDataEntity1.getDonorName());
                DonationHistoryDataEntity donationHistoryDataEntity = new DonationHistoryDataEntity();
                donationHistoryDataEntity.setOrphanageNamee(donationDetailsDataEntity1.getOrphanageName());
                donationHistoryDataEntity.setDonatedDate(donationDetailsDataEntity1.getEffectiveDate());
                donationHistoryDataEntityList.add(donationHistoryDataEntity);
            }
            setRecycleView(donationHistoryDataEntityList);
        }else{
            tvNoData.setVisibility(View.VISIBLE);
            imgNoData.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }

    private void switchBtnClicked() {
        tvSwitchRole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread background = new Thread() {
                    public void run() {
                        try {
                            sleep(500);
                            Intent i=new Intent(getActivity().getBaseContext(), SplashCaretakerActivity.class);
                            startActivity(i);

                            //Remove activity
                            getActivity().finish();
                        } catch (Exception e) {
                            Log.e("Error", String.valueOf(e));
                        }
                    }
                };
                // start thread
                background.start();

            }
        });
    }

    private void setRecycleView(List<DonationHistoryDataEntity> donationHistoryDataEntityList){
        if(myProfileAdapter == null) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setHasFixedSize(true);
            myProfileAdapter = new MyProfileAdapter(getContext(), donationHistoryDataEntityList);
        }else {
            myProfileAdapter.setAdapter(donationHistoryDataEntityList);
        }
        recyclerView.setAdapter(myProfileAdapter);
    }
}
