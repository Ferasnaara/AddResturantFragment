package com.example.addresturantfragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;

    public class addResturantfragment extends Fragment {

    private FireBaseServices fbs;
    private EditText etName, etDescription, etAddress, etPhone;
    private Button btnAdd;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public addResturantfragment() {
        // Required empty public constructor
    }

    public static addResturantfragment newInstance(String param1, String param2) {
        addResturantfragment fragment = new addResturantfragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_main, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        connectComponents();

    }

    private void connectComponents() {
        fbs = FireBaseServices.getInstance();
        etName = getView().findViewById(R.id.frameLayout);
        etDescription = getView().findViewById(R.id.frameLayout);
        etAddress = getView().findViewById(R.id.frameLayout);
        etPhone = getView().findViewById(R.id.frameLayout);
        btnAdd = getView().findViewById(R.id.frameLayout);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString();
                String description = etDescription.getText().toString();
                String address = etAddress.getText().toString();
                String phone = etPhone.getText().toString();

                if (name.trim().isEmpty() || description.trim().isEmpty() ||
                        address.trim().isEmpty() || phone.trim().isEmpty())
                {
                    Toast.makeText(getActivity(), "Some fields are empty!", Toast.LENGTH_LONG).show();
                    return;
                }
                Resturant rest = new Resturant(name, description, address, phone);

                fbs.getFire().collection("restaurants").add(rest).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getActivity(), "Successfully added your restaurant!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("Failure AddResturant: ", e.getMessage());
                    }
                });


            }
        });
    }
}