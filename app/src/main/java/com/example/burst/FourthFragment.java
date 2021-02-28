package com.example.burst;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FourthFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FourthFragment extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fourth, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.button_fourth).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TO_DO Create document
                /*
                String date = ((EditText)view.findViewById(R.id.editTextDate)).toString();
                String time = view.findViewById(R.id.editTextTime).toString();
                String location = view.findViewById(R.id.editTextLocation).toString();
                System.out.println(date);
                */
                //Documentation current = new Documentation(date, time, location);
                //NavHostFragment.findNavController(FourthFragment.this)
                  //      .navigate(R.id.action_thirdFragment_to_fourthFragment3);
            }
        });
    }
}