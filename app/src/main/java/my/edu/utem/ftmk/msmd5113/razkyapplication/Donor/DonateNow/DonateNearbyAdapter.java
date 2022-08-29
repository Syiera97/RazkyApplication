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
import my.edu.utem.ftmk.msmd5113.razkyapplication.DataModel.OrphanageDataEntity;
import my.edu.utem.ftmk.msmd5113.razkyapplication.DataModel.UserComments;
import my.edu.utem.ftmk.msmd5113.razkyapplication.R;

public class DonateNearbyAdapter extends RecyclerView.Adapter<DonateNearbyAdapter.DonateNearbyAdapterViewHolder>{

    private List<UserComments> userCommentsList;
    private Context context;

    public DonateNearbyAdapter(Context context, List<UserComments> userComments) {
        this.context = context;
        this.userCommentsList = userComments;
    }

    public void setAdapter(List<UserComments> userComments){
        this.userCommentsList = userComments;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DonateNearbyAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_user_updates, parent, false);
        return new DonateNearbyAdapter.DonateNearbyAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonateNearbyAdapterViewHolder holder, int position) {
        holder.username.setText(userCommentsList.get(position).getUsername());
        holder.user_comment.setText(userCommentsList.get(position).getComment());
        holder.date.setText(userCommentsList.get(position).getDateTime());
    }

    @Override
    public int getItemCount() {
        return userCommentsList.size();
    }

    public class DonateNearbyAdapterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.username)
        TextView username;
        @BindView(R.id.user_comment)
        TextView user_comment;
        @BindView(R.id.date)
        TextView date;
        public DonateNearbyAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
