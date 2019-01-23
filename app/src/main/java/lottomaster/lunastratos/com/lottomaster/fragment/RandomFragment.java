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

        makeRandomBtn = v.findViewById(R.id.makeRandomBtn);

        int[] resultArr = makeRandom();
        setRandomLottoNum(resultArr);

        makeRandomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setRandomLottoNum(makeRandom());
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
    private void setRandomLottoNum(int[] resultArr) {
        String drawNum1 = resultArr[0] + "";
        String drawNum2 = resultArr[1] + "";
        String drawNum3 = resultArr[2] + "";
        String drawNum4 = resultArr[3] + "";
        String drawNum5 = resultArr[4] + "";
        String drawNum6 = resultArr[5] + "";

        randomball1.setText(drawNum1);
        randomball2.setText(drawNum2);
        randomball3.setText(drawNum3);
        randomball4.setText(drawNum4);
        randomball5.setText(drawNum5);
        randomball6.setText(drawNum6);
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
