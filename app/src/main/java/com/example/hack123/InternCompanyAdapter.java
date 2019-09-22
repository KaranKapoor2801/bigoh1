package com.example.hack123;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class InternCompanyAdapter extends RecyclerView.Adapter<InternCompanyAdapter.InternViewHolder> {

    Context ctx;
    ArrayList<String> internCompanyUid;
    ArrayList<InternCompanyModel> internCompanyModelArrayList;
    DatabaseReference interestedCompanyRef;
    String currentUid;

    public InternCompanyAdapter(Context ctx, ArrayList<String> internCompanyUid, ArrayList<InternCompanyModel> internCompanyModelArrayList) {
        this.ctx = ctx;
        this.internCompanyUid = internCompanyUid;
        this.internCompanyModelArrayList = internCompanyModelArrayList;
        interestedCompanyRef=FirebaseDatabase.getInstance().getReference().child("intereted_students").child("intern");
        currentUid= FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    @NonNull
    @Override
    public InternCompanyAdapter.InternViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = (View) LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.intern_companies,viewGroup, false);

        return new InternViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InternCompanyAdapter.InternViewHolder internViewHolder, int i) {
        internViewHolder.nameCompany.setText(internCompanyModelArrayList.get(i).getName());
        internViewHolder.stipendCompany.setText(internCompanyModelArrayList.get(i).getStipend());
        Toast.makeText(ctx, Integer.toString(internCompanyModelArrayList.size()), Toast.LENGTH_SHORT).show();
        internViewHolder.setListener(i);
    }

    @Override
    public int getItemCount() {
        return internCompanyUid.size();
    }

    class InternViewHolder extends RecyclerView.ViewHolder{

        TextView nameCompany,stipendCompany;
        Button applyBtn;

        public InternViewHolder(@NonNull View itemView) {
            super(itemView);

            nameCompany=(TextView) itemView.findViewById(R.id.name_company_textview);
            stipendCompany=(TextView) itemView.findViewById(R.id.stipend_company_textview);
            applyBtn=(Button)itemView.findViewById(R.id.apply_company_btn);

        }
        public void setListener(final int i){
            applyBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String key=interestedCompanyRef.push().getKey();
                    HashMap<String,String> hashMap=new HashMap<>();
                    hashMap.put("uid",currentUid);
                    hashMap.put("company",internCompanyModelArrayList.get(i).getName());
                    interestedCompanyRef.child(key).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(ctx, "Company Added", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }
}
