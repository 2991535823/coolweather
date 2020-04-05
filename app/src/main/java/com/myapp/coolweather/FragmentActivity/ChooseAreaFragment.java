package com.myapp.coolweather.FragmentActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.myapp.coolweather.Activity.WeatherActivity;
import com.myapp.coolweather.ConstString;
import com.myapp.coolweather.R;
import com.myapp.coolweather.db.City;
import com.myapp.coolweather.db.County;
import com.myapp.coolweather.db.Province;
import com.myapp.coolweather.util.HttpUtil;
import com.myapp.coolweather.util.Utility;

import org.jetbrains.annotations.NotNull;
import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ChooseAreaFragment extends Fragment {
    private static final int LEVEL_PROVINCE = 0;
    private static final int LEVEL_CITY = 1;
    private static final int LEVEL_COUNTY = 2;
    private ProgressDialog progressDialog;
    private TextView titleText;
    private Button backButton;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> dataList =new ArrayList<>();
    private List<Province> provinceList;
    private List<City> cityList;
    private List<County> countyList;
    private Province selectedProvince;
    private City selectedCity;
    private County selectedCounty;
    private int selectedLevel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.choose_area,container,false);
        titleText=view.findViewById(R.id.title_text);
        backButton=view.findViewById(R.id.back_button);
        listView=view.findViewById(R.id.list_view);
        adapter=new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,dataList);
        listView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                if (selectedLevel==LEVEL_PROVINCE){
                    selectedProvince=provinceList.get(i);
                    //
                    queryCities();

                }else if(selectedLevel==LEVEL_CITY){
                    selectedCity=cityList.get(i);
                    //
                    queryCounties();
                }else if(selectedLevel==LEVEL_COUNTY){
                    String weatherId=countyList.get(i).getWeatherId();
                    Intent intent=new Intent(getActivity(), WeatherActivity.class);
                    intent.putExtra("weather_id",weatherId);
                    startActivity(intent);
                    getActivity().finish();
                }

            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedLevel==LEVEL_COUNTY){
                    queryCities();
                }else if(selectedLevel==LEVEL_CITY){
                    queryProvinces();
                }
            }
        });
        queryProvinces();
    }

    private void queryProvinces(){
        titleText.setText("中国");
        backButton.setClickable(false);
        provinceList= DataSupport.findAll(Province.class);

        if (provinceList.size()>0){
            Log.d("dialogsql", "queryCities: from sql success");
            dataList.clear();
            for (Province province:provinceList){
                dataList.add(province.getProvinceName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            selectedLevel=LEVEL_PROVINCE;
        }
        else {
            String address= ConstString.SERVER_PROVINCE_ADDRESS;
            //服务器查询
            queryFromServer(address,"province");
        }
    }
    private void queryCities(){
        titleText.setText(selectedProvince.getProvinceName());
        backButton.setClickable(true);
        //有问题
        cityList=DataSupport.where("provinceid = ?",String.valueOf(selectedProvince.getId())).find(City.class);
        if (cityList.size()>0){
            dataList.clear();
            for (City city:cityList){
                dataList.add(city.getCityName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            selectedLevel=LEVEL_CITY;
        }else {
            Log.d("dialogsql", "queryCities: from sql fail");
            String address=ConstString.SERVER_PROVINCE_ADDRESS+"/"+selectedProvince.getProvinceCode();
            //
            queryFromServer(address,"city");
        }

    }
    private void queryCounties(){
        titleText.setText(selectedCity.getCityName());
        backButton.setClickable(true);
        countyList=DataSupport.where("cityid = ?",String.valueOf(selectedCity.getId())).find(County.class);
        if (countyList.size()>0){
            dataList.clear();
            for (County county:countyList){
                dataList.add(county.getCountyName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            selectedLevel=LEVEL_COUNTY;
        }else {
            int provinceCode=selectedProvince.getProvinceCode();
            int cityCode=selectedCity.getCityCode();
            String address=ConstString.SERVER_PROVINCE_ADDRESS+"/"+provinceCode+"/"+cityCode;
            //服务器查询
            queryFromServer(address,"county");
        }

    }
    private void queryFromServer(String address,final String type){
        //进度
        showProgressDialog();//
        Log.d("dialog", "queryFromServer"+address);
        HttpUtil.sendOkHttpRequest(address, new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //关闭进度条
                        closeProgressDialog();
                        Log.d("dialog", "查询失败");
                        //通知失败
                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responseText=response.body().string();
                Log.d("response", "onResponse: msg"+responseText);
                Log.d("dialogsql", "onResponse: "+type);
                boolean result=false;
                if ("province".equals(type)){
                    result= Utility.handleProvinceResponse(responseText);
                }else if("city".equals(type)){
                    result=Utility.handleCityResponse(responseText,selectedProvince.getId());
                }else if("county".equals(type)){
                    result=Utility.handleCountyResponse(responseText,selectedCity.getId());
                }
                if (result){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //关闭进度
                            closeProgressDialog();
                            Log.d("dialog", "数据库成功，关闭失败 ");
                            //通知完成
                            if("province".equals(type)){
                                queryProvinces();
                            }else if("city".equals(type)){
                                queryCities();
                            }else if("county".equals(type)){
                                queryCounties();
                            }
                        }
                    });
                }else {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            Log.d("dialog", "数据库可能有问题");
                        }
                    });

                }
            }
        });
    }
    private void showProgressDialog(){
        if (progressDialog==null){
            progressDialog=new ProgressDialog(getActivity());
            progressDialog.setMessage("正在加载数据，耐心等候哦");
            progressDialog.setCancelable(false);
            }
        progressDialog.show();
        }

    private void closeProgressDialog(){
        if (progressDialog!=null){
            progressDialog.dismiss();
        }
    }
}


