package jp.ac.shohoku.s19b703.shibuyakai;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class CreditFragment extends Fragment {
    public static CreditFragment newInstance() {
        
        Bundle args = new Bundle();
        
        CreditFragment fragment = new CreditFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_credit, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        closeCredit();
        ImageView credit = (ImageView)getActivity().findViewById(R.id.creditView);
        credit.setImageResource(R.drawable.credit2);
    }

    public void closeCredit(){
        Button close = (Button)getActivity().findViewById(R.id.closeCredit);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
    }
}
