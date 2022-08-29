package my.edu.utem.ftmk.msmd5113.razkyapplication.Caretaker.Inventory;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import my.edu.utem.ftmk.msmd5113.razkyapplication.DataModel.StockDetails;
import my.edu.utem.ftmk.msmd5113.razkyapplication.Donor.DonateNow.StockDataEntity;
import my.edu.utem.ftmk.msmd5113.razkyapplication.MainCaretakerActivity;
import my.edu.utem.ftmk.msmd5113.razkyapplication.R;

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.InventoryViewHolder> {

    private List<StockDetails> stockDataEntityList;
    private Context context;
    private int progressStatus = 0;
    private Handler handler = new Handler();
    private boolean isEditMode = false;


    public InventoryAdapter(Context context, List<StockDetails> stockDataEntities) {
        this.context = context;
        this.stockDataEntityList = stockDataEntities;
    }

    public void setAdapter(List<StockDetails> stockDataEntities){
        this.stockDataEntityList = stockDataEntities;
        notifyDataSetChanged();
    }

    public boolean setEditMode(){
        if(isEditMode){
            return isEditMode = false;
        } else {
            return isEditMode = true;
        }
    }

    @NonNull
    @Override
    public InventoryAdapter.InventoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_inventory, parent, false);
        return new InventoryAdapter.InventoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InventoryAdapter.InventoryViewHolder holder, int position) {
        if(isEditMode){
            holder.counterLayout.setVisibility(View.VISIBLE);
        }else{
            holder.counterLayout.setVisibility(View.GONE);
        }

        holder.imageName.setText(stockDataEntityList.get(position).getProductName());
        Glide.with(context).load(stockDataEntityList.get(position).getProductImage()).into(holder.imageItem);

        new Thread(new Runnable() {
            public void run() {
                while (progressStatus < 10) {
                    progressStatus += 1;
                    // Update the progress bar and display the
                    //current value in the text view
                    handler.post(new Runnable() {
                        public void run() {
                            holder.progressBar.setProgress(progressStatus);
                            holder.progressVal.setText(progressStatus+"/"+holder.progressBar.getMax());
                        }
                    });
                    try {
                        // Sleep for 200 milliseconds.
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    public int getItemCount() {
        return stockDataEntityList.size();
    }

    public class InventoryViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.int_number)
        TextView displayInteger;
        @BindView(R.id.decreaseBtn)
        TextView decreaseBtn;
        @BindView(R.id.increaseBtn)
        TextView increaseBtn;
        @BindView(R.id.progressBar)
        ProgressBar progressBar;
        @BindView(R.id.progress_value)
        TextView progressVal;
        @BindView(R.id.counterLayout)
        LinearLayout counterLayout;
        @BindView(R.id.imageName)
        TextView imageName;
        @BindView(R.id.imageItem)
        ImageView imageItem;
//        @BindView(R.id.imageName)
//        TextView price;

        public InventoryViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            increaseBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    increaseInteger(view);
                }
            });

            decreaseBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    decreaseInteger(view);
                }
            });
        }

        int minteger = 0;

        public void increaseInteger(View view) {
            minteger = minteger + 1;
            display(minteger);

        }

        public void decreaseInteger(View view) {
            minteger = minteger - 1;
            display(minteger);
        }

        private void display(int number) {
            displayInteger.setText("" + number);
        }
    }
}
