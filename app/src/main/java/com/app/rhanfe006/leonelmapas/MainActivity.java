package com.app.rhanfe006.leonelmapas;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;


public class MainActivity extends Activity{

    private Button btnActualizar, btnDesactivar;
    private TextView lblLatitud, lblLongitud, lblPrecision, lblEstadoProveedor;

    private LocationManager locManager;
    private LocationListener locListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnActualizar = (Button) findViewById(R.id.BtnActualizar);
        btnDesactivar = (Button) findViewById(R.id.BtnDesactivar);
        lblLatitud = (TextView) findViewById(R.id.LblPosLatitud);
        lblLongitud = (TextView) findViewById(R.id.LblPosLongitud);
        lblPrecision = (TextView) findViewById(R.id.LblPrecision);
        lblEstadoProveedor = (TextView) findViewById(R.id.LblEstado);

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comenzarLocalizacion();
            }
        });

        btnDesactivar.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                locManager.removeUpdates(locListener);
            }
        });

    }

    private void mostrarPosicion (Location loc) {
        if (loc != null)
        {
            lblLatitud.setText("Latitud: " + String.valueOf(loc.getLatitude()));
            lblLongitud.setText("Longitud" + String.valueOf(loc.getLongitude()));
            lblPrecision.setText("Presicion" +String.valueOf(loc.getAccuracy()));
        }
        else
        {

            lblLatitud.setText("Latitud:(Sin datos)");
            lblLongitud.setText("Longitud: (sin datos)");
            lblPrecision.setText("Presicion: (sin datos)");
        }
    }

    private void comenzarLocalizacion(){
        //OBTENEMOS UNA REFERENCIA AL L
        locManager =
        (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //obtenemos la ultima posicion conocida

        Location loc =
        locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        //mostraos la ultima posicion
        mostrarPosicion(loc);

        //nos registramos para recibir actualizaciones de la posicion

        locListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                mostrarPosicion(location);

            }

            @Override
            public void onProviderDisabled(String provider) {
                lblEstadoProveedor.setText("Provider of");

            }

            @Override
            public void onProviderEnabled(String provider) {
                lblEstadoProveedor.setText("Provider on");

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

                lblEstadoProveedor.setText("Provider status:" + status);
            }
        };
        locManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 30000, 0, locListener);



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
