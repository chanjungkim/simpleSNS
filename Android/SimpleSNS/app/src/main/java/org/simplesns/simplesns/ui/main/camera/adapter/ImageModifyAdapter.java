package org.simplesns.simplesns.ui.main.camera.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.simplesns.simplesns.R;
import org.simplesns.simplesns.ui.main.camera.model.ImageModifyMenu;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ImageModifyAdapter extends RecyclerView.Adapter<ImageModifyAdapter.ViewHolder> {
    List<ImageModifyMenu> modifyMenuList;
    private LayoutInflater mLayoutInflater;
    Context context;

    public ImageModifyAdapter(Context context, List<ImageModifyMenu> modifyMenuList) {
        this.context = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.modifyMenuList = modifyMenuList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.image_modify_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.ivSymbol.setImageBitmap(modifyMenuList.get(position).getSymbol());
        holder.tvText.setText(modifyMenuList.get(position).getText());


        holder.ivSymbol.setOnClickListener(v -> {
            Toast.makeText(context
                    ,"color code" + modifyMenuList.get(position).getColorCode()
                    , Toast.LENGTH_SHORT).show();
            // todo call color function !!
        });
    }

    @Override
    public int getItemCount() {
        return modifyMenuList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView   ivSymbol;
        TextView    tvText;
        ViewHolder (View itemView) {
            super(itemView);
            ivSymbol =itemView.findViewById(R.id.symbol);
            tvText = itemView.findViewById(R.id.text);

        }
    }
}
