package jp.ac.shohoku.s19b703.shibuyakai;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

public class CreditFragment extends Fragment {
    public static CreditFragment newInstance() {
        
        Bundle args = new Bundle();
        
        CreditFragment fragment = new CreditFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
