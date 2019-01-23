package lottomaster.lunastratos.com.lottomaster;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;

public class LottoInfoAsyncTask extends AsyncTask<String, Void, HashMap> {

    private final String TAG = "TAG.Debug";
    TextView resultText;

    public LottoInfoAsyncTask(TextView textView) {
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

                //resultText.setText("이상한 회차 입력 금지");
            }else{
                //resultText.setText(mapObject.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
