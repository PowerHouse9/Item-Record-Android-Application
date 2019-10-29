package com.bill.ordersystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private List<OrderItem> orderList;
    private Context mContext;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public  static class OrderViewHolder extends RecyclerView.ViewHolder {

        public TextView OrderName, Supplier, Quantity, Date, Description;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);

            OrderName = itemView.findViewById(R.id.txt_cardorderName);
            Supplier = itemView.findViewById(R.id.txt_cardorderSupplier);
            Quantity = itemView.findViewById(R.id.txt_cardQuantity);
            Date = itemView.findViewById(R.id.txt_cardDate);
            Description = itemView.findViewById(R.id.txt_cardOrderDesc);
        }
    }

    public OrderAdapter (List<OrderItem> list, Context ctx) {
        orderList = list;
        mContext = ctx;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.order_item, parent, false);
        return new OrderViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        OrderItem orderCurrentItem = orderList.get(position);

        holder.OrderName.setText(orderCurrentItem.getOrderName());
        holder.Supplier.setText(orderCurrentItem.getSupplier());
        holder.Quantity.setText(orderCurrentItem.getQuantity());
        holder.Date.setText(orderCurrentItem.getDate());
        holder.Description.setText(orderCurrentItem.getDescription());
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
}
