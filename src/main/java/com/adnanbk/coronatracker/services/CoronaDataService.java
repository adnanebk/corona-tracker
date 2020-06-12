package com.adnanbk.coronatracker.services;

import com.adnanbk.coronatracker.models.Statistic;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class CoronaDataService {


    private Statistic stat;
    private List<Statistic> worldStatList=new ArrayList<>();
    private final OkHttpClient client;
    private final ObjectMapper mapper;
    private final String URL="https://covid-19-data.p.rapidapi.com/country?format=json&name=";

    public CoronaDataService() {
        client = new OkHttpClient();
        mapper=new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private Statistic getLatestStatisticByCountry(String countryName) throws IOException {
       countryName= Objects.requireNonNullElse(countryName,"morocco");

        String jsonResponse = BuildRequest(URL+countryName);

         jsonResponse = jsonResponse.replace("]","").replace("[","");

        return mapper.readValue(jsonResponse, Statistic.class);
    }

    @PostConstruct
    @Scheduled(cron = "* * 12 * * *")
    public void fetchDataForMorocco1() throws IOException {
        //moroccoStatisDuringMonth=null;
        Statistic moroccoStatLatest = getLatestStatisticByCountry("morocco");

        Statistic moroccoStatYesterday= GetStatByDateAndName(LocalDate.now().minusDays(1),"morocco");

        stat =new Statistic(moroccoStatYesterday,moroccoStatLatest);
        System.out.println("");
    }

    @Scheduled(cron = "* * 20 * * *")
    public void fetchDataForMorocco2() throws IOException {
        //moroccoStatisDuringMonth=null;
        Statistic moroccoStatLatest=  getLatestStatisticByCountry("morocco");

        stat =new Statistic(stat,moroccoStatLatest);

        //moroccoStatisList= Arrays.asList(mapper.readValue(jsonResponse, MoroccoStatis[].class));

    }

    private String BuildRequest(String url) throws IOException {
        Request request= new Request.Builder()
                .url(url)
                .get()
                .addHeader("x-rapidapi-host", "covid-19-data.p.rapidapi.com")
                .addHeader("x-rapidapi-key", "964264ef37mshdf7d883d364eb48p1c2079jsn572ed56f087a")
                .build();
   return client.newCall(request).execute().body().string();
    }


   // @Scheduled(fixedDelay = 14400000)
    //@PostConstruct
    public void fetchDataForTheWorld() throws IOException {

        //String jsonResponse = BuildRequest("https://covid-19-data.p.rapidapi.com/country/all?format=json");

        //String jsonResponse = client.newCall(request).execute().body().string();

        //ObjectMapper mapper = new ObjectMapper();

     // worldStatList = Arrays.asList(mapper.readValue(jsonResponse, Statistic[].class));

    }
    public Statistic GetMoroccoStatData(){

        return Objects.requireNonNullElse(stat,new Statistic());
    }


    @Cacheable("stats")
    public Map<String, Statistic> GetStatList(LocalDate date, String countryName,int startDay) throws IOException {
         /*   if(moroccoStatisDuringMonth!=null && date.isEqual(LocalDate.now()) && countryName.equals("morocco"))
                return moroccoStatisDuringMonth;*/
        Map<String, Statistic> statisDuringMonth=new LinkedHashMap<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMMM");

        Statistic statisticOfToday;
        if(date.isEqual(LocalDate.now()))
            statisticOfToday=getLatestStatisticByCountry(countryName);
        else statisticOfToday= GetStatByDateAndName(date,countryName);

        for(int i=startDay;i>=1;i--){
            statisDuringMonth.put(date.minusDays(i).format(formatter),new Statistic(GetStatByDateAndName(date.minusDays(i+1),countryName),GetStatByDateAndName(date.minusDays(i),countryName)));

        }

   /*     statisDuringMonth.put(date.minusDays(16).format(formatter),new Statistic(GetStatByDateAndName(date.minusDays(17),countryName),GetStatByDateAndName(date.minusDays(16),countryName)));
        statisDuringMonth.put(date.minusDays(14).format(formatter),new Statistic(GetStatByDateAndName(date.minusDays(15),countryName),GetStatByDateAndName(date.minusDays(14),countryName)));
        statisDuringMonth.put(date.minusDays(12).format(formatter),new Statistic(GetStatByDateAndName(date.minusDays(13),countryName),GetStatByDateAndName(date.minusDays(12),countryName)));
        statisDuringMonth.put(date.minusDays(10).format(formatter),new Statistic(GetStatByDateAndName(date.minusDays(11),countryName),GetStatByDateAndName(date.minusDays(10),countryName)));
        statisDuringMonth.put(date.minusDays(8).format(formatter),new Statistic(GetStatByDateAndName(date.minusDays(9),countryName),GetStatByDateAndName(date.minusDays(8),countryName)));

        statisDuringMonth.put(date.minusDays(6).format(formatter),new Statistic(GetStatByDateAndName(date.minusDays(7),countryName),GetStatByDateAndName(date.minusDays(6),countryName)));
        statisDuringMonth.put(date.minusDays(4).format(formatter),new Statistic(GetStatByDateAndName(date.minusDays(5),countryName),GetStatByDateAndName(date.minusDays(4),countryName)));
        statisDuringMonth.put(date.minusDays(2).format(formatter),new Statistic(GetStatByDateAndName(date.minusDays(3),countryName),GetStatByDateAndName(date.minusDays(2),countryName)));
       */
        //statisDuringMonth.put(date.format(formatter),new Statistic(GetStatByDateAndName(date.minusDays(1),countryName),statisticOfToday))

        statisDuringMonth.put(date.format(formatter),new Statistic(GetStatByDateAndName(date.minusDays(1),countryName),statisticOfToday));

      /*  if(moroccoStatisDuringMonth==null)
            moroccoStatisDuringMonth=new LinkedHashMap<>(statisDuringMonth);*/
        return  statisDuringMonth;

    }


    public Statistic GetStatByDateAndName(LocalDate date, String name) throws IOException {
        String jsonResponse = BuildRequest("https://covid-19-data.p.rapidapi.com/report/country/name?date-format=YYYY-MM-DD&format=json&date="+date+"&name="+name);
        //String jsonResponse = client.newCall(request).execute().body().string();
        jsonResponse =jsonResponse.substring(jsonResponse.lastIndexOf("{"),jsonResponse.indexOf("}")+1) ;
        Statistic statistic=mapper.readValue(jsonResponse, Statistic.class);
        return statistic;
    }

    public List<Statistic> GetWorldStatList() {
        return worldStatList;
    }
}
