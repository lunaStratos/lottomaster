package lottomaster.lunastratos.com.lottomaster.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lottomaster.lunastratos.com.lottomaster.R;

public class InfoFragment extends Fragment {

    TextView infoEmail;
    TextView infoSite;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_info,container,false);

        infoEmail = v.findViewById(R.id.infoEmail);
        infoSite = v.findViewById(R.id.infoSite);

        Linkify.TransformFilter mTransform = new Linkify.TransformFilter() {
            @Override
            public String transformUrl(Matcher matcher, String s) {
                return "";
            }
        };

        Pattern emailSearch = Pattern.compile("이메일");
        Pattern siteSearch = Pattern.compile("사이트");
        String email = "Dev.LunaStratos@gmail.com";
        String site = "http://stratos.dothome.co.kr";

        Linkify.addLinks(infoEmail, emailSearch, email,null, mTransform);
        Linkify.addLinks(infoSite, siteSearch, site, null, mTransform);

        infoEmail.setMovementMethod(LinkMovementMethod.getInstance());
        infoSite.setMovementMethod(LinkMovementMethod.getInstance());

        return v;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }


}
