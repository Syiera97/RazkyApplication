package my.edu.utem.ftmk.msmd5113.razkyapplication.Caretaker.Inventory.AddProduct;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import my.edu.utem.ftmk.msmd5113.razkyapplication.Caretaker.Inventory.InventoryAdapter;
import my.edu.utem.ftmk.msmd5113.razkyapplication.DataModel.StockDetails;
import my.edu.utem.ftmk.msmd5113.razkyapplication.Donor.DonateNow.StockDataEntity;
import my.edu.utem.ftmk.msmd5113.razkyapplication.R;

public class AddProductAdapter extends RecyclerView.Adapter<AddProductAdapter.AddProductViewHolder>{

    private List<StockDetails> stockDataEntityList;
    private Context context;

    public AddProductAdapter(Context context, List<StockDetails> stockDataEntities) {
        this.context = context;
        this.stockDataEntityList = stockDataEntities;
    }

    public void setAdapter(List<StockDetails> stockDataEntities){
        this.stockDataEntityList = stockDataEntities;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
      public AddProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_inventory, parent, false);
        return new AddProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddProductViewHolder holder, int position) {
        holder.imageName.setText(stockDataEntityList.get(position).getProductName());
        Glide.with(context).load(stockDataEntityList.get(position).getProductImage()).into(holder.imageItem);
    }

    @Override
    public int getItemCount() {
        return stockDataEntityList.size();
    }

    public class AddProductViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.progressBar)
        ProgressBar progressBar;
        @BindView(R.id.progress_value)
        TextView progressVal;
        @BindView(R.id.imageName)
        TextView imageName;
        @BindView(R.id.imageItem)
        ImageView imageItem;


        public AddProductViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            progressBar.setVisibility(View.GONE);
            progressVal.setVisibility(View.GONE);
        }
    }
}
