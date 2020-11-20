package jp.ac.shohoku.s19b703.shibuyakai;

//歩数計グラフ画面

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class GraphFragment extends Fragment {
    public static GraphFragment newInstance(){
        GraphFragment fragment = new GraphFragment ();
        // Bundle にパラメータを設定
        Bundle barg = new Bundle();
        fragment.setArguments(barg);

        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_graph, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        closeGraph();
    }

    public void closeGraph(){
        Button close = (Button)getActivity().findViewById(R.id.closeGraphButton);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().remove(GraphFragment.this).commit();
            }
        });
    }
}
