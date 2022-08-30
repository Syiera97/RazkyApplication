package my.edu.utem.ftmk.msmd5113.razkyapplication.Donor.DonateNow;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import my.edu.utem.ftmk.msmd5113.razkyapplication.DataModel.LocationDataEntity;
import my.edu.utem.ftmk.msmd5113.razkyapplication.DataModel.UserComments;
import my.edu.utem.ftmk.msmd5113.razkyapplication.R;

public class DonateNearbyFragment extends Fragment implements OnMapReadyCallback {

    GoogleMap map;
    private LatLngBounds bounds;
    private LatLngBounds.Builder builder;
    List<LocationDataEntity> data = new ArrayList<>();
    @BindView(R.id.user_comment)
    RecyclerView recyclerView;
    @BindView(R.id.rv_layout)
    LinearLayout rvLayout;
    @BindView(R.id.closeBtn)
    TextView closeBtn;
    @BindView(R.id.openBtn)
    TextView openBtn;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private String email;
    private FirestoreRecyclerAdapter adapter;
    LinearLayoutManager linearLayoutManager;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_donate_nearby, container, false);
        ButterKnife.bind(this, view);
        container.removeAllViews();

        initView();
        fetchData();
        return view;
    }

    private void initView() {
        setMarker();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            email = currentUser.getEmail();
        }

        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        clickButton();
        floatingButtonClicked();
    }

    private void floatingButtonClicked() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                final EditText edittext = new EditText(getActivity());
                alert.setTitle("Drop your update");
                alert.setMessage("Updates will be public. Your Razky username will appear with your update.");

                alert.setView(edittext);

                alert.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String update = edittext.getText().toString();
                        saveComment(update);
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });

                alert.show();

            }
        });
    }

    private void clickButton() {
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rvLayout.setVisibility(View.GONE);
                openBtn.setVisibility(View.VISIBLE);
                closeBtn.setVisibility(View.GONE);
            }
        });

        openBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rvLayout.setVisibility(View.VISIBLE);
                openBtn.setVisibility(View.GONE);
                closeBtn.setVisibility(View.VISIBLE);
            }
        });
    }

    private void saveComment(String update) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = sdf.format(c.getTime());

        UserComments userComments = new UserComments(email, strDate, update);
        db = FirebaseFirestore.getInstance();

        CollectionReference dbDonor = db.collection("updateDataFromDonor");
        dbDonor.add(userComments).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getContext(), "Your details has been added to Firebase Firestore", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Fail to add course \n" + e, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setMarker() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map);
        mapFragment.getMapAsync(this);

        LocationDataEntity locationDataEntity = new LocationDataEntity();
        locationDataEntity.setLatitude(3.0679);
        locationDataEntity.setLongitude(101.4902);
        locationDataEntity.setName("Yayasan Kebajikan Kanak-Kanak");
        data.add(locationDataEntity);

        LocationDataEntity locationDataEntity1 = new LocationDataEntity();
        locationDataEntity1.setLatitude(3.0116);
        locationDataEntity1.setLongitude(101.4819);
        locationDataEntity1.setName("Persatuan Kebajikan Jagaan Sri Sai Selangor");
        data.add(locationDataEntity1);

        LocationDataEntity locationDataEntity2 = new LocationDataEntity();
        locationDataEntity2.setLatitude(3.0771);
        locationDataEntity2.setLongitude(101.4876);
        locationDataEntity2.setName("Rumah Puteri Arafiah");
        data.add(locationDataEntity2);

        LocationDataEntity locationDataEntity3 = new LocationDataEntity();
        locationDataEntity3.setLatitude(3.0754);
        locationDataEntity3.setLongitude(101.4822);
        locationDataEntity3.setName("Pusat Jagaan Sayang");
        data.add(locationDataEntity3);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;

        builder = new LatLngBounds.Builder();
        for (int i = 0; i < data.size(); i++) {
            drawMarker(new LatLng(data.get(i).getLatitude(), data.get(i).getLongitude()), data.get(i).getName());
        }
        bounds = builder.build();
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 0);
        map.animateCamera(cu);
    }

    private void drawMarker(LatLng point, String text) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(point).title(text).icon(generateBitmapDescriptorFromRes(getContext(), R.drawable.ic_baseline_location_on));
        map.addMarker(markerOptions);
        builder.include(markerOptions.getPosition());
    }

    public static BitmapDescriptor generateBitmapDescriptorFromRes(
            Context context, int resId) {
        Drawable drawable = ContextCompat.getDrawable(context, resId);
        drawable.setBounds(
                0,
                0,
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    private void fetchData(){
        Query query = FirebaseFirestore.getInstance().collection("updateDataFromDonor");
        FirestoreRecyclerOptions<UserComments> options = new FirestoreRecyclerOptions.Builder<UserComments>().setQuery(query, UserComments.class).build();

        adapter = new FirestoreRecyclerAdapter<UserComments, CommentsHolder>(options) {
            @Override
            protected void onBindViewHolder(CommentsHolder holder, int position, UserComments userComments) {
                holder.username.setText(userComments.getUsername());
                holder.user_comment.setText(userComments.getComment());
                holder.date.setText(userComments.getDateTime());
            }

            @NonNull
            @Override
            public CommentsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(getContext()).inflate(R.layout.adapter_user_updates, parent, false);
                return new CommentsHolder(view);
            }

            @Override
            public void onError(FirebaseFirestoreException e) {
                Log.e("error", e.getMessage());
            }
        };

        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

    public class CommentsHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.username)
        TextView username;
        @BindView(R.id.user_comment)
        TextView user_comment;
        @BindView(R.id.date)
        TextView date;
        public CommentsHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}
