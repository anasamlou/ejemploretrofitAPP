package edu.upc.dsa.ejemplo_retrofit_app;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = (Button)findViewById(R.id.button);
        TextView txt = (TextView)findViewById(R.id.textView);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("APP","Button clicked");
                //Toast.makeText(MainActivity.this,"Button clicked", Toast.LENGTH_SHORT);


                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


                OkHttpClient client = new OkHttpClient.Builder()
                        .addInterceptor(interceptor)
                        .build();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://api.github.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(client)
                        .build();

                GitHubService service = retrofit.create(GitHubService.class);

                Call<List<Repo>> repos = service.listRepos("anasamlou");
                /*
                try {
                    List<Repo> result = repos.().body();

                }
                catch(Exception e) {
                    System.out.println("EXCEPTION:");
                    System.out.println(e.toString());
                }


                 */
                repos.enqueue(new Callback<List<Repo>>() {
                    @Override
                    public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                        List<Repo> repos = response.body();
                        String texto = "[";
                        for (Repo r : repos){
                            texto = texto + r.full_name + "";
                        }
                        texto = texto + "]";
                        txt.setText(texto);
                    }

                    @Override
                    public void onFailure(Call<List<Repo>> call, Throwable t) {
                        txt.setText("ERROR");
                    }
                });

            }

        });
    }


}
