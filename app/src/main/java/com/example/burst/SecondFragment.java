package com.example.burst;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.app.Activity;
import android.widget.ImageButton;

public class SecondFragment extends Fragment {

    EditText date;
    EditText location;
    EditText time;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        date = view.findViewById(R.id.editTextDate);
        time = view.findViewById(R.id.editTextTime);
        location = view.findViewById(R.id.editTextLocation);
        view.findViewById(R.id.button_second).setOnClickListener(new View.OnClickListener() {
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
                /*try {
                    FileOutputStream fos = openFileOutput("burst-report.txt");
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));
                    EditText editText = (EditText) findViewById(R.id.editTextDate);
                    String text = editText.getText.toString();
                    writer.write(text);
                    writer.close();
                } catch (IOException e) {
                    Log.v("ERROR", "error writing to file");
                    e.printStackTrace();
                }*/

                ((MainActivity)getActivity()).makeDocument(date.getText().toString(), time.getText().toString(), location.getText().toString());
                System.out.println(date.getText().toString());

                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_thirdFragment);
            }
        });
    }



}