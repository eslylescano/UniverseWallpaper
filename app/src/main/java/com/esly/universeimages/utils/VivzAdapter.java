package com.esly.universeimages.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.esly.universeimages.R;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

/**
 * Created by Esly on 23/04/2015.
 */
public class VivzAdapter extends RecyclerView.Adapter<VivzAdapter.MyViewHolder> {

    private  LayoutInflater inflater;

    List<Information> data = Collections.emptyList();

    private Context context;



    public VivzAdapter(Context context,List<Information> data ){
        inflater = LayoutInflater.from(context);
        this.data = data;
        this.context = context;
    }





    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.custom_row,parent,false);
      //  Log.d("VIVZ","onCreateHolder called");
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

       final Information current = data.get(position);

      //  Log.d("VIVZ","onBindViewHolder called "+position);
        holder.title.setText(current.title);

        //holder.icon.setImageResource(current.iconId);
        Picasso.with(context).load(current.iconId).into(holder.icon);

        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(current.url));
                context.startActivity(intent);

            }
        });

        holder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,current.title,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(current.url));
                context.startActivity(intent);

            }
        });



    }

    @Override
    public int getItemCount() {


        return data.size();
    }



    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView icon;

        public MyViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.listText);
            icon = (ImageView)itemView.findViewById(R.id.listIcon);


//

        }



    }




}
