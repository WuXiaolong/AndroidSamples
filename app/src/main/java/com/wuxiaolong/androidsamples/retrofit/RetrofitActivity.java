package com.wuxiaolong.androidsamples.retrofit;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.wuxiaolong.androidsamples.BaseActivity;
import com.wuxiaolong.androidsamples.R;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public class RetrofitActivity extends BaseActivity {
    Retrofit mRetrofit;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        getWeather();
    }

    private void getWeather() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl("http://www.weather.com.cn/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiStores apiStores = mRetrofit.create(ApiStores.class);
        Call<WeatherJson> call = apiStores.getWeather("101010100");
        call.enqueue(new Callback<WeatherJson>() {
            @Override
            public void onResponse(Call<WeatherJson> call, Response<WeatherJson> response) {
                Log.i("wxl", "getCity=" + response.body().getWeatherinfo().getCity());
                Toast.makeText(context, response.body().getWeatherinfo().getCity(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<WeatherJson> call, Throwable t) {

            }

        });
    }

    private void getCarType() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl("http://wuxiaolong.me")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiStores apiStores = mRetrofit.create(ApiStores.class);
        ApiInfo apiInfo = new ApiInfo();
        ApiInfo.ApiInfoBean apiInfoBean = apiInfo.new ApiInfoBean();
        apiInfoBean.setApiKey("fba123");
        apiInfoBean.setApiName("android");
        apiInfo.setApiInfo(apiInfoBean);
        Call<ResponseBody> call = apiStores.getCarType(apiInfo);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String body = null;//获取返回体的字符串
                try {
                    body = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.i("wxl", "getCarType=" + body);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }

        });
    }

    /**
     * Call<T> get();必须是这种形式,这是2.0之后的新形式
     * 如果不需要转换成Json数据,可以用了ResponseBody;
     * 你也可以使用Call<GsonBean> get();这样的话,需要添加Gson转换器
     */
    public interface ApiStores {
        @GET("adat/sk/{cityId}.html")
        Call<WeatherJson> getWeather(@Path("cityId") String cityId);

        @POST("client/shipper/getCarType")
        Call<ResponseBody> getCarType(@Body ApiInfo apiInfo);
    }


    public class ApiInfo {


        private ApiInfoBean apiInfo;

        public ApiInfoBean getApiInfo() {
            return apiInfo;
        }

        public void setApiInfo(ApiInfoBean apiInfo) {
            this.apiInfo = apiInfo;
        }

        public class ApiInfoBean {
            private String apiName;
            private String apiKey;

            public String getApiName() {
                return apiName;
            }

            public void setApiName(String apiName) {
                this.apiName = apiName;
            }

            public String getApiKey() {
                return apiKey;
            }

            public void setApiKey(String apiKey) {
                this.apiKey = apiKey;
            }
        }
    }

    public class WeatherJson {


        private WeatherinfoEntity weatherinfo;

        public void setWeatherinfo(WeatherinfoEntity weatherinfo) {
            this.weatherinfo = weatherinfo;
        }

        public WeatherinfoEntity getWeatherinfo() {
            return weatherinfo;
        }

        public class WeatherinfoEntity {
            private String city;
            private String cityid;
            private String temp;
            private String WD;
            private String WS;
            private String SD;
            private String WSE;
            private String time;
            private String isRadar;
            private String Radar;
            private String njd;
            private String qy;

            public void setCity(String city) {
                this.city = city;
            }

            public void setCityid(String cityid) {
                this.cityid = cityid;
            }

            public void setTemp(String temp) {
                this.temp = temp;
            }

            public void setWD(String WD) {
                this.WD = WD;
            }

            public void setWS(String WS) {
                this.WS = WS;
            }

            public void setSD(String SD) {
                this.SD = SD;
            }

            public void setWSE(String WSE) {
                this.WSE = WSE;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public void setIsRadar(String isRadar) {
                this.isRadar = isRadar;
            }

            public void setRadar(String Radar) {
                this.Radar = Radar;
            }

            public void setNjd(String njd) {
                this.njd = njd;
            }

            public void setQy(String qy) {
                this.qy = qy;
            }

            public String getCity() {
                return city;
            }

            public String getCityid() {
                return cityid;
            }

            public String getTemp() {
                return temp;
            }

            public String getWD() {
                return WD;
            }

            public String getWS() {
                return WS;
            }

            public String getSD() {
                return SD;
            }

            public String getWSE() {
                return WSE;
            }

            public String getTime() {
                return time;
            }

            public String getIsRadar() {
                return isRadar;
            }

            public String getRadar() {
                return Radar;
            }

            public String getNjd() {
                return njd;
            }

            public String getQy() {
                return qy;
            }
        }
    }
}
