package lottomaster.lunastratos.com.lottomaster.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

import lottomaster.lunastratos.com.lottomaster.LottoInfoAsyncTask;
import lottomaster.lunastratos.com.lottomaster.R;

public class HomeFragment extends Fragment {

    TextView insert_home;
    HashMap map;
    ImageView ballImage;

    TextView ball1;
    TextView ball2;
    TextView ball3;
    TextView ball4;
    TextView ball5;
    TextView ball6;
    TextView bonus;

    TextView firstHowTo;
    TextView firstAccumamnt;
    TextView firstPrzwnerCo;
    TextView firstWinamnt;

    /*
    최초에 로또 현재 데이터를 받는 부분
    에러로 뜨나 정상임
     */
    public HomeFragment(HashMap map) {
        this.map = map;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        insert_home = v.findViewById(R.id.insert_home);

        ball1 = v.findViewById(R.id.ball1);
        ball2 = v.findViewById(R.id.ball2);
        ball3 = v.findViewById(R.id.ball3);
        ball4 = v.findViewById(R.id.ball4);
        ball5 = v.findViewById(R.id.ball5);
        ball6 = v.findViewById(R.id.ball6);
        bonus = v.findViewById(R.id.bonus);

        firstHowTo = v.findViewById(R.id.firstHowTo);
        firstAccumamnt = v.findViewById(R.id.firstAccumamnt);
        firstPrzwnerCo = v.findViewById(R.id.firstPrzwnerCo);
        firstWinamnt = v.findViewById(R.id.firstWinamnt);

        Log.i("HomeFragment", map.toString());
        if (map != null) {
            insert_home.setText("조회된 회차:" + map.get("drwNoDate") + "  / " + map.get("drwNo"));

            ball1.setText((String) map.get("drwtNo1"));
            ball2.setText((String) map.get("drwtNo2"));
            ball3.setText((String) map.get("drwtNo3"));
            ball4.setText((String) map.get("drwtNo4"));
            ball5.setText((String) map.get("drwtNo5"));
            ball6.setText((String) map.get("drwtNo6"));
            bonus.setText((String) map.get("bnusNo"));

            firstAccumamnt.setText((String) map.get("firstAccumamnt"));
            firstHowTo.setText((String) map.get("firstHowTo"));
            firstPrzwnerCo.setText((String) map.get("firstHowTo"));
            firstWinamnt.setText((String) map.get("firstWinamnt"));


        } else {
            getNowLottoInfo();
        }


        return v;

    }

    public void getNowLottoInfo() {
        String[] nowLottoAddress = {"https://www.dhlottery.co.kr/gameResult.do?method=byWin", "now"};
        LottoInfoAsyncTask task = new LottoInfoAsyncTask(insert_home);
        task.execute(nowLottoAddress);
    }


}
