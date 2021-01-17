package jp.ac.shohoku.s19b703.shibuyakai;

//ゲーム説明画面

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class ExpoFragment extends Fragment {
    String expo = "モンスターの幼体を発見したぞ!\n" +
                  "歩いたエネルギーでモンスターは育つ。\n" +
                  "育ち切ったモンスターは旅立っていくよ。\n" +
                  "モンスターは全部で3種類!\n"+
                  "君はすべてのモンスターを育てられるかな!?";


    public static ExpoFragment newInstance() {
        ExpoFragment fragment = new ExpoFragment();
        // Bundle にパラメータを設定
        Bundle barg = new Bundle();
        fragment.setArguments(barg);

        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_expo, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView text = (TextView) getActivity().findViewById(R.id.ExpoText);
        text.setText(expo);
        closeExpo();
        moveCresit();
    }

    public void closeExpo() {
        Button close = (Button) getActivity().findViewById(R.id.closeExpoButton);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button expo = (Button) getActivity().findViewById(R.id.expoButton);
                Button count = (Button) getActivity().findViewById(R.id.countButton);
                expo.setVisibility(View.VISIBLE);
                count.setVisibility(View.VISIBLE);
                assert getFragmentManager() != null;
                getFragmentManager().beginTransaction().remove(ExpoFragment.this).commit();
            }
        });
    }

    private void moveCresit() {
        Button credit = (Button) getActivity().findViewById(R.id.Credit);
        credit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                assert fragmentManager != null;
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                // BackStackを設定
                fragmentTransaction.addToBackStack(null);

                // パラメータを設定
                fragmentTransaction.replace(R.id.expo, CreditFragment.newInstance());
                fragmentTransaction.commit();
            }
        });
    }

}
