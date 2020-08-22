package qazi.tooba.com.drawer;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

/**
 * Created by sickbay on 12/15/2017.
 */

public class BottomSheetRiderFragment extends BottomSheetDialogFragment {
    String name;
    String number;

    public static BottomSheetRiderFragment newInstance(String name, String number)
    {
        BottomSheetRiderFragment f = new BottomSheetRiderFragment();
        f.name = name;
        f.number = number;
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_rider,container,false);

        TextView tvName = view.findViewById(R.id.tvName);
        TextView tvNumber= view.findViewById(R.id.tvNumber);


        tvName.setText(name);
        tvNumber.setText(number);

        return view;
    }
}
