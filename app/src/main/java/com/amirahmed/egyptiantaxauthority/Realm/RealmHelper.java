package com.amirahmed.egyptiantaxauthority.Realm;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealmHelper {

    private Realm realm;

    public RealmHelper(Realm realm) {
        this.realm = realm;
    }

    //WRITE
    public void save(final VehicleItemRealm vehicleItemRealm)
    {
        realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm -> {

            VehicleItemRealm vehicleItem = realm.copyToRealm(vehicleItemRealm);

        });

    }

    //UPDATE
    public void update(final int id,final String Car_Engine,final String Car_Chassis,final String Car_No, String Car_No2, String Car_Model, String Car_Color, String Car_Cylinders, String Car_Maker, String Car_Brand, String Car_Type, String Car_Belongs)
    {
        realm.executeTransactionAsync(realm -> {

            realm = Realm.getDefaultInstance();
            VehicleItemRealm vehicleItemRealm = realm.where(VehicleItemRealm.class).equalTo("Car_ID",id).findFirst();


            assert vehicleItemRealm != null;
            vehicleItemRealm.setCar_Engine(Car_Engine);
            vehicleItemRealm.setCar_Chassis(Car_Chassis);
            vehicleItemRealm.setCar_No(Car_No);
            vehicleItemRealm.setCar_No2(Car_No2);
            vehicleItemRealm.setCar_Model(Car_Model);
            vehicleItemRealm.setCar_Color(Car_Color);
            vehicleItemRealm.setCar_Cylinders(Car_Cylinders);
            vehicleItemRealm.setCar_Maker(Car_Maker);
            vehicleItemRealm.setCar_Brand(Car_Brand);
            vehicleItemRealm.setCar_Type(Car_Type);
            vehicleItemRealm.setCar_Belongs(Car_Belongs);

        });


    }

    //READ
    public ArrayList<VehicleItemRealm> retrieve()
    {
        realm = Realm.getDefaultInstance();
        ArrayList<VehicleItemRealm> vehicles = new ArrayList<>();
        RealmResults<VehicleItemRealm> vehicleItem = realm.where(VehicleItemRealm.class).findAll();

        for(VehicleItemRealm v:vehicleItem)
        {
            vehicles.add(new VehicleItemRealm(v.getCar_ID(),v.getCar_Engine(),v.getCar_Chassis(),v.getCar_No(),v.getCar_No2(),v.getCar_Model(),v.getCar_Color(),v.getCar_Cylinders(),v.getCar_Maker(),v.getCar_Brand(),v.getCar_Type(),v.getCar_Belongs()));

        }

        return vehicles;
    }

    //READ FILTERED
    public ArrayList<VehicleItemRealm> retrieveFiltered(String key,String value)
    {
        realm = Realm.getDefaultInstance();
        ArrayList<VehicleItemRealm> vehicles = new ArrayList<>();
        RealmResults<VehicleItemRealm> vehicleItem = realm.where(VehicleItemRealm.class).equalTo(key, value).findAll();

        for(VehicleItemRealm v:vehicleItem)
        {
            vehicles.add(new VehicleItemRealm(v.getCar_ID(),v.getCar_Engine(),v.getCar_Chassis(),v.getCar_No(),v.getCar_No2(),v.getCar_Model(),v.getCar_Color(),v.getCar_Cylinders(),v.getCar_Maker(),v.getCar_Brand(),v.getCar_Type(),v.getCar_Belongs()));

        }

        return vehicles;
    }
}