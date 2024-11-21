package com.example.crudfirebase;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class SinhVienActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sinh_vien);

        FirebaseApp.initializeApp(SinhVienActivity.this);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        TextView emptyListText = findViewById(R.id.empty_list_text);

        FloatingActionButton add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View view1 = LayoutInflater.from(SinhVienActivity.this).inflate(R.layout.add_sinhvien_dialog, null);
                TextInputLayout tenLayout = view1.findViewById(R.id.tenLayout);
                TextInputLayout namSinhLayout = view1.findViewById(R.id.namsinhLayout);
                TextInputLayout sdtLayout = view1.findViewById(R.id.sdtLayout);
                TextInputLayout diaChiLayout = view1.findViewById(R.id.diachiLayout);
                TextInputLayout maLopLayout = view1.findViewById(R.id.malopLayout);
                TextInputEditText tenET, namSinhET, sdtET, diaChiET, maLopET;
                tenET = view1.findViewById(R.id.tenET);
                namSinhET = view1.findViewById(R.id.namsinhET);
                sdtET = view1.findViewById(R.id.sdtET);
                diaChiET = view1.findViewById(R.id.diachiET);
                maLopET = view1.findViewById(R.id.malopET);

                AlertDialog dialog = new AlertDialog.Builder(SinhVienActivity.this)
                       .setTitle("Add")
                       .setView(view1)
                       .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {
                                if(Objects.requireNonNull(tenET.getText()).toString().isEmpty()){
                                    tenLayout.setError("Vui lòng nhập tên");
                                }else if(Objects.requireNonNull(namSinhET.getText()).toString().isEmpty()){
                                    namSinhLayout.setError("Vui lòng nhập năm sinh");
                                }else{
                                    ProgressDialog progressDialog = new ProgressDialog(SinhVienActivity.this);
                                    progressDialog.setMessage("Loading...");
                                    progressDialog.show();
                                    SinhVien sinhVien = new SinhVien();
                                    sinhVien.setTen(tenET.getText().toString());
                                    sinhVien.setNamSinh(namSinhET.getText().toString());
                                    sinhVien.setSdt(sdtET.getText().toString());
                                    sinhVien.setDiaChi(diaChiET.getText().toString());
                                    sinhVien.setMaLop(maLopET.getText().toString());
                                    database.getReference().child("sinhvien")
                                            .push().setValue(sinhVien).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {

                                                    dialogInterface.dismiss();
                                                    Toast.makeText(SinhVienActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {

                                                    dialogInterface.dismiss();
                                                        Toast.makeText(SinhVienActivity.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                           }
                       })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                       .create();
        dialog.show();
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        database.getReference().child("sinhvien").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<SinhVien> arrayList = new ArrayList<>();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    SinhVien sinhVien = dataSnapshot.getValue(SinhVien.class);
                    Objects.requireNonNull(sinhVien).setId(dataSnapshot.getKey());
                    arrayList.add(sinhVien);

                }
                if(arrayList.isEmpty()){
                    emptyListText.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }else{
                    emptyListText.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
                SinhVienAdapter adapter = new SinhVienAdapter(SinhVienActivity.this, arrayList);
                recyclerView.setAdapter(adapter);

                adapter.setOnItemClickListener(new SinhVienAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(SinhVien sinhVien) {
                        View view = LayoutInflater.from(SinhVienActivity.this).inflate(R.layout.add_sinhvien_dialog, null);
                        TextInputLayout tenLayout, namSinhLayout, sdtLayout, diaChiLayout, maLopLayout;
                        TextInputEditText tenET, namSinhET, sdtET, diaChiET, maLopET;
                        tenLayout = view.findViewById(R.id.tenLayout);
                        namSinhLayout = view.findViewById(R.id.namsinhLayout);
                        sdtLayout = view.findViewById(R.id.sdtLayout);
                        diaChiLayout = view.findViewById(R.id.diachiLayout);
                        maLopLayout = view.findViewById(R.id.malopLayout);

                        tenET = view.findViewById(R.id.tenET);
                        namSinhET = view.findViewById(R.id.namsinhET);
                        sdtET = view.findViewById(R.id.sdtET);
                        diaChiET = view.findViewById(R.id.diachiET);
                        maLopET = view.findViewById(R.id.malopET);

                        tenET.setText(sinhVien.getTen());
                        namSinhET.setText(sinhVien.getNamSinh());
                        sdtET.setText(sinhVien.getSdt());
                        diaChiET.setText(sinhVien.getDiaChi());
                        maLopET.setText(sinhVien.getMaLop());

                        ProgressDialog progressDialog = new ProgressDialog(SinhVienActivity.this);


                        AlertDialog alertDialog = new AlertDialog.Builder(SinhVienActivity.this)
                                .setTitle("Edit")
                                .setView(view)
                                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if(Objects.requireNonNull(tenET.getText()).toString().isEmpty()){
                                            tenLayout.setError("Vui lòng nhập tên");
                                        }else if(Objects.requireNonNull(namSinhET.getText()).toString().isEmpty()){
                                            namSinhLayout.setError("Vui lòng nhập năm sinh");
                                        }else{

                                            progressDialog.setMessage("Saving...");
                                            progressDialog.show();
                                            SinhVien sinhVien1 = new SinhVien();
                                            sinhVien1.setTen(tenET.getText().toString());
                                            sinhVien1.setNamSinh(namSinhET.getText().toString());
                                            sinhVien1.setSdt(sdtET.getText().toString());
                                            sinhVien1.setDiaChi(diaChiET.getText().toString());
                                            sinhVien1.setMaLop(maLopET.getText().toString());
                                            database.getReference().child("sinhvien")
                                                    .child(sinhVien.getId()).setValue(sinhVien1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void unused) {

                                                            progressDialog.dismiss();
                                                            Toast.makeText(SinhVienActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {

                                                            progressDialog.dismiss();
                                                            Toast.makeText(SinhVienActivity.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                        }
                                    }
                                }).setNeutralButton("close", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                }).setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        progressDialog.setTitle("Deleting...");
                                        progressDialog.show();
                                        database.getReference().child("sinhvien").child(sinhVien.getId()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                progressDialog.dismiss();
                                                Toast.makeText(SinhVienActivity.this,"Deleted successfully",Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                progressDialog.dismiss();
                                            }
                                        });
                                    }
                                }).create();
                        alertDialog.show();


                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}