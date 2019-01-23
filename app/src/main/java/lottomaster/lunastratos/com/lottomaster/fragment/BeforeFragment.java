package lottomaster.lunastratos.com.lottomaster.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

import lottomaster.lunastratos.com.lottomaster.LottoInfoAsyncTask;
import lottomaster.lunastratos.com.lottomaster.R;

public class BeforeFragment extends Fragment {
    EditText insert_beforeNum;

    Button beforeClickBtn;
    TextView beforeText;

    TextView beforeBall1;
    TextView beforeBall2;
    TextView beforeBall3;
    TextView beforeBall4;
    TextView beforeBall5;
    TextView beforeBall6;
    TextView beforeBonus;

    TextView before_firstAccumamnt;
    TextView beforefirstHowTo;
    TextView beforefirstWinamnt;
    TextView beforefirstPrzwnerCo;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_before, container, false);

        beforeText = v.findViewById(R.id.beforeText);
        beforeClickBtn = v.findViewById(R.id.beforeLottoBtn);
        insert_beforeNum = v.findViewById(R.id.insert_beforeNum);

        beforeBall1 = v.findViewById(R.id.beforeBall1);
        beforeBall2 = v.findViewById(R.id.beforeBall2);
        beforeBall3 = v.findViewById(R.id.beforeBall3);
        beforeBall4 = v.findViewById(R.id.beforeBall4);
        beforeBall5 = v.findViewById(R.id.beforeBall5);
        beforeBall6 = v.findViewById(R.id.beforeBall6);
        beforeBonus = v.findViewById(R.id.beforeBonus);

        before_firstAccumamnt = v.findViewById(R.id.before_firstAccumamnt);
        beforefirstHowTo = v.findViewById(R.id.beforefirstHowTo);
        beforefirstWinamnt = v.findViewById(R.id.beforefirstWinamnt);
        beforefirstPrzwnerCo = v.findViewById(R.id.beforefirstPrzwnerCo);

        beforeBonus = v.findViewById(R.id.beforeBonus);

        beforeClickBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // "http://hangang.dkserver.wo.tc"
                if (insert_beforeNum.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "숫자를 입력해 주세요.", Toast.LENGTH_LONG).show();
                    return;
                }

                String address = "https://www.dhlottery.co.kr/gameResult.do?method=byWin&drwNo=" + insert_beforeNum.getText();
                String[] beforeLottoAddress = {address, "now"};
                LottoAsyncTask task = new LottoAsyncTask(beforeText);
                task.execute(beforeLottoAddress);


            }
        });

        return v;

    }



    public class LottoAsyncTask extends AsyncTask<String, Void, HashMap> {

        private final String TAG = "TAG.Debug";
        TextView resultText;

        public LottoAsyncTask(TextView textView) {
            super();
            this.resultText = textView;
        }

        /**
         * @param strings : String []
         *                [0]: URL Address
         *                [1]:  now or before
         * @return JSONObject
         */

        @Override
        protected HashMap doInBackground(String... strings) {

            String urlAddress = strings[0];
            BufferedReader buf = null;
            StringBuilder sb = new StringBuilder();
            String str;
            HashMap<String, Object> mapResult = new HashMap();


            try {

                if (strings[1].equals("now")) {

                    // Gradle에 implementation 'org.jsoup:jsoup:1.11.3' 추가 후 Sync
                    try {
                        //Start
                        Document doc = Jsoup.connect(urlAddress).get();

                        //로또 회차
                        String drwNo = doc.select("div.win_result h4 Strong").text();
                        mapResult.put("drwNo", drwNo);

                        //당첨날짜
                        String dateCall = doc.select(".win_result p.desc").text().replace("추첨", "").replaceAll(" ", "");
                        String convertDate = dateCall.replace("년 ", "-").replace("월 ", "-").replace("일 ", "");
                        mapResult.put("drwNoDate", convertDate);

                        //로또 당첨 액수와 수
                        Elements lottoMoneys = doc.select(".tbl_data.tbl_data_col tbody tr").eq(1);
                        String firstAccumamnt = lottoMoneys.select("td").eq(1).text().replace("원", "").replaceAll(" ", "");
                        String firstPrzwnerCo = lottoMoneys.select("td").eq(2).text().replace("원", "").replaceAll(" ", "");
                        String firstWinamnt = lottoMoneys.select("td").eq(3).text().replace("원", "").replaceAll(" ", "");
                        String firstHowTo = lottoMoneys.select("td").eq(5).text().replace("1등", "").replace("원", "").replaceAll(" ", "");

                        mapResult.put("firstAccumamnt", firstAccumamnt);
                        mapResult.put("firstPrzwnerCo", firstPrzwnerCo);
                        mapResult.put("firstWinamnt", firstWinamnt);
                        mapResult.put("firstHowTo", firstHowTo);


                        Elements getLottoNum = doc.select(".win_result div.nums div.num p span");
                        int lottoCount = 1;
                        for (Element element : getLottoNum) {
                            String mapName = "drwtNo" + lottoCount;

                            if (lottoCount == 7) { //보너스 번호
                                mapResult.put("bnusNo", element.ownText());
                            } else {
                                mapResult.put(mapName, element.ownText());
                            }

                            lottoCount++;
                        }


                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else if (strings[1].equals("before")) {
                    URL url = new URL(urlAddress);
                    URLConnection con = url.openConnection();
                    con.setRequestProperty("X-HTTP-Method-Override", "PATCH");
                    buf = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    while ((str = buf.readLine()) != null) {
                        sb.append(str);
                    }

                    buf.close();

                    try {

                        mapResult = new ObjectMapper().readValue(sb.toString(), HashMap.class);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return mapResult;
        }

        @Override
        protected void onPostExecute(HashMap mapObject) {
            super.onPostExecute(mapObject);

            Log.i(TAG, mapObject.toString());
            try {

                if(mapObject.get("firstAccumamnt").equals("0")){
//                    resultText.setText("이상한 회차 입력 금지");
                    Toast.makeText(getContext(), "이상한 회차 입력 금지", Toast.LENGTH_LONG).show();
                }else{
//                    resultText.setText(mapObject.toString());

                    beforeBall1.setText((String) mapObject.get("drwtNo1"));
                    beforeBall2.setText((String) mapObject.get("drwtNo2"));
                    beforeBall3.setText((String) mapObject.get("drwtNo3"));
                    beforeBall4.setText((String) mapObject.get("drwtNo4"));
                    beforeBall5.setText((String) mapObject.get("drwtNo5"));
                    beforeBall6.setText((String) mapObject.get("drwtNo6"));
                    beforeBonus.setText((String) mapObject.get("bnusNo"));

                    before_firstAccumamnt.setText((String) mapObject.get("firstAccumamnt"));
                    beforefirstHowTo.setText((String) mapObject.get("firstHowTo"));
                    beforefirstPrzwnerCo.setText((String) mapObject.get("firstHowTo"));
                    beforefirstWinamnt.setText((String) mapObject.get("firstWinamnt"));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

}
