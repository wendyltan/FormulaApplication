package xyz.wendyltanpcy.formulaapplication.Fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.zzhoujay.markdown.MarkDown;

import java.io.InputStream;

import xyz.wendyltanpcy.formulaapplication.R;


public class AboutFragment extends Fragment {

    public AboutFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_about, container, false);
        ScrollView scrollView = v.findViewById(R.id.about_scroll_info);
        final TextView about = v.findViewById(R.id.about_text);
        about.post(new Runnable() {
            @Override
            public void run() {
                InputStream in = getContext().getResources().openRawResource(R.raw.about);
                Spanned spanned = MarkDown.fromMarkdown(in, new Html.ImageGetter() {
                    @Override
                    public Drawable getDrawable(String source) {
                        return null;
                    }
                }, about);
                about.setText(spanned);
            }

        });


        return v;
    }

}
