package projectbtn.chrisli8.washington.edu.projectbtn;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class GameFragment extends Fragment {

    RelativeLayout fragment;

    public GameFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);
        // Inflate the layout for this fragment
        fragment = (RelativeLayout) view.findViewById(R.id.grid);
        return view;
    }

    public RelativeLayout getLayout() {
        return this.fragment;
    }

}
