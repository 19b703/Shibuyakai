package jp.ac.shohoku.s19b703.shibuyakai;

//ゲーム説明画面

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class ExpoFragment extends Fragment {
    public static ExpoFragment newInstance(){
        ExpoFragment fragment = new ExpoFragment ();
        // Bundle にパラメータを設定
        Bundle barg = new Bundle();
        fragment.setArguments(barg);

        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_expo, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        closeExpo();
    }

    public void closeExpo(){
        Button close = (Button)getActivity().findViewById(R.id.closeExpoButton);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button expo = (Button)getActivity().findViewById(R.id.expoButton);
                Button count = (Button)getActivity().findViewById(R.id.countButton);
                expo.setVisibility(View.VISIBLE);
                count.setVisibility(View.VISIBLE);
                getFragmentManager().beginTransaction().remove(ExpoFragment.this).commit();
            }
        });
    }

}
