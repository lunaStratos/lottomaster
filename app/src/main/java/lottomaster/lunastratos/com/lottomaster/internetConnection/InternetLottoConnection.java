package lottomaster.lunastratos.com.lottomaster.internetConnection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;

public class InternetLottoConnection {


    public HashMap getNowLotto(){
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

}
