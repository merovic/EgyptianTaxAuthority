package com.amirahmed.egyptiantaxauthority;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;

import com.amirahmed.egyptiantaxauthority.Realm.CarListRealmAdapter;
import com.amirahmed.egyptiantaxauthority.Realm.RealmHelper;
import com.amirahmed.egyptiantaxauthority.Realm.VehicleItemRealm;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class CarsListActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private static final String TAG = "123";
    RecyclerView rv;
    List<VehicleItem> vehicleItemList;
    List<VehicleItemRealm> vehicleItemList2;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    CarsListAdapter adapter;
    CarListRealmAdapter adapter2;

    Spinner carmakers,carbrand;

    List<String> makers = new ArrayList<>();
    List<String> brands = new ArrayList<>();

    ArrayAdapter<String> adapter4;

    String maker,brand;

    Button addbutton;

    Switch aSwitch;

    LinearLayout filter;

    //----------------

    Realm realm;
    RealmHelper helper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carslist);

        aSwitch = findViewById(R.id.switchitem);
        filter = findViewById(R.id.filterlayout);

        aSwitch.setChecked(false);
        filter.setVisibility(View.GONE);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                if (bChecked) {
                    filter.setVisibility(View.VISIBLE);
                } else {
                    //vehicleItemList.clear(); //clear list
                    //adapter.notifyDataSetChanged();
                    //filter.setVisibility(View.GONE);
                    //getData();

                    vehicleItemList2.clear(); //clear list
                    adapter2.notifyDataSetChanged();
                    filter.setVisibility(View.GONE);
                    getRealmData();
                }
            }
        });

        vehicleItemList = new ArrayList<>();

        rv = findViewById(R.id.rv);

        rv.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(mLayoutManager);

        //---------------

        //SETUP REALM
        // initialize Realm
        Realm.init(this);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .name("vehicles.realm")
                .schemaVersion(0)
                .build();
        Realm.setDefaultConfiguration(realmConfig);

        //---------------

        getRealmData();
        //getData();
        //getFilteredData();

        carmakers = findViewById(R.id.carmakerspinner);
        carbrand = findViewById(R.id.carbrandspinner);

        addbutton = findViewById(R.id.addbutton);

        makers.add("اختر ماركة السيارة");
        makers.add("شيفروليه");
        makers.add("نيسان");
        makers.add("بيجو");
        makers.add("نصر");
        makers.add("مرسيدس");
        makers.add("كل دوبل كابينة");

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, makers);

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        carmakers.setAdapter(adapter2);
        carmakers.setOnItemSelectedListener(this);

        adapter2.notifyDataSetChanged();

        adapter4 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, brands);

        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        carbrand.setAdapter(adapter4);
        carbrand.setOnItemSelectedListener(this);

        adapter4.notifyDataSetChanged();

        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //vehicleItemList.clear(); //clear list
                //adapter.notifyDataSetChanged();
                //getFilteredData();

                vehicleItemList2.clear(); //clear list
                adapter2.notifyDataSetChanged();
                getRealmDataFiltered();
            }
        });


    }



    @Override
    protected void onRestart() {
        super.onRestart();
        adapter2.notifyDataSetChanged();
        rv.invalidate();
    }

    private void getRealmData() {

        //RETRIEVE
        helper = new RealmHelper(realm);
        vehicleItemList2 = helper.retrieve();
        adapter2 = new CarListRealmAdapter(vehicleItemList2);
        rv.setAdapter(adapter2);

    }

    private void getRealmDataFiltered() {

        //RETRIEVE
        helper = new RealmHelper(realm);
        vehicleItemList2 = helper.retrieveFiltered("Car_Brand",brand);
        adapter2 = new CarListRealmAdapter(vehicleItemList2);
        rv.setAdapter(adapter2);

    }

    public void getData()
    {

        db.collection("vehicles")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {

                                VehicleItem vehicleItem = new VehicleItem(document.getId()
                                        ,document.getString("Car_Engine")
                                        ,document.getString("Car_Chassis")
                                        ,document.getString("Car_No")
                                        ,document.getString("Car_No2")
                                        ,document.getString("Car_Model")
                                        ,document.getString("Car_Color")
                                        ,document.getString("Car_Cylinders")
                                        ,document.getString("Car_Maker")
                                        ,document.getString("Car_Brand")
                                        ,document.getString("Car_Type")
                                        ,document.getString("Car_Belongs"));

                                vehicleItemList.add(vehicleItem);
                            }

                            adapter = new CarsListAdapter(vehicleItemList);
                            rv.setAdapter(adapter);

                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });




    }

    public void  getFilteredData(){

        CollectionReference vehiclesRef = db.collection("vehicles");

        Query query = vehiclesRef.whereEqualTo("Car_Brand", brand);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {

                                VehicleItem vehicleItem = new VehicleItem(document.getId()
                                        ,document.getString("Car_Engine")
                                        ,document.getString("Car_Chassis")
                                        ,document.getString("Car_No")
                                        ,document.getString("Car_No2")
                                        ,document.getString("Car_Model")
                                        ,document.getString("Car_Color")
                                        ,document.getString("Car_Cylinders")
                                        ,document.getString("Car_Maker")
                                        ,document.getString("Car_Brand")
                                        ,document.getString("Car_Type")
                                        ,document.getString("Car_Belongs"));

                                vehicleItemList.add(vehicleItem);
                            }

                            adapter = new CarsListAdapter(vehicleItemList);
                            rv.setAdapter(adapter);

                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (parent.getId())
        {
            case R.id.carmakerspinner:

                if(carmakers.getSelectedItemPosition()==1)
                {

                    brands.clear();

                    brands.add("اختر طراز السيارة");
                    brands.add("افيو");
                    brands.add("أوبترا");
                    brands.add("باك اب");
                    brands.add("دوبل كابينة");
                    brands.add("اخرى");

                }else if(carmakers.getSelectedItemPosition()==2)
                {

                    brands.clear();

                    brands.add("اختر طراز السيارة");
                    brands.add("صنى");
                    brands.add("باك اب");
                    brands.add("دوبل كابينة");
                    brands.add("اخرى");
                    brands.add("اخرى");

                }else if(carmakers.getSelectedItemPosition()==3)
                {
                    brands.clear();

                    brands.add("اختر طراز السيارة");
                    brands.add("٥٠٤");
                    brands.add("٥٠٥");
                    brands.add("٤٠٦");
                    brands.add("٣٠٨");
                    brands.add("٤٠٨");

                }else if(carmakers.getSelectedItemPosition()==4)
                {
                    brands.clear();

                    brands.add("اختر طراز السيارة");
                    brands.add("١٢٨");
                    brands.add("شاهين");
                    brands.add("اتوبيس");
                    brands.add("اخرى");
                    brands.add("اخرى");

                }else if(carmakers.getSelectedItemPosition()==5)
                {
                    brands.clear();

                    brands.add("اختر طراز السيارة");
                    brands.add("اتوبيس");
                    brands.add("اخرى");
                    brands.add("اخرى");
                    brands.add("اخرى");
                    brands.add("اخرى");

                }else if(carmakers.getSelectedItemPosition()==6)
                {
                    brands.clear();

                    brands.add("دوبل كابينة");

                }else
                {
                    brands.clear();

                    brands.add("اختر الماركة أولا");
                }

                carbrand.setAdapter(adapter4);

                maker = carmakers.getSelectedItem().toString();

                break;

            case R.id.carbrandspinner:

                brand = carbrand.getSelectedItem().toString();

                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
