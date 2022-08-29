package my.edu.utem.ftmk.msmd5113.razkyapplication.Donor.DonateNow;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.ncorti.slidetoact.SlideToActView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import my.edu.utem.ftmk.msmd5113.razkyapplication.DataModel.DonationDetailsDataEntity;
import my.edu.utem.ftmk.msmd5113.razkyapplication.DataModel.DonationItem;
import my.edu.utem.ftmk.msmd5113.razkyapplication.DataModel.DonorDataEntity;
import my.edu.utem.ftmk.msmd5113.razkyapplication.DataModel.OrphanageDataEntity;
import my.edu.utem.ftmk.msmd5113.razkyapplication.DataModel.StockDetails;
import my.edu.utem.ftmk.msmd5113.razkyapplication.MainActivity;
import my.edu.utem.ftmk.msmd5113.razkyapplication.R;

public class DonationDetailsFragment extends Fragment {

    @BindView(R.id.btn_confirm)
    SlideToActView btnConfirm;
    @BindView(R.id.donor_name_val)
    TextView donor_name_val;
    @BindView(R.id.donor_email_val)
    TextView donor_email_val;
    @BindView(R.id.donor_contact_val)
    TextView donor_contact_val;
    @BindView(R.id.donor_amount_val)
    TextView donor_amount_val;
    @BindView(R.id.from_acc_val)
    TextView from_acc_val;
    @BindView(R.id.to_acc_val)
    TextView to_acc_val;
    @BindView(R.id.recepient_name_val)
    TextView recepient_name_val;

    AlertDialog.Builder builder;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    String email;
    OrphanageDataEntity orphanageDataEntity;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_donation_details, container, false);
        ButterKnife.bind(this, view);
        container.removeAllViews();
        initView();
        return view;
    }

    private void initView() {

        if(getActivity() != null && getActivity() instanceof MainActivity){
            orphanageDataEntity = ((MainActivity) getActivity()).getOrphanageDetails();
        }

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            email = currentUser.getEmail();
            fetchUserData(email);
        }

        btnConfirm.setOnSlideCompleteListener(new SlideToActView.OnSlideCompleteListener() {
            @Override
            public void onSlideComplete(@NonNull SlideToActView slideToActView) {
                builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("You have successfully donated " + orphanageDataEntity.getTotalItemsRequire() + " items to " + orphanageDataEntity.getOrphanageName() + ".").setTitle("Thank you !");
                builder.setCancelable(false);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ((MainActivity) getContext()).navigateToDonationCompletedFragment();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    private void fetchUserData(String email) {
        db = FirebaseFirestore.getInstance();
        db.collection("donorDetails").whereEqualTo("donatorEmail", email).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    DonorDataEntity donorDataEntity = new DonorDataEntity();
                    for(DocumentSnapshot document:task.getResult().getDocuments()){
                        donorDataEntity.setDonatorName(String.valueOf(document.getData().get("donatorName")));
                        donorDataEntity.setDonatorEmail(String.valueOf(document.getData().get("donatorEmail")));
                        donorDataEntity.setPhoneNo(String.valueOf(document.getData().get("phoneNo")));
                    }
                    ((MainActivity) getActivity()).setDonorDetails(donorDataEntity);
                    bindUi(donorDataEntity);
                }
            }
        });
    }

    private void bindUi(DonorDataEntity donorDataEntity) {
        donor_name_val.setText(donorDataEntity.getDonatorName() != null ? donorDataEntity.getDonatorName() : "");
        donor_email_val.setText(donorDataEntity.getDonatorEmail() != null ? donorDataEntity.getDonatorEmail() : "");
        donor_contact_val.setText(donorDataEntity.getPhoneNo() != null ? donorDataEntity.getPhoneNo() : "");
        donor_amount_val.setText(orphanageDataEntity.getTotalItemsRequire());
        to_acc_val.setText(orphanageDataEntity.getOrphanageName());
    }

}
