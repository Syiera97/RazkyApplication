package my.edu.utem.ftmk.msmd5113.razkyapplication.Donor.DonateNow;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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
import my.edu.utem.ftmk.msmd5113.razkyapplication.DataModel.OrphanageDataEntity;
import my.edu.utem.ftmk.msmd5113.razkyapplication.DataModel.StockDetails;
import my.edu.utem.ftmk.msmd5113.razkyapplication.MainActivity;
import my.edu.utem.ftmk.msmd5113.razkyapplication.R;

public class DonateNowFragment extends Fragment {

    @BindView(R.id.rv_orphanage_list)
    RecyclerView recyclerView;
    @BindView(R.id.btn_find_nearby_orphanage)
    TextView btnFindOrphanages;

    private DonateNowAdapter listAdapter;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = database.getReference();
    private FirebaseFirestore db;
    private String TAG = "Failed:";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_donate_now, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        btnFindOrphanages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getContext()).navigateToDonateNearbyFragment();
            }
        });

        fetchOrphanageData();
    }

    private void fetchOrphanageData() {
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
                                orphanageDataEntity.setCreatedDate(jsonObject.getString("createdDate"));
                                orphanageDataEntity.setUpdatedDate(jsonObject.getString("updatedDate"));
                                orphanageDataEntity.setAlertIcon(jsonObject.getString("alertIcon"));
                                orphanageDataEntity.setInventoryLeftValue(jsonObject.getString("inventoryLeftValue"));
                                orphanageDataEntity.setTotalItemsRequire(jsonObject.getString("totalItemsRequire"));
                                orphanageDataEntity.setStockOutDays(jsonObject.getString("stockOutDays"));
                                JSONArray stockData = jsonObject.getJSONArray("stockDetails");
                                int stockLength = stockData.length();
                                List<StockDetails> stockDataEntityList = new ArrayList<>();
                                for(int a = 0; a <stockLength; a++){
                                    StockDetails stockDataEntity = new StockDetails();
                                    JSONObject jsonObject1 = stockData.getJSONObject(a);
                                    stockDataEntity.setProductName(jsonObject1.getString("productName"));
                                    stockDataEntity.setProductImage(jsonObject1.getString("productImage"));
                                    stockDataEntity.setCurrentStock(jsonObject1.getString("currentStock"));
                                    stockDataEntity.setStockRequire(jsonObject1.getString("stockRequire"));
                                    stockDataEntity.setMinStock(jsonObject1.getString("minStock"));
                                    stockDataEntityList.add(stockDataEntity);
                                }
                                orphanageDataEntity.setStockDetails(stockDataEntityList);
                                orphanageDataEntityList.add(orphanageDataEntity);
                            }
                            setRecyclerView(orphanageDataEntityList);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    Log.w(TAG, "Error getting documents.", task.getException());
                }
            }
        });
    }

    private void setRecyclerView(List<OrphanageDataEntity> oprhanagesDataEntities) {
        if(oprhanagesDataEntities != null) {
            if (listAdapter == null) {
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setHasFixedSize(true);
                listAdapter = new DonateNowAdapter(getContext(), oprhanagesDataEntities);
            } else {
                listAdapter.setAdapter(oprhanagesDataEntities);
            }
            recyclerView.setAdapter(listAdapter);
        }
    }
}