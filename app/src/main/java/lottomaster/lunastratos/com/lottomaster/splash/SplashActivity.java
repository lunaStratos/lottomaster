package lottomaster.lunastratos.com.lottomaster.splash;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

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
import lottomaster.lunastratos.com.lottomaster.MainActivity;

public class SplashActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // MainActivity.class 자리에 다음에 넘어갈 액티비티를 넣어주기
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        LottoLoad();

    }

    public void LottoLoad() {
        LoadFirstTimeAsync task = new LoadFirstTimeAsync();
        task.execute();
    }

    private class LoadFirstTimeAsync extends AsyncTask<Void, Void, HashMap> {
        private final String TAG = "TAG.Debug";

        public LoadFirstTimeAsync() {
            super();
        }

        /**
         * @param strings : String []
         *                [0]: URL Address
         *                [1]:  now or before
         * @return JSONObject
         */

        @Override
        protected HashMap doInBackground(Void... Voids) {

            HashMap<String, Object> mapResult = new HashMap();


            // Gradle에 implementation 'org.jsoup:jsoup:1.11.3' 추가 후 Sync
            try {
                //Start
                Document doc = Jsoup.connect("https://www.dhlottery.co.kr/gameResult.do?method=byWin").get();

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


            return mapResult;
        }

        @Override
        protected void onPostExecute(HashMap mapObject) {
            super.onPostExecute(mapObject);
            Log.d(TAG, mapObject.toString());

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("HashMap", mapObject);
            startActivity(intent);
            finish();

        }
    }
}
