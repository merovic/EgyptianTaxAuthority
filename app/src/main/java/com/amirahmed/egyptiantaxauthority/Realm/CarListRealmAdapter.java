package com.amirahmed.egyptiantaxauthority.Realm;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.amirahmed.egyptiantaxauthority.CarDetailsActivity;
import com.amirahmed.egyptiantaxauthority.EditCarActivity;
import com.amirahmed.egyptiantaxauthority.R;
import com.amirahmed.egyptiantaxauthority.TinyDB;

import java.util.List;

public class CarListRealmAdapter extends RecyclerView.Adapter<CarListRealmAdapter.CarListRealmViewHolder> {

    private List<VehicleItemRealm> vehicleItemList;
    Context context;

    TinyDB tinyDB;

    public CarListRealmAdapter(List<VehicleItemRealm> vehicleItemList) {
        this.vehicleItemList = vehicleItemList;
    }

    @NonNull
    @Override
    public CarListRealmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        context = parent.getContext();

        tinyDB = new TinyDB(context);

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_car, parent, false);
        return new CarListRealmAdapter.CarListRealmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarListRealmViewHolder holder, final int position) {

        holder.no2.setText(vehicleItemList.get(position).getCar_No());
        holder.no.setText(vehicleItemList.get(position).getCar_No2());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showMessage(String.valueOf(vehicleItemList.get(position).getCar_ID()));


                if(tinyDB.getString("Type").equals("New"))
                {
                    Intent intent = new Intent(context,CarDetailsActivity.class);
                    intent.putExtra("number",vehicleItemList.get(position).getCar_No());
                    intent.putExtra("number2",vehicleItemList.get(position).getCar_No2());
                    intent.putExtra("maker",vehicleItemList.get(position).getCar_Maker());
                    intent.putExtra("brand",vehicleItemList.get(position).getCar_Brand());
                    intent.putExtra("color",vehicleItemList.get(position).getCar_Color());
                    intent.putExtra("year",vehicleItemList.get(position).getCar_Model());
                    intent.putExtra("motor",vehicleItemList.get(position).getCar_Engine());
                    intent.putExtra("chasse",vehicleItemList.get(position).getCar_Chassis());
                    intent.putExtra("cylinders",vehicleItemList.get(position).getCar_Cylinders());
                    intent.putExtra("fuel",vehicleItemList.get(position).getCar_Type());
                    intent.putExtra("belongs",vehicleItemList.get(position).getCar_Belongs());
                    context.startActivity(intent);
                }else
                {
                    Intent intent = new Intent(context,EditCarActivity.class);
                    intent.putExtra("ID",vehicleItemList.get(position).getCar_ID());
                    intent.putExtra("number",vehicleItemList.get(position).getCar_No());
                    intent.putExtra("number2",vehicleItemList.get(position).getCar_No2());
                    intent.putExtra("maker",vehicleItemList.get(position).getCar_Maker());
                    intent.putExtra("brand",vehicleItemList.get(position).getCar_Brand());
                    intent.putExtra("color",vehicleItemList.get(position).getCar_Color());
                    intent.putExtra("year",vehicleItemList.get(position).getCar_Model());
                    intent.putExtra("motor",vehicleItemList.get(position).getCar_Engine());
                    intent.putExtra("chasse",vehicleItemList.get(position).getCar_Chassis());
                    intent.putExtra("cylinders",vehicleItemList.get(position).getCar_Cylinders());
                    intent.putExtra("fuel",vehicleItemList.get(position).getCar_Type());
                    intent.putExtra("belongs",vehicleItemList.get(position).getCar_Belongs());
                    context.startActivity(intent);
                }


            }
        });

    }

    @Override
    public int getItemCount() {
        return vehicleItemList.size();
    }

    class CarListRealmViewHolder extends RecyclerView.ViewHolder {

        TextView no,no2;

        CarListRealmViewHolder(View itemView) {
            super(itemView);

            no = itemView.findViewById(R.id.carnotext);
            no2 = itemView.findViewById(R.id.carnotext2);

        }
    }

    private void showMessage(String _s) {
        Toast.makeText(context, _s, Toast.LENGTH_SHORT).show();
    }
}
