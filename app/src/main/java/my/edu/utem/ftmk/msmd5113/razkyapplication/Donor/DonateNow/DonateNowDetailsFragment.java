package my.edu.utem.ftmk.msmd5113.razkyapplication.Donor.DonateNow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.common.reflect.TypeToken;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firestore.v1.Value;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.ncorti.slidetoact.SlideToActView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.security.Key;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import my.edu.utem.ftmk.msmd5113.razkyapplication.DataModel.OrphanageDataEntity;
import my.edu.utem.ftmk.msmd5113.razkyapplication.DataModel.StockDetails;
import my.edu.utem.ftmk.msmd5113.razkyapplication.MainActivity;
import my.edu.utem.ftmk.msmd5113.razkyapplication.R;

public class DonateNowDetailsFragment extends Fragment {

    @BindView(R.id.rv_item_donation)
    RecyclerView recyclerView;
    @BindView(R.id.scrollView)
    NestedScrollView nestedScrollView;
    @BindView(R.id.btn_donate)
    SlideToActView btnDonate;

    private DonateNowDetailsAdapter donateNowDetailsAdapter;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = database.getReference();
    private FirebaseFirestore db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_donate_now_details, container, false);
        ButterKnife.bind(this, view);
        container.removeAllViews();
        initView();
        return view;
    }

    private void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setNestedScrollingEnabled(false);
        nestedScrollView.setNestedScrollingEnabled(true);

        btnDonate.setOnSlideCompleteListener(new SlideToActView.OnSlideCompleteListener() {
            @Override
            public void onSlideComplete(@NonNull SlideToActView slideToActView) {
                ((MainActivity) getContext()).navigateToDonationDetailsFragment();
            }
        });

//        setData();

        setDataFromFirestore();
    }

    private void setDataFromFirestore() {
        db = FirebaseFirestore.getInstance();
        db.collection("orphanges").document("uYYTurwQ8WH4vKPnsFtX")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        List<OrphanagesDataEntity> oprhanagesDataEntities;
                        List<OrphanageDataEntity> orphanageDataEntityList = new ArrayList<>();
                        List<StockDetails> stockDataEntityList = new ArrayList<>();
                        oprhanagesDataEntities = (List<OrphanagesDataEntity>) document.getData().get("orphanageDetails");

                        Gson gson = new Gson();
                        JsonElement jsonElement = gson.toJsonTree(oprhanagesDataEntities);

                        try {
                            JSONArray response = new JSONArray(jsonElement.toString());
                            int length = response.length();

                            for (int i = 0; i < length; i++) {
                                OrphanageDataEntity orphanageDataEntity = new OrphanageDataEntity();
                                JSONObject jsonObject = response.getJSONObject(i);
                                orphanageDataEntity.setOrphanageName(jsonObject.getString("orphanageName"));
                                JSONArray stockData = jsonObject.getJSONArray("stockDetails");
                                int stockLength = stockData.length();
                                for(int a = 0; a <stockLength; a++){
                                    StockDetails stockDataEntity = new StockDetails();
                                    JSONObject jsonObject1 = stockData.getJSONObject(a);
                                    stockDataEntity.setProductName(jsonObject1.getString("productName"));
                                    stockDataEntity.setProductImage(jsonObject1.getString("productImage"));
                                    stockDataEntity.setCurrentStock(jsonObject1.getString("currentStock"));
                                    stockDataEntity.setPrice(jsonObject1.getString("price"));
                                    stockDataEntity.setMinStock(jsonObject1.getString("minStock"));
                                    stockDataEntityList.add(stockDataEntity);
                                }
                                setRecycleView(stockDataEntityList);
                                orphanageDataEntity.setStockDetails(stockDataEntityList);
                                orphanageDataEntityList.add(orphanageDataEntity);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    private void setData() {
        databaseReference = database.getReference("orphanagesDetails").child("0").child("stockDetails");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<StockDataEntity> stockDataEntityList = new ArrayList<>();
                for(DataSnapshot ds : snapshot.getChildren()){
                    StockDataEntity stockDataEntity = ds.getValue(StockDataEntity.class);
                    stockDataEntityList.add(stockDataEntity);
                }
//                setRecycleView(stockDataEntityList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("The read failed: " + error.getCode());
            }
        });
    }

    private void setRecycleView(List<StockDetails> stockDataEntityList){
        if(donateNowDetailsAdapter == null) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setHasFixedSize(true);
            donateNowDetailsAdapter = new DonateNowDetailsAdapter(getContext(), stockDataEntityList);
        }else {
            donateNowDetailsAdapter.setAdapter(stockDataEntityList);
        }
        recyclerView.setAdapter(donateNowDetailsAdapter);
    }

}
