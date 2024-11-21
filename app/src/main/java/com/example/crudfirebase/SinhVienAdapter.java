package com.example.crudfirebase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SinhVienAdapter extends RecyclerView.Adapter<SinhVienAdapter.SinhVienViewHolder> {
    Context context;
    ArrayList<SinhVien> sinhVienArrayList;
    OnItemClickListener onItemClickListener;

    public SinhVienAdapter(Context context, ArrayList<SinhVien> sinhVienArrayList) {
        this.context = context;
        this.sinhVienArrayList = sinhVienArrayList;
    }

    @NonNull
    @Override
    public SinhVienViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sinhvien_list_item, parent, false);
        return new SinhVienViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SinhVienViewHolder holder, int position) {
        holder.ten.setText(sinhVienArrayList.get(position).getTen());
        holder.namSinh.setText(sinhVienArrayList.get(position).getNamSinh());
        holder.sdt.setText(sinhVienArrayList.get(position).getSdt());
        holder.diaChi.setText(sinhVienArrayList.get(position).getDiaChi());
        holder.maLop.setText(sinhVienArrayList.get(position).getMaLop());
        holder.itemView.setOnClickListener(v -> onItemClickListener.onClick(sinhVienArrayList.get(position)));

    }

    @Override
    public int getItemCount() {
        return sinhVienArrayList.size();
    }

    public static class SinhVienViewHolder extends RecyclerView.ViewHolder {
        TextView ten, namSinh, sdt, diaChi, maLop;
        public SinhVienViewHolder(@NonNull View itemView) {
            super(itemView);
            ten = itemView.findViewById(R.id.list_item_ten);
            namSinh = itemView.findViewById(R.id.list_item_namsinh);
            sdt = itemView.findViewById(R.id.list_item_sdt);
            diaChi = itemView.findViewById(R.id.list_item_diachi);
            maLop = itemView.findViewById(R.id.list_item_malop);

        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onClick(SinhVien sinhVien);
    }
}
