package my.edu.utem.ftmk.msmd5113.razkyapplication.Caretaker.Inventory;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
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
import my.edu.utem.ftmk.msmd5113.razkyapplication.Donor.DonateNow.OrphanagesDataEntity;
import my.edu.utem.ftmk.msmd5113.razkyapplication.Donor.DonateNow.StockDataEntity;
import my.edu.utem.ftmk.msmd5113.razkyapplication.MainCaretakerActivity;
import my.edu.utem.ftmk.msmd5113.razkyapplication.R;

public class InventoryFragment extends Fragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    InventoryAdapter inventoryAdapter;
    private Menu menuList;
    private FirebaseFirestore db;
    private String TAG = "Failed:";


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        this.menuList = menu;
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inventory, container, false);
        ButterKnife.bind(this, view);
        container.removeAllViews();
        initView();
        return view;
    }

    private void initView() {
        setHasOptionsMenu(true);
        ActionBar actionBar = ((MainCaretakerActivity)getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);

        fetchStocksData();
    }

    private void fetchStocksData() {
        db = FirebaseFirestore.getInstance();
        db.collection("stockDetails").document("qec9mR7LQjTENY6xeAwa")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        List<StockDetails> stockDetailsList = new ArrayList<>();
                        List<StockDetails> stockDataEntityList = new ArrayList<>();
                        stockDataEntityList = (List<StockDetails>) document.getData().get("data");

                        Gson gson = new Gson();
                        JsonElement jsonElement = gson.toJsonTree(stockDataEntityList);

                        try {
                            JSONArray response = new JSONArray(jsonElement.toString());
                            int stockLength = response.length();
                            for(int a = 0; a <stockLength; a++){
                                StockDetails stockDataEntity = new StockDetails();
                                JSONObject jsonObject1 = response.getJSONObject(a);
                                stockDataEntity.setProductName(jsonObject1.getString("productName"));
                                stockDataEntity.setProductImage(jsonObject1.getString("productImage"));
//                                    stockDataEntity.setCurrentStock(jsonObject1.getString("currentStock"));
//                                stockDataEntity.setStockRequire(jsonObject1.getString("stockRequire"));
//                                    stockDataEntity.setMinStock(jsonObject1.getString("minStock"));
                                stockDetailsList.add(stockDataEntity);
                            }
                            setRecycleView(stockDetailsList);

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

    private void setRecycleView(List<StockDetails> stockDataEntityList) {
        if(inventoryAdapter == null) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setHasFixedSize(true);
            inventoryAdapter = new InventoryAdapter(getContext(), stockDataEntityList);
        }else {
            inventoryAdapter.setAdapter(stockDataEntityList);
        }
        recyclerView.setAdapter(inventoryAdapter);
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if(menu.findItem(R.id.edit).isChecked()){
            menu.findItem(R.id.save).setVisible(true);
        }
    }

    private void showSaveButton(){
        MenuItem item = menuList.findItem(R.id.save);
        item.setVisible(true);
        hideEditButton();
        hideAddButton();
    }

    private void hideEditButton(){
        MenuItem item = menuList.findItem(R.id.edit);
        item.setVisible(false);
    }

    private void hideAddButton(){
        MenuItem item = menuList.findItem(R.id.add);
        item.setVisible(false);
    }

    private void hideSaveButton(){
        MenuItem item = menuList.findItem(R.id.save);
        item.setVisible(false);
        showEditButton();
        showAddButton();
    }

    private void showEditButton(){
        MenuItem item = menuList.findItem(R.id.edit);
        item.setVisible(true);
    }

    private void showAddButton(){
        MenuItem item = menuList.findItem(R.id.add);
        item.setVisible(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.add:
                ((MainCaretakerActivity) getContext()).navigateToAddProductFragment();
                showSaveButton();
                return (true);
            case R.id.edit:
                if(inventoryAdapter != null){
                    inventoryAdapter.setEditMode();
                    inventoryAdapter.notifyDataSetChanged();
                }
                showSaveButton();
                return (true);
            case R.id.save:
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Data saved successfully!")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //do things
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
                hideSaveButton();
                if(inventoryAdapter != null){
                    inventoryAdapter.setEditMode();
                    inventoryAdapter.notifyDataSetChanged();
                }
        }
        return(super.onOptionsItemSelected(item));
    }
}
