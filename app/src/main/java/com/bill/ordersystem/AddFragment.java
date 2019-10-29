package com.bill.ordersystem;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddFragment extends Fragment {

    private EditText itemName, Supplier, Quantity, Date, Description;
    private Button saveButton;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add, container, false);

        itemName = rootView.findViewById(R.id.txt_itemName);
        Supplier = rootView.findViewById(R.id.txt_Supplier);
        Quantity = rootView.findViewById(R.id.txt_Quantity);
        Date = rootView.findViewById(R.id.txt_Date);
        Description = rootView.findViewById(R.id.txt_Description);
        saveButton = rootView.findViewById(R.id.btn_Save);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        submitClick();

        return rootView;
    }

    private void submitClick () {
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadOrder();
            }
        });
    }

    private void uploadOrder () {

        String ItemName = itemName.getText().toString().trim();
        String supplier = Supplier.getText().toString().trim();
        String quantity = Quantity.getText().toString().trim();
        String date = Date.getText().toString().trim();
        String description = Description.getText().toString().trim();

        OrderItem orderItem = new OrderItem(ItemName,supplier,quantity,date,description);

        CollectionReference dbOrder = db.collection("Orders");

        if(!validateInputs(ItemName, supplier, quantity, date, description)){

            dbOrder.add(orderItem)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(getActivity(), "Form Has Been Saved !", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }

    }

    private boolean validateInputs(String ItemName, String supplier, String quantity, String date, String description){

        if(ItemName.isEmpty()){
            itemName.setError("This Field Cannot be Empty");
            itemName.requestFocus();
            return true;
        }

        if(supplier.isEmpty()){
            Supplier.setError("This Field Cannot be Empty");
            Supplier.requestFocus();
            return true;
        }

        if(quantity.isEmpty()){
            Quantity.setError("This Field Cannot be Empty");
            Quantity.requestFocus();
            return true;
        }

        if(date.isEmpty()){
            Date.setError("This Field Cannot be Empty");
            Date.requestFocus();
            return true;
        }

        if(description.isEmpty()){
            Description.setError("This Field Cannot be Empty");
            Description.requestFocus();
            return true;
        }

        return false;
    }
}
