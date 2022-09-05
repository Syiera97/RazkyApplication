package my.edu.utem.ftmk.msmd5113.razkyapplication.Caretaker.Inventory.AddProduct;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import my.edu.utem.ftmk.msmd5113.razkyapplication.DataModel.CaretakerDataEntity;
import my.edu.utem.ftmk.msmd5113.razkyapplication.DataModel.OrphanageDataEntity;
import my.edu.utem.ftmk.msmd5113.razkyapplication.DataModel.StockDetails;
import my.edu.utem.ftmk.msmd5113.razkyapplication.MainCaretakerActivity;
import my.edu.utem.ftmk.msmd5113.razkyapplication.R;

public class AddProductFragment extends Fragment {
    @BindView(R.id.rv_product)
    RecyclerView recyclerView;
//    @BindView(R.id.searchBar)
//    SearchView searchBar;
//    @BindView(R.id.img_no_data)
//    ImageView img_no_data;
//    @BindView(R.id.tv_no_data)
//    TextView tv_no_data;
    private Menu menuList;

    AddProductAdapter addProductAdapter;
    private FirebaseFirestore db;
    private final String TAG = "Failed:";
    private FirebaseAuth mAuth;
    String email;
    CaretakerDataEntity caretakerDataEntity = new CaretakerDataEntity();
    List<StockDetails> stockDetails = new ArrayList<>();
    String currentStock, minStock, totalCurrentStock;
    private static final DecimalFormat df = new DecimalFormat("0.00");


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        this.menuList = menu;
        menu.findItem(R.id.edit).setVisible(false);
        menu.findItem(R.id.add).setVisible(false);
        menu.findItem(R.id.save).setVisible(true);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_product, container, false);
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
        if(addProductAdapter == null) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setHasFixedSize(true);
            addProductAdapter = new AddProductAdapter(getContext(), stockDataEntityList);
        }else {
            addProductAdapter.setAdapter(stockDataEntityList);
        }
        recyclerView.setAdapter(addProductAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.save:
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Date saved successfully!")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //do things
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
                saveDetails();
                hideSaveButton();
        }
        return(super.onOptionsItemSelected(item));
    }

    private void fetchUserData(String email) {
        db = FirebaseFirestore.getInstance();
        db.collection("caretakerDetails").whereEqualTo("email", email).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot document:task.getResult().getDocuments()){
                        caretakerDataEntity.setCaretakerName(String.valueOf(document.getData().get("caretakerName")));
                        caretakerDataEntity.setEmail(String.valueOf(document.getData().get("email")));
                        caretakerDataEntity.setOrphanageName(String.valueOf(document.getData().get("orphanageName")));
                    }
                }
            }
        });
    }

    private String inventoryLeftVal(int sumCurrentStock, int sumMinStock){
        String lastVal;
        double val, temp, total;
        temp = (sumMinStock - sumCurrentStock);
        val = temp/sumMinStock;
        total  = val * 100;
        lastVal = df.format(total);
        return lastVal;
    }

    private String iconVal(double lastVal){
        String icon;
        if(lastVal > 70){
            icon = "R";
        } else if(lastVal > 35 && lastVal < 70){
            icon = "A";
        } else {
            icon = "G";
        }
        return icon;
    }

    private String dayToStockOut(int sumCurrentStock, int sumMinStock){
        double totalDay, temp;
        temp = ((double)sumCurrentStock/(double)sumMinStock);
        totalDay = temp * 14;
        return df.format(totalDay) + " day(s)";
    }

    private void saveDetails() {
        String invVal, dayToStockout, iconVal;
        stockDetails = addProductAdapter.getStockDetails();
        int sumCurrentStock = 0;
        int sumMinStock = 0;
        int sumStockRequire = 0;
        List<Integer> totalCurrentStock = new ArrayList<>();
        List<Integer> totalMinStock = new ArrayList<>();
        List<Integer> totalStockRequire = new ArrayList<>();
        for(StockDetails stockData: stockDetails){
            int currentStock = Integer.parseInt(stockData.getCurrentStock());
            int minStock = Integer.parseInt(stockData.getMinStock());
            calculateStockRequire(minStock, currentStock, stockData);
            int stockRequire = Integer.parseInt(stockData.getStockRequire());
            totalStockRequire.add(stockRequire);
            totalCurrentStock.add(currentStock);
            totalMinStock.add(minStock);
        }
        for(int c : totalCurrentStock){
            sumCurrentStock += c;
        }
        for(int m : totalMinStock){
            sumMinStock += m;
        }
        for(int s : totalStockRequire){
            sumStockRequire += s;
        }
        invVal = inventoryLeftVal(sumCurrentStock, sumMinStock);
        dayToStockout = dayToStockOut(sumCurrentStock, sumMinStock);
        iconVal = iconVal(Double.parseDouble(invVal));


        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = sdf.format(c.getTime());

        OrphanageDataEntity orphanageDataEntity = new OrphanageDataEntity(caretakerDataEntity.getOrphanageName(), stockDetails, invVal, strDate, iconVal,  strDate, String.valueOf(sumStockRequire), dayToStockout);
        db = FirebaseFirestore.getInstance();

        CollectionReference dbDonor = db.collection("orphanageDetails");
        dbDonor.add(orphanageDataEntity).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
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

    private void calculateStockRequire(int minStock, int currentStock, StockDetails stockdata) {
        int stockRequire;
        stockRequire = minStock - currentStock;
        stockdata.setStockRequire(String.valueOf(stockRequire));
    }

    private void hideSaveButton(){
        MenuItem item = menuList.findItem(R.id.save);
        item.setVisible(false);
    }
}
