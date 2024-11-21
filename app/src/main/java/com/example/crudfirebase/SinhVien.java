package com.example.crudfirebase;

public class SinhVien {
    String id,ten,namSinh,sdt,diaChi,maLop;

    public SinhVien() {
    }

    public SinhVien(String id, String ten, String namSinh, String sdt, String diaChi, String maLop) {
        this.id = id;
        this.ten = ten;
        this.namSinh = namSinh;
        this.sdt = sdt;
        this.diaChi = diaChi;
        this.maLop = maLop;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getNamSinh() {
        return namSinh;
    }

    public void setNamSinh(String namSinh) {
        this.namSinh = namSinh;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getMaLop() {
        return maLop;
    }

    public void setMaLop(String maLop) {
        this.maLop = maLop;
    }
}
