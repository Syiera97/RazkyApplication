package my.edu.utem.ftmk.msmd5113.razkyapplication.Caretaker.Inventory.AddProduct;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import my.edu.utem.ftmk.msmd5113.razkyapplication.DataModel.StockDetails;
import my.edu.utem.ftmk.msmd5113.razkyapplication.R;

public class AddProductAdapter extends RecyclerView.Adapter<AddProductAdapter.AddProductViewHolder>{

    private List<StockDetails> stockDataEntityList;
    private Context context;
    int currentStock, minStock, stockRequire;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_add_product, parent, false);
        return new AddProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddProductViewHolder holder, int position) {
        holder.imageName.setText(stockDataEntityList.get(position).getProductName());
        Glide.with(context).load(stockDataEntityList.get(position).getProductImage()).into(holder.imageItem);
    }

    public List<StockDetails> getStockDetails(){
        List<StockDetails> stockDetails = new ArrayList<>();

        for(StockDetails data : stockDataEntityList){
            StockDetails details = new StockDetails();
            details.setProductName(data.getProductName());
            details.setProductImage(data.getProductName());
            details.setMinStock(data.getMinStock());
            details.setCurrentStock(data.getCurrentStock());
            details.setCurrentStock(data.getCurrentStock());
            stockDetails.add(details);
        }
        return stockDetails;
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
        @BindView(R.id.current_stock_val)
        EditText current_stock_val;
        @BindView(R.id.min_stock_val)
        EditText min_stock_val;
        @BindView(R.id.imageItem)
        ImageView imageItem;


        public AddProductViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            current_stock_val.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    stockDataEntityList.get(getAdapterPosition()).setCurrentStock(charSequence.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {
//                    currentStock = Integer.parseInt(String.valueOf(editable));
//                    calculateStockRequire();
                }
            });


            min_stock_val.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    stockDataEntityList.get(getAdapterPosition()).setMinStock(charSequence.toString());

                }

                @Override
                public void afterTextChanged(Editable editable) {
//                    minStock = Integer.parseInt(String.valueOf(editable));
//                    stockDataEntityList.get(getAdapterPosition()).setMinStock(String.valueOf(editable));
//                    calculateStockRequire();
                }
            });
        }

        private void calculateStockRequire() {
            stockRequire = minStock - currentStock;
            stockDataEntityList.get(getAdapterPosition()).setStockRequire(String.valueOf(stockRequire));
            notifyDataSetChanged();
        }
    }
}
