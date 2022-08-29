package my.edu.utem.ftmk.msmd5113.razkyapplication.Donor.DonateNow;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
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
import com.google.firebase.firestore.SetOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    List<UserComments> userCommentsList = new ArrayList<>();
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

    private DonateNearbyAdapter listAdapter;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private String email;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_donate_nearby, container, false);
        ButterKnife.bind(this, view);
        container.removeAllViews();

        initView();
        return view;
    }

    private void initView() {
        setMarker();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            email = currentUser.getEmail();
        }

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

    private void saveComment(String update) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = sdf.format(c.getTime());

        UserComments userComments = new UserComments(email, strDate, update);
        db = FirebaseFirestore.getInstance();

        Map<String, Object> docData = new HashMap<>();
        docData.put("details", Arrays.asList(userComments));
        db.collection("updateDataFromDonor").document("fdJErb0Dr1JnFTomSNMt").set(docData, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getContext(), "Your details has been added to Firebase Firestore", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Fail to add course \n" + e, Toast.LENGTH_SHORT).show();
            }
        });


//        UserComments userComments1 = new UserComments(email, strDate, update);
//        userCommentsList.add(userComments1);
//
//        CollectionReference child = db.collection("updateDataFromDonor");
//        DocumentReference docRef = child.document("fdJErb0Dr1JnFTomSNMt");
//        docRef.update("details")


//        CollectionReference dbDonor = db.collection("updateDataFromDonor");
//        dbDonor.add(userComments).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//            @Override
//            public void onSuccess(DocumentReference documentReference) {
//                Toast.makeText(getContext(), "Your details has been added to Firebase Firestore", Toast.LENGTH_SHORT).show();
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(getContext(), "Fail to add course \n" + e, Toast.LENGTH_SHORT).show();
//            }
//        });
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

//        UserComments userComments = new UserComments();
//        userComments.setComment("Rumah Kasih Syahirah required your help peeps.. Do donate on the neccssary item from the apps. There is one more new orphanage near high school, required someone help to guide the caretaker to register into the app. Thank you <3");
//        userComments.setDateTime("public 9:51 AM");
//        userComments.setUsername("Nur Syahirah");
//        userCommentsList.add(userComments);
//        UserComments userComments1 = new UserComments();
//        userComments1.setComment("Rumah Kasih Syahirah required your help peeps.. Do donate on the neccssary item from the apps. There is one more new orphanage near high school, required someone help to guide the caretaker to register into the app. Thank you <3");
//        userComments1.setDateTime("public 9:51 AM");
//        userComments1.setUsername("Nur Syahirah");
//        userCommentsList.add(userComments1);
//        setRecyclerView(userCommentsList);
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

    private void setRecyclerView(List<UserComments> userComments) {
        if(userComments != null) {
            if (listAdapter == null) {
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setHasFixedSize(true);
                listAdapter = new DonateNearbyAdapter(getContext(), userComments);
            } else {
                listAdapter.setAdapter(userComments);
            }
            recyclerView.setAdapter(listAdapter);
        }
    }

}
