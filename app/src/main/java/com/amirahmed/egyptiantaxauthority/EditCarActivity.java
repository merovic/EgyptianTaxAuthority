package com.amirahmed.egyptiantaxauthority;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.amirahmed.egyptiantaxauthority.Realm.RealmHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class EditCarActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

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

    ArrayAdapter<String> adapter4;

    String color,cylinder,maker,brand,type,belong;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    String ID;
    int ID2;

    //----------------

    Realm realm;
    RealmHelper helper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        //SETUP REALM
        Realm.init(this);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .name("vehicles.realm")
                .schemaVersion(0)
                .build();
        Realm.setDefaultConfiguration(realmConfig);

        realm = Realm.getDefaultInstance();

        //--------------

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


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String numbers = extras.getString("number");
            String numbers2 = extras.getString("number2");
            String makers = extras.getString("maker");
            String brands = extras.getString("brand");
            String colors = extras.getString("color");
            String years = extras.getString("year");
            String motors = extras.getString("motor");
            String chasses = extras.getString("chasse");
            String cylinderss = extras.getString("cylinders");
            String fuels = extras.getString("fuel");
            String belongss = extras.getString("belongs");

            ID = extras.getString("ID");
            ID2 = extras.getInt("ID");

            carmakers.setSelection(adapter3.getPosition(makers));
            carbrand.setSelection(adapter4.getPosition(brands));
            carcolor.setSelection(adapter.getPosition(colors));
            carcylinders.setSelection(adapter2.getPosition(cylinderss));
            fueltype.setSelection(adapter5.getPosition(fuels));
            belongsto.setSelection(adapter6.getPosition(belongss));

            carnumber.setText(numbers);
            carnumber2.setText(numbers2);

            carmodel.setText(years);
            carengine.setText(motors);
            carchassis.setText(chasses);

        }


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
                        //UPDATE_DATA_CLOUD_FIRESTORE();
                        UPDATE_DATA_REALM();
                    }
                }

            }
        });


    }

    public void UPDATE_DATA_REALM()
    {
        helper = new RealmHelper(realm);
        helper.update(ID2,carengine.getText().toString(),carchassis.getText().toString(),carnumber.getText().toString(),carnumber2.getText().toString(),carmodel.getText().toString(),color,cylinder,maker,brand,type,belong);
        showMessage("تم تحديث البيانات");
        finish();


    }


    public void UPDATE_DATA_CLOUD_FIRESTORE()
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
        db.collection("vehicles").document(ID).update(vehicles).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                showMessage("تم التحديث بنجاح");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                showMessage("خطأ");
            }
        });

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

    private boolean isEmpty(EditText carengine, EditText carchassis, EditText carnumber, EditText carmodel) {
        return !(carengine.getText().toString().trim().length() > 0 && carchassis.getText().toString().trim().length() > 0 && carnumber.getText().toString().trim().length() > 0  && carmodel.getText().toString().trim().length() > 0);

    }


    private void showMessage(String _s) {
        Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
    }
}
