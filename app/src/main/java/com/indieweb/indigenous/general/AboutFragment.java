package com.indieweb.indigenous.general;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.indieweb.indigenous.BuildConfig;
import com.indieweb.indigenous.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AboutFragment extends Fragment {

    View currentView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_about, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().setTitle("About");
        currentView = view;

        // Html.
        TextView about = view.findViewById(R.id.about);
        about.setMovementMethod(LinkMovementMethod.getInstance());
        String aboutInfo = getString(R.string.about_info);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            about.setText(Html.fromHtml(aboutInfo, Html.FROM_HTML_MODE_LEGACY));
        }
        else {
            about.setText(Html.fromHtml(aboutInfo));
        }

        // Version number.
        TextView version = view.findViewById(R.id.about_version);
        version.setText("Indigenous version: " + BuildConfig.VERSION_NAME);

        // Changelog.
        Button changelog = view.findViewById(R.id.about_changelog_button);
        changelog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadChangelog();
            }
        });
    }

    /**
     * Load changelog.
     */
    public void loadChangelog() {
        StringBuilder text = new StringBuilder();

        try {
            String line;
            BufferedReader reader = new BufferedReader(new InputStreamReader(requireActivity().getAssets().open("changelog")));
            while ((line = reader.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
        } catch (IOException ignored) { }

        TextView t = currentView.findViewById(R.id.about_changelog);
        t.setMovementMethod(LinkMovementMethod.getInstance());
        t.setText(Html.fromHtml(text.toString()));
    }

}