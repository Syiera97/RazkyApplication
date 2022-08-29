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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
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
import my.edu.utem.ftmk.msmd5113.razkyapplication.DataModel.OrphanageDataEntity;
import my.edu.utem.ftmk.msmd5113.razkyapplication.DataModel.StockDetails;
import my.edu.utem.ftmk.msmd5113.razkyapplication.MainActivity;
import my.edu.utem.ftmk.msmd5113.razkyapplication.R;

public class DonationDetailsFragment extends Fragment {

    @BindView(R.id.btn_request_tac)
    TextView btnTAC;
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
    @BindView(R.id.tac_val)
    TextView tac_val;

    AlertDialog.Builder builder;
    private FirebaseFirestore db;

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
        fetchData();


        btnTAC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Succesfully sent the TAC !", Toast.LENGTH_LONG).show();
            }
        });

        btnConfirm.setOnSlideCompleteListener(new SlideToActView.OnSlideCompleteListener() {
            @Override
            public void onSlideComplete(@NonNull SlideToActView slideToActView) {
                builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("You have successfully donate RM XXX").setTitle("Thank you !");
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
                                donationDetailsDataEntity.setDonationAmount(jsonObject.getString("donationAmt"));
                                donationDetailsDataEntity.setAccNo(jsonObject.getString("accNo"));
                                donationDetailsDataEntity.setAnonymous(jsonObject.getBoolean("isAnonymous"));
                                donationDetailsDataEntity.setDonatorID(jsonObject.getString("donatorID"));
                                donationDetailsDataEntity.setEffectiveDate(jsonObject.getString("effectiveDate"));
                                donationDetailsDataEntity.setPhoneNumber(jsonObject.getString("phoneNo"));
                                donationDetailsDataEntity.setOrphanageName(jsonObject.getString("orphanageName"));
                                donationDetailsDataEntity.setReferenceID(jsonObject.getString("referenceID"));
                                donationDetailsDataEntity.setDonatorEmail(jsonObject.getString("donatorEmail"));
//                                JSONArray donationData = jsonObject.getJSONArray("donationItem");
//                                int donationItemLength = donationData.length();
//                                for(int a = 0; a <donationItemLength; a++) {
//                                    DonationItem donationItem = new DonationItem();
//                                    JSONObject jsonObject1 = donationData.getJSONObject(a);
//                                    donationItem.setProductName(jsonObject1.getString("productName"));
//                                    donationItem.setProductImage(jsonObject1.getString("productImage"));
//                                    donationItem.setQuantity(jsonObject1.getString("quantity"));
//                                    donationItem.setCurrentStock(jsonObject1.getString("currentStock"));
//                                    donationItem.setMinStock(jsonObject1.getString("minStock"));
//                                    donationItem.setPrice(jsonObject1.getString("price"));
//                                    donationItemList.add(donationItem);
//                                }
//                                donationDetailsDataEntity.setDonationItem(donationItemList);
                                donationDetailsDataEntities.add(donationDetailsDataEntity);
                            }
                            setData(donationDetailsDataEntities);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

    }

    private void setData(List<DonationDetailsDataEntity> donationDetailsDataEntities) {
        if(donationDetailsDataEntities != null){
            for(DonationDetailsDataEntity donationDetailsDataEntity : donationDetailsDataEntities){
                donor_name_val.setText(donationDetailsDataEntity.getDonorName() != null ? donationDetailsDataEntity.getDonorName() : "");
                donor_email_val.setText(donationDetailsDataEntity.getDonatorEmail() != null ? donationDetailsDataEntity.getDonatorEmail() : "");
                donor_contact_val.setText(donationDetailsDataEntity.getPhoneNumber() != null ? donationDetailsDataEntity.getPhoneNumber() : "");
                donor_amount_val.setText(donationDetailsDataEntity.getDonationAmount() != null ? donationDetailsDataEntity.getDonationAmount() : "");
                from_acc_val.setText(donationDetailsDataEntity.getAccNo() != null ? donationDetailsDataEntity.getAccNo() : "");
                to_acc_val.setText(donationDetailsDataEntity.getAccNo() != null ? donationDetailsDataEntity.getAccNo() : "");
                recepient_name_val.setText(donationDetailsDataEntity.getOrphanageName() != null ? donationDetailsDataEntity.getOrphanageName() : "");
            }
        }
    }
}
