package qi.com.demop;

import androidx.appcompat.app.AppCompatActivity;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.EditText;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
public class MainActivity extends AppCompatActivity{
    EditText et_ip, et_count, et_size, et_time;
    String ip, count, size, time;
    private String query;
    private String city;
    private String regionName;
    private String org;
    private boolean isFirst = true;
    private SharedPreferences pref;
    private Timer timer1;
    private int cc=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        pref = getSharedPreferences("isFirst", MODE_PRIVATE);//创建SharedPreferences对象
        isFirst = pref.getBoolean("isFirstIn", true);
        try {
            RetrofitUtils.getInstance().getMyServer().ping()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<PingBean>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(PingBean pingBean) {
                            query = pingBean.getQuery();
                            city = pingBean.getCity();
                            regionName = pingBean.getRegionName();
                            org = pingBean.getOrg();

                            // et_ip.setText(query);


                            Log.i("cc", query +"");
                        }
                    });
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        timer1 = new Timer();
        TimerTask task=new TimerTask() {
            @Override
            public void run() {
                List<String> list=new ArrayList<>();
                list.add("39.106.143.106");
                list.add("59.110.167.178");
                list.add("39.108.229.16");
                list.add("120.78.64.248");
                list.add("120.78.68.95");
                list.add("47.104.100.41");
                list.add("47.104.131.23");
                list.add("106.14.141.68");
                list.add("47.100.186.29");
                list.add("106.15.207.66");
                list.add("39.104.202.19");
                list.add("39.104.228.146");
                list.add("149.129.242.146");

                for (int i = 0; i <list.size() ; i++) {
                    Log.i("yes",list.get(i));
                    //ip = et_ip.getText().toString();
                    count = et_count.getText().toString();
                    size = et_size.getText().toString();
                    time = et_time.getText().toString();

                    String countCmd = " -c " + count + " ";
                    String sizeCmd = " -s " + size + " ";
                    String timeCmd = " -i " + time + " ";
                    String ip_adress = list.get(i);
                    String ping = "ping" + countCmd + timeCmd + sizeCmd + ip_adress;

                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, PingResult1.class);
                    // new一个Bundle对象，并将要传递的数据传入
                    Bundle bundle = new Bundle();
                    bundle.putString("ping", ping);
                    bundle.putString("ip", list.get(i));
                    bundle.putString("city",city);
                    bundle.putString("regionName",regionName);
                    bundle.putString("org",org);
                    bundle.putString("count", count);
                    bundle.putString("size", size);
                    bundle.putString("time", time);
                    bundle.putString("query",query);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }

        };
        timer1.schedule(task,4000);
    }

    private void init() {
        et_ip = (EditText) findViewById(R.id.edit_ip);
        et_count = (EditText) findViewById(R.id.edit_count);
        et_size = (EditText) findViewById(R.id.edit_size);
        et_time = (EditText) findViewById(R.id.edit_time);

    }

    @Override
    protected void onResume() {
        super.onResume();
        cc++;
        if (cc>1){
            new Thread(){
                @Override
                public void run() {
                    super.run();
                    SystemClock.sleep(1000*60*60);
                    List<String> list=new ArrayList<>();
                    list.add("39.106.143.106");
                    list.add("59.110.167.178");
                    list.add("39.108.229.16");
                    list.add("120.78.64.248");
                    list.add("120.78.68.95");
                    list.add("47.104.100.41");
                    list.add("47.104.131.23");
                    list.add("106.14.141.68");
                    list.add("47.100.186.29");
                    list.add("106.15.207.66");
                    list.add("39.104.202.19");
                    list.add("39.104.228.146");
                    list.add("149.129.242.146");
                    for (int i = 0; i <list.size() ; i++) {

                        //ip = et_ip.getText().toString();
                        count = et_count.getText().toString();
                        size = et_size.getText().toString();
                        time = et_time.getText().toString();

                        String countCmd = " -c " + count + " ";
                        String sizeCmd = " -s " + size + " ";
                        String timeCmd = " -i " + time + " ";
                        String ip_adress = list.get(i);
                        String ping = "ping" + countCmd + timeCmd + sizeCmd + ip_adress;

                        Intent intent = new Intent();
                        intent.setClass(MainActivity.this, PingResult1.class);
                        // new一个Bundle对象，并将要传递的数据传入
                        Bundle bundle = new Bundle();
                        bundle.putString("ping", ping);
                        bundle.putString("ip", list.get(i));
                        bundle.putString("city",city);
                        bundle.putString("regionName",regionName);
                        bundle.putString("org",org);
                        bundle.putString("count", count);
                        bundle.putString("size", size);
                        bundle.putString("time", time);
                        bundle.putString("query",query);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                }
            }.start();
        }
    }
}
