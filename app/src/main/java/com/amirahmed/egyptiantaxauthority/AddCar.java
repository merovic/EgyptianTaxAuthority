package com.amirahmed.egyptiantaxauthority;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.amirahmed.egyptiantaxauthority.Realm.RealmHelper;
import com.amirahmed.egyptiantaxauthority.Realm.VehicleItemRealm;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class AddCar extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private static final String TAG = "123";
    EditText carengine,carchassis,carnumber,carnumber2,carmodel;
    Spinner carcolor,carcylinders,carmakers,carbrand,fueltype,belongsto;

    List<String> colors = new ArrayList<>();
    List<String> cylinders = new ArrayList<>();
    List<String> makers = new ArrayList<>();
    List<String> brands = new ArrayList<>();
    List<String> types = new ArrayList<>();
    List<String> belongs = new ArrayList<>();

    Button addbutton;

    String GET_JSON_DATA_HTTP_URL;

    RequestQueue requestQueue;

    ArrayAdapter<String> adapter4;

    String color,cylinder,maker,brand,type,belong;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    //-----------------------------

    Realm realm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        //SETUP REALM
        Realm.init(this);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .name("vehicles.realm")
                .schemaVersion(0)
                .build();
        Realm.setDefaultConfiguration(realmConfig);

        realm = Realm.getDefaultInstance();

        //--------------------------


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        carengine = findViewById(R.id.carengineedittext);
        carchassis = findViewById(R.id.carchassisedittext);
        carnumber = findViewById(R.id.carnumberedittext);
        carnumber2 = findViewById(R.id.carnumberedittext2);
        carmodel = findViewById(R.id.carmodeledittext);

        carcolor = findViewById(R.id.carcolorspinner);
        carcylinders = findViewById(R.id.carcylindersspinner);
        carmakers = findViewById(R.id.carmakerspinner);
        carbrand = findViewById(R.id.carbrandspinner);
        fueltype = findViewById(R.id.fueltypespinner);
        belongsto = findViewById(R.id.belongstopinner);


        colors.add("اختر لون السيارة");
        colors.add("أحمر");
        colors.add("أبيض");
        colors.add("أسود");
        colors.add("أزرق");
        colors.add("أصفر");

        cylinders.add("اختر عدد السلندرات");
        cylinders.add("٤");
        cylinders.add("٥");
        cylinders.add("٦");
        cylinders.add("٧");
        cylinders.add("٨");

        makers.add("اختر ماركة السيارة");
        makers.add("شيفروليه");
        makers.add("نيسان");
        makers.add("بيجو");
        makers.add("نصر");
        makers.add("مرسيدس");


        types.add("اختر نوع الوقود");
        types.add("بنزين");
        types.add("سولار");
        types.add("غاز طبيعى");


        belongs.add("السيارة تابعة الى ؟");
        belongs.add("القاهرة");
        belongs.add("الأسكندرية");
        belongs.add("المنيا");
        belongs.add("بورسعيد");
        belongs.add("دمياط");


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, colors);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        carcolor.setAdapter(adapter);
        carcolor.setOnItemSelectedListener(this);

        adapter.notifyDataSetChanged();

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cylinders);

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        carcylinders.setAdapter(adapter2);
        carcylinders.setOnItemSelectedListener(this);

        adapter2.notifyDataSetChanged();

        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, makers);

        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        carmakers.setAdapter(adapter3);
        carmakers.setOnItemSelectedListener(this);

        adapter3.notifyDataSetChanged();

        adapter4 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, brands);

        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        carbrand.setAdapter(adapter4);
        carbrand.setOnItemSelectedListener(this);

        adapter4.notifyDataSetChanged();

        ArrayAdapter<String> adapter5 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, types);

        adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fueltype.setAdapter(adapter5);
        fueltype.setOnItemSelectedListener(this);

        adapter5.notifyDataSetChanged();

        ArrayAdapter<String> adapter6 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, belongs);

        adapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        belongsto.setAdapter(adapter6);
        belongsto.setOnItemSelectedListener(this);

        adapter6.notifyDataSetChanged();

        addbutton = findViewById(R.id.addbutton);

        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isEmpty(carengine,carchassis,carnumber,carmodel))
                {
                    showMessage("احد الحقول فارغة");
                }else
                    {

                        if(carcolor.getSelectedItemPosition()==0 || carcylinders.getSelectedItemPosition()==0 || carmakers.getSelectedItemPosition()==0 || carbrand.getSelectedItemPosition()==0 || fueltype.getSelectedItemPosition()==0 || belongsto.getSelectedItemPosition()==0)
                        {
                            showMessage("احد الاختيارات فارغة");
                        }else
                            {
                                //JSON_DATA_WEB_CALL();
                                //ADD_DATA_CLOUD_FIRESTORE();
                                ADD_DATA_TO_REALM();
                            }
                    }

            }
        });


    }

    public int getNextKey() {
        try {
            Number number = realm.where(VehicleItemRealm.class).max("Car_ID");
            if (number != null) {
                return number.intValue() + 1;
            } else {
                return 0;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return 0;
        }
    }

    private void ADD_DATA_TO_REALM() {

        VehicleItemRealm vehicleItem = new VehicleItemRealm();
        vehicleItem.setCar_ID(getNextKey());
        vehicleItem.setCar_Engine(carengine.getText().toString());
        vehicleItem.setCar_Chassis(carchassis.getText().toString());
        vehicleItem.setCar_No(carnumber.getText().toString());
        vehicleItem.setCar_No2(carnumber2.getText().toString());
        vehicleItem.setCar_Model(carmodel.getText().toString());
        vehicleItem.setCar_Color(color);
        vehicleItem.setCar_Cylinders(cylinder);
        vehicleItem.setCar_Maker(maker);
        vehicleItem.setCar_Brand(brand);
        vehicleItem.setCar_Type(type);
        vehicleItem.setCar_Belongs(belong);

        RealmHelper helper = new RealmHelper(realm);
        helper.save(vehicleItem);

    }

    public void ADD_DATA_CLOUD_FIRESTORE()
    {

        // Create a new vehicles with data
        Map<String, Object> vehicles = new HashMap<>();
        vehicles.put("Car_Engine", carengine.getText().toString());
        vehicles.put("Car_Chassis", carchassis.getText().toString());
        vehicles.put("Car_No", carnumber.getText().toString());
        vehicles.put("Car_No2", carnumber2.getText().toString());
        vehicles.put("Car_Model", carmodel.getText().toString());
        vehicles.put("Car_Color", color);
        vehicles.put("Car_Cylinders", cylinder);
        vehicles.put("Car_Maker", maker);
        vehicles.put("Car_Brand", brand);
        vehicles.put("Car_Type", type);
        vehicles.put("Car_Belongs", belong);

        // Add a new document with a generated ID
        db.collection("vehicles")
                .add(vehicles)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        //showMessage("DocumentSnapshot added with ID: " + documentReference.getId());
                        showMessage("تم اضافة السيارة");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        showMessage("Error adding document");
                        Log.w(TAG, "Error adding document", e);
                    }
                });

    }


    public void JSON_DATA_WEB_CALL(){

        GET_JSON_DATA_HTTP_URL = "http://thegreatkiko2090.000webhostapp.com/mobile/users_register.php?Engine_Number="+carengine.getText().toString()+"&Car_Chassis_Number="+carchassis.getText().toString()+"&Car_Number="+carnumber.getText().toString()+"&Car_Model="+carmodel.getText().toString()+"&Car_Color="+color+"&Car_Cylinders="+cylinder+"&Car_Manufacturer="+maker+"&Car_Brand="+brand+"&Fuel_Type="+type+"&Belongs_To="+belong;
        StringRequest stringRequest = new StringRequest(Request.Method.GET,GET_JSON_DATA_HTTP_URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(Objects.equals(response, "1"))
                        {
                            showMessage("تم الأضافة بنجاح");
                        }else
                            {
                                showMessage("فشل فى اضافة سيارة");
                            }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        showMessage("فشل الأتصال");


                    }
                }
        );

        requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (parent.getId()) {
            case R.id.carcolorspinner:

                color = carcolor.getSelectedItem().toString();

                break;
            case R.id.carcylindersspinner:

                cylinder = carcylinders.getSelectedItem().toString();

                break;
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
            case R.id.fueltypespinner:

               type  = fueltype.getSelectedItem().toString();

                break;
            case R.id.belongstopinner:

                belong = belongsto.getSelectedItem().toString();

                break;
            default:
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private boolean isEmpty(EditText carengine,EditText carchassis,EditText carnumber,EditText carmodel) {
        return !(carengine.getText().toString().trim().length() > 0 && carchassis.getText().toString().trim().length() > 0 && carnumber.getText().toString().trim().length() > 0  && carmodel.getText().toString().trim().length() > 0);

    }


    private void showMessage(String _s) {
        Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
    }


}
