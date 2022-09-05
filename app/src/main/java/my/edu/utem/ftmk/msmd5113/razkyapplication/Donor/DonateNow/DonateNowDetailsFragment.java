package my.edu.utem.ftmk.msmd5113.razkyapplication.Donor.DonateNow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
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
import com.ncorti.slidetoact.SlideToActView;

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

public class DonateNowDetailsFragment extends Fragment {

    @BindView(R.id.rv_item_donation)
    RecyclerView recyclerView;
    @BindView(R.id.scrollView)
    NestedScrollView nestedScrollView;
    @BindView(R.id.btn_donate)
    SlideToActView btnDonate;

    private DonateNowDetailsAdapter donateNowDetailsAdapter;
    List<StockDetails> stockDataEntityList;

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

        if(getActivity() != null && getActivity() instanceof MainActivity){
            stockDataEntityList = ((MainActivity) getActivity()).getStockDetails();
            setRecycleView(stockDataEntityList);
        }

        btnDonate.setOnSlideCompleteListener(new SlideToActView.OnSlideCompleteListener() {
            @Override
            public void onSlideComplete(@NonNull SlideToActView slideToActView) {
                ((MainActivity) getContext()).navigateToDonationDetailsFragment();
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
