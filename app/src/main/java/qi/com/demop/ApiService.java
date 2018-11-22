package qi.com.demop;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface ApiService {
    @GET("json")
    Observable<PingBean> ping();

}
