package com.ismek.ferhat.hikayeuygulamas;

import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Objects;

class Hikayeler {
    public String Baslik;
    public String Tur;
    public String Detail;
}

public class MainActivity extends AppCompatActivity {

    Hikayeler[] hikayeler = new Hikayeler[3];
    Button btnSearch;
    EditText editTextSearch;
    TextView txtDetail, txtTitle;
    RadioGroup groupType;
    RadioButton radioType = null;
    ScrollView scrollView;
    AlertDialog.Builder dialog;
    boolean isWind =false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Hikayeler hikaye1 = new Hikayeler();
        Hikayeler hikaye2 = new Hikayeler();
        Hikayeler hikaye3 = new Hikayeler();

        hikaye1.Baslik = getResources().getString(R.string.hikaye1Baslik);
        hikaye2.Baslik = getResources().getString(R.string.Hikaye2Baslik);
        hikaye3.Baslik = getResources().getString(R.string.Hikaye3Baslik);

        hikaye1.Tur = getResources().getString(R.string.Hikaye1Tur);
        hikaye2.Tur = getResources().getString(R.string.Hikaye3Tur);
        hikaye3.Tur = getResources().getString(R.string.Hikaye3Tur);

        hikaye1.Detail = getResources().getString(R.string.hikaye1);
        hikaye2.Detail = getResources().getString(R.string.Hikaye2);
        hikaye3.Detail = getResources().getString(R.string.Hikaye3);

        hikayeler[0] = hikaye1;
        hikayeler[1] = hikaye2;
        hikayeler[2] = hikaye3;


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

dialog = new AlertDialog.Builder(MainActivity.this);
        btnSearch = findViewById(R.id.btnSearch);
        editTextSearch = findViewById(R.id.editTxtSearch);
        txtDetail = findViewById(R.id.txtDetail);
        txtTitle = findViewById(R.id.txtTitle);
        groupType = findViewById(R.id.groupType);
        scrollView = findViewById(R.id.scrollView);


        groupType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioType = findViewById(checkedId);
            }
        });


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = editTextSearch.getText().toString();
                if (searchText.isEmpty() && radioType==null) {

                    dialog.setTitle("Hata");
                    dialog.setMessage("Lütfen Aramak istediğiniz kelimeyi veya Tür seçiniz.");
                    dialog.setNegativeButton("Kapat", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    dialog.show();
                }
                else if(searchText.isEmpty()){
                    for (int i = 0; i<hikayeler.length;i++){
                        if (hikayeler[i].Tur.equals(radioType.getText().toString()))
                        {
                            Bulundu(hikayeler[i]);
                            isWind = true;
                            break;
                        }
                    }
                    if (!isWind){
                        Bulunamadi();
                    }
                }
                else if (radioType == null){
                    for (int i = 0; i<hikayeler.length;i++){
                        if (hikayeler[i].Detail.contains(searchText))
                        {
                            Bulundu(hikayeler[i]);
                            isWind = true;

                            break;
                        }
                    }
                    if (!isWind){
                        Bulunamadi();
                    }
                }
                else{
                    for (int i = 0; i<hikayeler.length;i++){
                        if (hikayeler[i].Detail.contains(searchText) && hikayeler[i].Tur.equals(radioType.getText().toString()))
                        {
                            Bulundu(hikayeler[i]);
                            isWind = true;

                            break;
                        }
                    }
                    if (!isWind){
                        Bulunamadi();
                    }
                }
            }
        });
    }
    public  void Bulundu(final Hikayeler hikaye){
        dialog.setTitle("Bilgi");
        dialog.setMessage(hikaye.Baslik + " isimli hikayeyi okumak istermisiniz");
        dialog.setPositiveButton("Oku", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                scrollView.setVisibility(View.VISIBLE);
                txtTitle.setVisibility(View.VISIBLE);
                txtDetail.setText( hikaye.Detail);
                txtTitle.setText( hikaye.Baslik);
                editTextSearch.setText("");
                groupType.clearCheck();
            }
        });
        dialog.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.show();
    }
    public void Bulunamadi(){
        dialog.setTitle("Uyarı");
        dialog.setMessage("Aradığınız kriterlere göre hikaye bulunamadı");
        dialog.setNegativeButton("Kapat", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.show();
    }
}
