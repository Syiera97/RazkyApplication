package my.edu.utem.ftmk.msmd5113.razkyapplication.Donor.DonateNow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import my.edu.utem.ftmk.msmd5113.razkyapplication.DataModel.StockDetails;
import my.edu.utem.ftmk.msmd5113.razkyapplication.R;

public class DonateNowDetailsAdapter extends RecyclerView.Adapter<DonateNowDetailsAdapter.DonateNowDetailsAdapterViewHolder>{
    private List<StockDetails> stockDataEntityList;
    private Context context;

    public DonateNowDetailsAdapter(Context context, List<StockDetails> stockDataEntities) {
        this.context = context;
        this.stockDataEntityList = stockDataEntities;
    }

    public void setAdapter(List<StockDetails> stockDataEntities){
        this.stockDataEntityList = stockDataEntities;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DonateNowDetailsAdapter.DonateNowDetailsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_donate_details, parent, false);
        return new DonateNowDetailsAdapter.DonateNowDetailsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonateNowDetailsAdapter.DonateNowDetailsAdapterViewHolder holder, int i) {
        if(stockDataEntityList != null){
            StockDetails data = stockDataEntityList.get(i);
            holder.tvItem.setText(data.getProductName());
            holder.tvCurrentStock.setText(String.valueOf(data.getCurrentStock()));
            holder.tvMinStock.setText(String.valueOf(data.getMinStock()));
            holder.tvPrice.setText(String.valueOf(data.getPrice()));
        }
    }

    @Override
    public int getItemCount() {
        return stockDataEntityList.size();
    }

    public class DonateNowDetailsAdapterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item)
        TextView tvItem;
        @BindView(R.id.price_item)
        TextView tvPrice;
        @BindView(R.id.current_stock_val)
        TextView tvCurrentStock;
        @BindView(R.id.min_stock_val)
        TextView tvMinStock;

        public DonateNowDetailsAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
