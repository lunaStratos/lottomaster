package lottomaster.lunastratos.com.lottomaster.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import lottomaster.lunastratos.com.lottomaster.R;

public class RandomFragment extends Fragment {
    TextView randomText;

    TextView randomball1;
    TextView randomball2;
    TextView randomball3;
    TextView randomball4;
    TextView randomball5;
    TextView randomball6;

    TextView randomball1_2;
    TextView randomball2_2;
    TextView randomball3_2;
    TextView randomball4_2;
    TextView randomball5_2;
    TextView randomball6_2;

    TextView randomball1_3;
    TextView randomball2_3;
    TextView randomball3_3;
    TextView randomball4_3;
    TextView randomball5_3;
    TextView randomball6_3;

    TextView randomball1_4;
    TextView randomball2_4;
    TextView randomball3_4;
    TextView randomball4_4;
    TextView randomball5_4;
    TextView randomball6_4;

    TextView randomball1_5;
    TextView randomball2_5;
    TextView randomball3_5;
    TextView randomball4_5;
    TextView randomball5_5;
    TextView randomball6_5;

    Button makeRandomBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_random, container, false);
        randomText = v.findViewById(R.id.randomTextResult);

        randomball1 = v.findViewById(R.id.randomball1);
        randomball2 = v.findViewById(R.id.randomball2);
        randomball3 = v.findViewById(R.id.randomball3);
        randomball4 = v.findViewById(R.id.randomball4);
        randomball5 = v.findViewById(R.id.randomball5);
        randomball6 = v.findViewById(R.id.randomball6);

        randomball1_2 = v.findViewById(R.id.randomball1_2);
        randomball2_2 = v.findViewById(R.id.randomball2_2);
        randomball3_2 = v.findViewById(R.id.randomball3_2);
        randomball4_2 = v.findViewById(R.id.randomball4_2);
        randomball5_2 = v.findViewById(R.id.randomball5_2);
        randomball6_2 = v.findViewById(R.id.randomball6_2);

        randomball1_3 = v.findViewById(R.id.randomball1_3);
        randomball2_3 = v.findViewById(R.id.randomball2_3);
        randomball3_3 = v.findViewById(R.id.randomball3_3);
        randomball4_3 = v.findViewById(R.id.randomball4_3);
        randomball5_3 = v.findViewById(R.id.randomball5_3);
        randomball6_3 = v.findViewById(R.id.randomball6_3);

        randomball1_4 = v.findViewById(R.id.randomball1_4);
        randomball2_4 = v.findViewById(R.id.randomball2_4);
        randomball3_4 = v.findViewById(R.id.randomball3_4);
        randomball4_4 = v.findViewById(R.id.randomball4_4);
        randomball5_4 = v.findViewById(R.id.randomball5_4);
        randomball6_4 = v.findViewById(R.id.randomball6_4);

        randomball1_5 = v.findViewById(R.id.randomball1_5);
        randomball2_5 = v.findViewById(R.id.randomball2_5);
        randomball3_5 = v.findViewById(R.id.randomball3_5);
        randomball4_5 = v.findViewById(R.id.randomball4_5);
        randomball5_5 = v.findViewById(R.id.randomball5_5);
        randomball6_5 = v.findViewById(R.id.randomball6_5);

        makeRandomBtn = v.findViewById(R.id.makeRandomBtn);

        int[] resultArr = makeRandom();
        setRandomLottoNum();

        makeRandomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setRandomLottoNum();
            }
        });
        return v;

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    /*
    로또 볼 이미지 세팅
     */
    private void setRandomLottoNum() {
        int[] resultArr = makeRandom();

        randomball1.setText("" + resultArr[0]);
        randomball2.setText("" + resultArr[1]);
        randomball3.setText("" + resultArr[2]);
        randomball4.setText("" + resultArr[3]);
        randomball5.setText("" + resultArr[4]);
        randomball6.setText("" + resultArr[5]);

        //재생성
        resultArr = makeRandom();
        randomball1_2.setText("" + resultArr[0]);
        randomball2_2.setText("" + resultArr[1]);
        randomball3_2.setText("" + resultArr[2]);
        randomball4_2.setText("" + resultArr[3]);
        randomball5_2.setText("" + resultArr[4]);
        randomball6_2.setText("" + resultArr[5]);

        //재생성
        resultArr = makeRandom();
        randomball1_3.setText("" + resultArr[0]);
        randomball2_3.setText("" + resultArr[1]);
        randomball3_3.setText("" + resultArr[2]);
        randomball4_3.setText("" + resultArr[3]);
        randomball5_3.setText("" + resultArr[4]);
        randomball6_3.setText("" + resultArr[5]);

        //재생성
        resultArr = makeRandom();
        randomball1_4.setText("" + resultArr[0]);
        randomball2_4.setText("" + resultArr[1]);
        randomball3_4.setText("" + resultArr[2]);
        randomball4_4.setText("" + resultArr[3]);
        randomball5_4.setText("" + resultArr[4]);
        randomball6_4.setText("" + resultArr[5]);

        //재생성
        resultArr = makeRandom();
        randomball1_5.setText("" + resultArr[0]);
        randomball2_5.setText("" + resultArr[1]);
        randomball3_5.setText("" + resultArr[2]);
        randomball4_5.setText("" + resultArr[3]);
        randomball5_5.setText("" + resultArr[4]);
        randomball6_5.setText("" + resultArr[5]);

        randomText.setText("생성하였습니다!");

    }

    /*
    로또 번호 생성 부분
           */
    private int[] makeRandom() {
        List<Integer> arr = new ArrayList<>();
        for (int i = 1; i < 46; i++) {
            arr.add(i);
        }
        Collections.shuffle(arr);

        int[] resultArr = new int[6];
        for (int i = 0; i < 6; i++) {
            resultArr[i] = arr.get(i);
        }

        Arrays.sort(resultArr);

        return resultArr;
    }


}
