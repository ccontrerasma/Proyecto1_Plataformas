package com.example.proyecto1;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.MapView;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

public class BuscarActivity extends AppCompatActivity {

    private MapView mapView;  // Mapa

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar);

        // Configuración de OSMDroid (se carga la configuración en la Activity)
        Configuration.getInstance().load(getApplicationContext(), PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));

        // Inicializar el MapView
        mapView = findViewById(R.id.mapView);

        // Configuración básica del mapa
        mapView.setTileSource(org.osmdroid.tileprovider.tilesource.TileSourceFactory.MAPNIK);
        mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);

        // Establecer el punto de inicio (Ejemplo: Arequipa)
        GeoPoint startPoint = new GeoPoint(-16.409047, -71.537451);  // Coordenadas de Arequipa
        mapView.getController().setZoom(10);
        mapView.getController().setCenter(startPoint);

        // Cargar y agregar edificaciones al mapa desde el archivo JSON
        List<Edificacion> edificaciones = cargarEdificaciones();
        if (edificaciones != null) {
            agregarMarcadores(edificaciones);
        } else {
            Toast.makeText(this, "No se pudieron cargar las edificaciones", Toast.LENGTH_SHORT).show();
        }

    }

    private List<Edificacion> cargarEdificaciones() {
        try {
            // Acceder a los archivos assets
            AssetManager assetManager = getAssets();
            InputStream inputStream = assetManager.open("edificaciones.json");

            // Convertir el InputStream en un String usando InputStreamReader
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

            // Usar Gson para parsear el JSON en una lista de objetos Edificacion
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Edificacion>>(){}.getType();
            return gson.fromJson(inputStreamReader, listType);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void agregarMarcadores(List<Edificacion> edificaciones) {
        for (Edificacion edificacion : edificaciones) {
            GeoPoint punto = new GeoPoint(edificacion.getLatitud(), edificacion.getLongitud());

            // Crear marcador
            Marker marcador = new Marker(mapView);
            marcador.setPosition(punto);
            marcador.setTitle(edificacion.getTitulo());
            marcador.setSubDescription(edificacion.getDescripcion());

            // Agregar el marcador al mapa
            mapView.getOverlays().add(marcador);

            // Configurar el listener de clic en el marcador
            marcador.setOnMarkerClickListener((m, mapView1) -> {
                m.showInfoWindow();  // Muestra la ventana de información
                return true;
            });
        }
    }
}