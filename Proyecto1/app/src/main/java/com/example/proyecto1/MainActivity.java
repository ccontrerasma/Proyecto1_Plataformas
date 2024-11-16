package com.example.proyecto1;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private BuildingViewModel viewModel;
    private BuildingView buildingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildingView = new BuildingView(this);

        // Configurar ViewModel
        viewModel = new ViewModelProvider(this).get(BuildingViewModel.class);
        viewModel.getAmbientes().observe(this, ambientes -> {
            // Actualización de View si es necesario
        });

        setContentView(R.layout.activity_main2); // Establecer layout si es necesario
        setContentView(buildingView);  // Establecer el View personalizado con los cambios
    }

    public class BuildingView extends View {
        private Paint paint, pointPaint, textPaint, buttonPaint;
        private List<Path> ambientes; // Lista para almacenar las formas de cada ambiente
        private List<Region> ambienteRegions; // Lista para almacenar las regiones de cada ambiente
        private String[] labels = {"Sala Principal", "Patio", "Capilla", "Altar"};
        private float[][] puntos; // Coordenadas para los puntos de referencia
        private RectF buttonRect; // Área para el botón de "Agregar Comentario"

        public BuildingView(Context context) {
            super(context);
            init();
        }

        private void init() {
            paint = new Paint();
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(5);

            textPaint = new Paint();
            textPaint.setColor(Color.BLACK);
            textPaint.setTextSize(40);

            pointPaint = new Paint();
            pointPaint.setColor(Color.parseColor("#FFA500")); // Naranja
            pointPaint.setStyle(Paint.Style.FILL);

            buttonPaint = new Paint();
            buttonPaint.setColor(Color.GRAY); // Color de fondo del botón

            // Crear formas irregulares para cada ambiente (utilizando Path)
            ambientes = new ArrayList<>();
            ambienteRegions = new ArrayList<>();

            // Crear Path y Region para Sala Principal
            Path salaPrincipal = new Path();
            salaPrincipal.moveTo(100, 100);
            salaPrincipal.lineTo(400, 100);
            salaPrincipal.lineTo(400, 300);
            salaPrincipal.lineTo(250, 300);
            salaPrincipal.lineTo(250, 450);
            salaPrincipal.lineTo(100, 450);
            salaPrincipal.close();
            ambientes.add(salaPrincipal);
            ambienteRegions.add(createRegionFromPath(salaPrincipal));

            // Crear Path y Region para Patio
            Path patio = new Path();
            patio.moveTo(400, 100);
            patio.lineTo(700, 100);
            patio.lineTo(700, 300);
            patio.lineTo(400, 300);
            patio.close();
            ambientes.add(patio);
            ambienteRegions.add(createRegionFromPath(patio));

            // Crear Path y Region para Capilla
            Path capilla = new Path();
            capilla.moveTo(100, 450);
            capilla.lineTo(250, 450);
            capilla.lineTo(250, 650);
            capilla.lineTo(100, 650);
            capilla.close();
            ambientes.add(capilla);
            ambienteRegions.add(createRegionFromPath(capilla));

            // Crear Path y Region para Altar
            Path altar = new Path();
            altar.moveTo(250, 450);
            altar.lineTo(400, 450);
            altar.lineTo(400, 650);
            altar.lineTo(250, 650);
            altar.close();
            ambientes.add(altar);
            ambienteRegions.add(createRegionFromPath(altar));

            // Coordenadas de los puntos de referencia en cada ambiente
            puntos = new float[][] {
                    {200, 200}, // Sala Principal
                    {550, 200}, // Patio
                    {150, 550}, // Capilla
                    {300, 550}  // Altar
            };

            // Definir el área del botón
            buttonRect = new RectF(50, 750, 350, 850);  // Cambia estos valores según el tamaño que desees
        }

        private Region createRegionFromPath(Path path) {
            Region region = new Region();
            RectF bounds = new RectF();
            path.computeBounds(bounds, true);
            region.setPath(path, new Region((int) bounds.left, (int) bounds.top, (int) bounds.right, (int) bounds.bottom));
            return region;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            // Dibujar cada ambiente
            for (int i = 0; i < ambientes.size(); i++) {
                canvas.drawPath(ambientes.get(i), paint);
                float labelX = puntos[i][0] - 50;  // Ajuste de posición del texto
                float labelY = puntos[i][1] - 20;
                canvas.drawText(labels[i], labelX, labelY, textPaint);
            }

            // Dibujar puntos de referencia
            for (float[] punto : puntos) {
                canvas.drawCircle(punto[0], punto[1], 10, pointPaint);
            }

            // Dibujar el botón
            canvas.drawRoundRect(buttonRect, 30, 20, buttonPaint); // Rectángulo con bordes redondeados
            canvas.drawText("Comentar", 100, 810, textPaint); // Texto del botón
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                float x = event.getX();
                float y = event.getY();

                // Detectar en qué ambiente se hizo clic
                for (int i = 0; i < ambienteRegions.size(); i++) {
                    if (ambienteRegions.get(i).contains((int) x, (int) y)) {
                        mostrarInformacion(labels[i]);
                        return true;
                    }
                }

                // Detectar si se hizo clic en el botón de "Agregar Comentario"
                if (buttonRect.contains(x, y)) {
                    // Si el clic es dentro del botón, navegar a la actividad de comentarios
                    Intent intent = new Intent(getContext(), ComentariosActivity.class);
                    getContext().startActivity(intent);
                    return true;
                }
            }
            return false;
        }

        private void mostrarInformacion(String ambiente) {
            RoomInfoFragment fragment = RoomInfoFragment.newInstance(ambiente);
            fragment.show(getSupportFragmentManager(), "room_info");
        }
    }

    public static class BuildingViewModel extends ViewModel {
        private MutableLiveData<List<Path>> ambientesLiveData;

        public BuildingViewModel() {
            ambientesLiveData = new MutableLiveData<>();
            cargarDatosDesdeArchivo();
        }

        private void cargarDatosDesdeArchivo() {
            // Simulación de carga de datos desde archivo
            List<Path> ambientes = new ArrayList<>();
            // Añadir paths específicos si fuese necesario
            ambientesLiveData.setValue(ambientes);
        }

        public MutableLiveData<List<Path>> getAmbientes() {
            return ambientesLiveData;
        }
    }
}
