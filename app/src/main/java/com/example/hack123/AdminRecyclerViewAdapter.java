package com.example.hack123;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AdminRecyclerViewAdapter extends RecyclerView.Adapter<AdminRecyclerViewAdapter.AdminViewHolder> {
    Context ctx;
    ArrayList<UserInfoModel>userInfoModelArrayList;
    ArrayList<String>userUidArrayList;

    public AdminRecyclerViewAdapter(Context ctx, ArrayList<UserInfoModel>userInfoList,ArrayList<String>userUidArrayList){
        this.ctx=ctx;
        this.userInfoModelArrayList=userInfoList;
        this.userUidArrayList=userUidArrayList;
    }

    @NonNull
    @Override
    public AdminRecyclerViewAdapter.AdminViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = (View) LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_recyclerview,viewGroup, false);

        return new AdminViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminRecyclerViewAdapter.AdminViewHolder adminViewHolder, int i) {
        adminViewHolder.nameTextView.setText(userInfoModelArrayList.get(i).getName());
        adminViewHolder.setListener(i);

    }

    @Override
    public int getItemCount() {
        return userInfoModelArrayList.size();
    }


    public class AdminViewHolder  extends RecyclerView.ViewHolder{
        TextView nameTextView;
        public AdminViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView=(TextView)itemView.findViewById(R.id.name_textView);
        }
        public void setListener(final int i){
            nameTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent verifyIntent=new Intent(ctx,VerifyDocumentActivity.class);
                    verifyIntent.putExtra("doc_url",userInfoModelArrayList.get(i).getDocURl());
                    verifyIntent.putExtra("current_uid",userUidArrayList.get(i));
                    ctx.startActivity(verifyIntent);
                }
            });
        }
    }
}
