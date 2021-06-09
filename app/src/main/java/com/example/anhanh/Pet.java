package com.example.anhanh;

public class Pet {
    private int idPet;
    private String namePet;

    public Pet(int idPet, String namePet) {
        this.idPet = idPet;
        this.namePet = namePet;
    }

    public int getIdPet() {
        return idPet;
    }

    public void setIdPet(int idPet) {
        this.idPet = idPet;
    }

    public String getNamePet() {
        return namePet;
    }

    public void setNamePet(String namePet) {
        this.namePet = namePet;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "idPet=" + idPet +
                ", namePet='" + namePet + '\'' +
                '}';
    }
}
