package my.edu.utem.ftmk.msmd5113.razkyapplication.Donor.MyProfile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import my.edu.utem.ftmk.msmd5113.razkyapplication.Donor.DonateNow.DonateNowDetailsAdapter;
import my.edu.utem.ftmk.msmd5113.razkyapplication.Donor.DonateNow.StockDataEntity;
import my.edu.utem.ftmk.msmd5113.razkyapplication.R;
import my.edu.utem.ftmk.msmd5113.razkyapplication.SplashActivity;
import my.edu.utem.ftmk.msmd5113.razkyapplication.SplashCaretakerActivity;

public class MyProfileFragment extends Fragment {

    @BindView(R.id.rv_history_list)
    RecyclerView recyclerView;
    @BindView(R.id.scrollView)
    NestedScrollView nestedScrollView;
    @BindView(R.id.switch_persona)
    TextView tvSwitchRole;

    MyProfileAdapter myProfileAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_profile, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setNestedScrollingEnabled(false);
        nestedScrollView.setNestedScrollingEnabled(true);

        List<DonationHistoryDataEntity> donationHistoryDataEntityList = new ArrayList<>();
        DonationHistoryDataEntity donationHistoryDataEntity = new DonationHistoryDataEntity();
        donationHistoryDataEntity.setOrphanageNamee("Rumah Amal Budi Bestari");
        donationHistoryDataEntity.setDonatedDate("Donated on 6 Jun 12:49 (MYT)");
        donationHistoryDataEntityList.add(donationHistoryDataEntity);
        setRecycleView(donationHistoryDataEntityList);

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
