package com.example.zenword;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class MainActivity extends AppCompatActivity {

    private static final int MIN_WORDLENGTH = 3;
    private static final int MAX_WORDLENGTH = 7;
    private Button clear, send, help, shuffle, restart, bonus;
    private static ConstraintLayout constraintLayout;
    private static final int textView_width = 140;
    private static final int margen_Letras = 10;

    private int[] guias;
    private Button[] botones;

    private static int bonusAcumulado;
    public int colors[];
    private Random rm;
    private static int wordLength;
    private TextView tResultado, tBonus, tInstruction;
    private int indexAleatori;

    private static Button[] bConjunto;
    private TextView[][] tvConjunt;
    private TreeMap<String, Integer> longituds;
    private static int contador;
    private TreeMap<String, Integer> paraulesOcultes;

    private TreeMap<String, String> solucionsTrobades;
    private TreeMap<String, String> paraulesValides; // los keys son las palabras sin acentos
    private HashMap<String, Integer> solucions;

    //sizes
    private static int sizeParaulesOcultes;
    private static int sizeSolucions;
    private static int sizeClavesAsociadas;
    private static int sizePalabrasNomostradas;
    Map<Integer, Integer> sizesPorLongitud;
    Map<String, Integer> PalabrasNomostradas;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Conectar 'id's con botones
        clear = findViewById(R.id.clear);
        send = findViewById(R.id.send);
        bonus = findViewById(R.id.bonus);
        shuffle = findViewById(R.id.shuffle);
        restart = findViewById(R.id.restart);
        help = findViewById(R.id.help);

        botones = new Button[]{clear, send, help, shuffle, restart, bonus};

        // Configura el OnClickListener para todos los botones
        View.OnClickListener buttonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String action = v.getTag().toString();
                handleButtonClick(action);
            }
        };
        // Asigna el OnClickListener a todos los botones
        for (int i = 0; i < botones.length; i++) {
            botones[i].setOnClickListener(buttonClickListener);
        }

        constraintLayout = findViewById(R.id.constraintLayout);
        tResultado = findViewById(R.id.textResultado);
        tBonus = findViewById(R.id.textBonus);
        tInstruction = findViewById(R.id.textInstruction);

        bConjunto = new Button[]{findViewById(R.id.button1), findViewById(R.id.button2), findViewById(R.id.button3), findViewById(R.id.button4), findViewById(R.id.button5), findViewById(R.id.button6), findViewById(R.id.button7)};
        guias = new int[]{R.id.guideline10, R.id.guideline19, R.id.guideline28, R.id.guideline37, R.id.guideline46};
        colors = getResources().getIntArray(R.array.colors_array);
        rm = new Random();
        indexAleatori = 0;

        //Lectura diccionari
        try {
            llegirParaules();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        sizeParaulesOcultes = 0;
        sizeSolucions = 0;
        contador = 0;
        bonusAcumulado = 0;
        sizeClavesAsociadas = 0;
        sizePalabrasNomostradas = 0;

        posarLletresCercle();

        textoSoluciones();
    }

    /**
     * Actualiza el marcador según el usuario va desvelando soluciones y muestra repetidas en rojo
     */
    private void textoSoluciones() {
        // Actualizamos marcador
        tInstruction.setText("Has encertat " + contador + " de possibles " + sizeSolucions + ": ");
        // Incorporamos nueva palabra con formato HTML
        StringBuilder sb = new StringBuilder();
        Iterator<Map.Entry<String, String>> iterator = solucionsTrobades.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            String palabra = entry.getKey();
            String palabraFormateada = entry.getValue();

            // Comprobar si la palabra ya tiene formato HTML para evitar duplicar el formato
            if (!palabraFormateada.startsWith("<font color='red'>")) {
                sb.append(palabra);
            } else {
                sb.append(palabraFormateada);
            }

            if (iterator.hasNext()) {
                sb.append(", ");
            }
        }

        // Seteamos el texto con formato HTML en el TextView
        tInstruction.setText(Html.fromHtml(tInstruction.getText() + sb.toString()));
    }

    /**
     * Funcionalidad del botón enviar segun sea la solución escrita por el usuario. Muestra los
     * mensajes en pantalla.
     */
    private void SendFunction() {
        String palabraEncontrada = tResultado.getText().toString().trim().toLowerCase();
        borrarContenido();

        //la palabra introducida es de las palabras ocultas
        if (paraulesOcultes.containsKey(palabraEncontrada)) {

            //mostrar la palabra en su posicion
            String palabraAcentuada = paraulesValides.get(palabraEncontrada);
            mostraParaula(palabraAcentuada, paraulesOcultes.get(palabraEncontrada) + 1);

            //añddir la palabra al conjunto de palabras encontradas
            solucionsTrobades.put(palabraEncontrada, palabraAcentuada);
            //comunicarle al usuario que ha encontrado una palabra
            mostraMissatge("Has trobat una paraula solucio", false);
            //se debe eliminar de palabras ocultas
            paraulesOcultes.remove(palabraEncontrada);
            sizeParaulesOcultes--;
            if (PalabrasNomostradas.remove(palabraEncontrada) != null) { //ya la tenemos
                sizePalabrasNomostradas--;
            }
            contador++;
            //la palabra es valida y se ha introducido por primera vez
        } else if (paraulesValides.containsKey(palabraEncontrada) && solucionsTrobades.get(palabraEncontrada) == null) {
            String palabraAcentuada = paraulesValides.get(palabraEncontrada);

            solucionsTrobades.put(palabraEncontrada, palabraAcentuada);
            mostraMissatge("Paraula vàlida! Tens un bonus", false);
            gestionarBonus('i'); //incrementa bonus
            contador++;

        } else if (solucionsTrobades.get(palabraEncontrada) != null) { // si la palabra ya se introdujo
            mostraMissatge("Aquesta ja la tens", false);

            //mostrar en rojo dicha palabra en la lista
            // String styledText = "<font color =’red ’>"+palabraEncontrada+" </ font >.";
            solucionsTrobades.put(palabraEncontrada, "<font color='red'>" + solucionsTrobades.get(palabraEncontrada) + "</font>");

        } else {
            mostraMissatge("Paraula no vàlida", false);
        }

        //Has adivinado todas las palabras??
        if (paraulesOcultes.isEmpty()) {
            mostraMissatge("Enhorabona! has guanyat", true);
            Enable_DisableBottons(false, constraintLayout.getId());
        }

        //Actualiza marcador
        textoSoluciones();
    }

    /**
     * Hace las llamadas a métodos cuando el usuario pulsa el botón.
     *
     * @param action String para identificar la función a realizar
     */
    private void handleButtonClick(String action) {
        switch (action) {
            case "clear":
                borrarContenido();
                break;
            case "send":
                SendFunction();
                break;
            case "help":
                ajuda();
                break;
            case "shuffle":
                random();
                break;
            case "restart":
                reiniciar();
                break;
            case "bonus":
                bonus();
                break;
            default:
                break;
        }
    }

    /**
     * Crea las filas de las 5 casillas (si es posible) dependiendo la longitud de la palabra
     * formada en el círculo
     */
    private void prepararParaules() {
        for (int i = 0; i < 5; i++) {
            Iterator<Map.Entry<String, Integer>> iterator = paraulesOcultes.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Integer> entry = iterator.next();
                //paraulesOcultes contiene en el valor del mapping la posición pertinente
                if (entry.getValue() == i) {
                    tvConjunt[i] = crearFilaTextViews(guias[i], entry.getKey().length());
                }
            }
        }
    }

    /**
     * Crea una fila a partir de estos parámetros.
     *
     * @param guia    guideline con su id
     * @param lletres cantidad de letras de la palabra
     * @return fila correspondiente
     */
    public TextView[] crearFilaTextViews(int guia, int lletres) {
        constraintLayout = findViewById(R.id.constraintLayout);

        TextView[] textviews = new TextView[lletres];
        ConstraintSet constraintSet = new ConstraintSet();
        int margenPantalla = ((textView_width + margen_Letras) * lletres) - margen_Letras;
        margenPantalla = (1080 - margenPantalla) / 2; //1080 = ancho pantalla

        for (int i = 0; i < lletres; i++) {
            // Crear un nuevo TextView
            textviews[i] = new TextView(this);
            textviews[i].setId(View.generateViewId());
            textviews[i].setTextColor(Color.WHITE);
            textviews[i].setBackgroundColor(colors[indexAleatori]);
            textviews[i].setTextSize(28);
            textviews[i].setGravity(Gravity.CENTER);
            textviews[i].setTypeface(Typeface.create("Georgia", Typeface.BOLD));

            // Agregar el TextView al ConstraintLayout
            constraintLayout.addView(textviews[i]);

            // Obtener el ID del TextView recién agregado
            int viewId = textviews[i].getId();

            // Establecer restricciones para cada TextView usando ConstraintSet
            constraintSet.constrainWidth(viewId, textView_width);
            constraintSet.constrainHeight(viewId, textView_width); //es un cuadrado

            // Conectar TextViews según la posición y guía
            if (i == 0) {
                // Primer TextView: conectar al borde izquierdo del ConstraintLayout
                constraintSet.connect(viewId, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, margenPantalla);
            } else {
                // Resto de TextViews: conectar al TextView anterior
                constraintSet.connect(viewId, ConstraintSet.START, textviews[i - 1].getId(), ConstraintSet.END, margen_Letras);
            }

            // Conectar la parte superior del TextView a la parte superior del ConstraintLayout
            constraintSet.connect(viewId, ConstraintSet.TOP, guia, ConstraintSet.TOP, 0);

            // Aplicar las restricciones al TextView actual
            constraintSet.applyTo(constraintLayout);
        }

        return textviews;
    }

    /**
     * Borrar contenido text view y activar letras
     */
    private void borrarContenido() {
        tResultado.setText("");

        //Reiniciar letras
        for (int i = 0; i < bConjunto.length; i++) {
            bConjunto[i].setTextColor(Color.WHITE);
            bConjunto[i].setEnabled(true);
        }
    }

    /**
     * Comprueba el estado del conjunto de los botones y si se ha pulsado alguno de ellos
     *
     * @param botones array tipo Button
     */
    private void setLletres(Button[] botones) {
        for (int i = 0; i < botones.length; i++) {
            int index = i;
            botones[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setLletra(botones[index]);
                }
            });
        }
    }

    /**
     * Desactiva una letra pulsada del círculo y pilla el valor del botón
     *
     * @param boton tipo Button
     */
    private void setLletra(Button boton) {
        String lletra = boton.getText().toString();
        tResultado.setText(tResultado.getText() + lletra);
        boton.setEnabled(false);
        boton.setTextColor(Color.GRAY);
    }

    /**
     * Realiza la funcionalidad del botón random utilizando el algoritmo Fisher-Yates una vez
     * obtenidas las letras del círculo y después las devuelve al círculo.
     */
    private void random() {
        if (tResultado.length() < 1) {
            //Calcular cantidad de letras en el círculo
            int size = 0;
            for (int i = 0; i < bConjunto.length; i++) {
                if (!bConjunto[i].getText().toString().isEmpty()) {
                    size++;
                }
            }

            //Obtener letras sin ordenar del círculo
            Character[] letras = new Character[size];
            int index = 0;
            for (int i = 0; i < bConjunto.length; i++) {
                if (!bConjunto[i].getText().toString().isEmpty()) {
                    letras[index++] = bConjunto[i].getText().charAt(0);
                }
            }

            //Ordenar distribución de las letras
            for (int i = letras.length - 1; i > 0; i--) {
                int j = rm.nextInt(i + 1);
                char aux = letras[i];
                letras[i] = letras[j];
                letras[j] = aux;
            }

            //Organizar mejor letras del círculo y devolver las letras ordenadas al círculo
            if (letras.length == 4) {
                bConjunto[3] = findViewById(R.id.button7);
            }
            for (int i = 0; i < letras.length; i++) {
                bConjunto[i].setText(String.valueOf(letras[i]));
            }
            bConjunto[3] = findViewById(R.id.button4);
        }
    }


    /**
     * Realiza la funcionalidad del botón ayuda (?). Cada vez el usuario tenga 5 puntos de bonus o
     * más y no haya adivinado todas se puede usar.
     */
    private void ajuda() {
        if (bonusAcumulado >= 5 && sizePalabrasNomostradas > 0) {
            // Obtener un valor aleatorio entre las palabras no acertadas
            int dado = rm.nextInt(sizePalabrasNomostradas);

            String palabraSeleccionada = null;
            int valorSeleccionado = 0;

            // Usar un iterador para encontrar la entrada correspondiente
            Iterator<Map.Entry<String, Integer>> iterator = PalabrasNomostradas.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Integer> entry = iterator.next();
                if (dado == 0) {
                    palabraSeleccionada = entry.getKey();
                    valorSeleccionado = entry.getValue();
                    break;
                }
                dado--;
            }

            // Mostrar la primera letra de la palabra seleccionada y eliminarla del mapa
            if (palabraSeleccionada != null) {
                mostraPrimeraLletra(palabraSeleccionada, valorSeleccionado + 1);
                PalabrasNomostradas.remove(palabraSeleccionada);
                sizePalabrasNomostradas--;
            }

            gestionarBonus('d'); // Decrementa bonus
        }
    }


    /**
     * Funcionalidad del botón bonus. Muestra pantalla con las soluciones que el usuario ha
     * desvelado
     */
    private void bonus() {
        //Crea diálogo y su título
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Encertades ( " + contador + " de " + sizeSolucions + " ):");

        //Incorporamos las palabras encontradas recorriendo mapping
        StringBuilder sb = new StringBuilder();
        Iterator<Map.Entry<String, String>> iterator = solucionsTrobades.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            sb.append(entry.getKey());
            if (iterator.hasNext()) {
                sb.append(", ");
            }
        }

        //Muestra el cuerpo del mensaje
        builder.setMessage(sb.toString());
        builder.setPositiveButton("OK", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Método que lleva los cambios de valor de la variable bonusAcumulado que sirve para calcular
     * el bonus del usuario. También actualiza el valor en el icono que hay en el logo del bonus.
     *
     * @param c carácter para saber que función es
     */
    private void gestionarBonus(char c) {
        switch (c) {
            //Reinicio
            case 'r':
                bonusAcumulado = 0;
                break;
            //Incrementa
            case 'i':
                bonusAcumulado += 1;
                break;
            //Decrementa (ha gastado un bonus)
            case 'd':
                bonusAcumulado -= 5;
                break;
        }
        tBonus.setText(String.valueOf(bonusAcumulado));
    }

    /**
     * Método que determina si una palabra es solución a partir de otra. Hacemos uso de un mapping
     *
     * @param Paraula1 `palabra` del círculo
     * @param Paraula2 palabra que podría ser solución
     * @return booleano que determino si la palabra2 se puede determinar a partir de letras de la
     * palabra1.
     * @lletresDisponibles: es un mapping que contiene el carácter (la letra) y el número de
     * apariciones de esta letra. Sabemos la longitud del mapping y no hace falta que esté ordenado.
     * Por eso, hacemos uso de un UnsortedArrayMapping que hemos creado.
     */
    private static boolean esParaulaSolucio(String Paraula1, String Paraula2) {
        int n = Paraula1.length();
        UnsortedArrayMapping<Character, Integer> lletresDisponibles = new UnsortedArrayMapping<>(n);
        //preparar el conjunt de lletres solucions
        for (int i = 0; i < n; i++) {
            char lletra = Paraula1.charAt(i);

            if (lletresDisponibles.get(lletra) != null) {
                lletresDisponibles.put(lletra, lletresDisponibles.get(lletra) + 1);
            } else {
                lletresDisponibles.put(lletra, 1);

            }
        }

        //comparamos carácter a carácter
        for (int i = 0; i < Paraula2.length(); i++) {
            char lletra = Paraula2.charAt(i);
            if (lletresDisponibles.get(lletra) == null || lletresDisponibles.get(lletra) == 0) {
                return false;
            } else {
                lletresDisponibles.put(lletra, lletresDisponibles.get(lletra) - 1);
            }
        }
        return true;

    }

    /**
     * Muestra una palabra en la casilla (posición) correspondiente
     *
     * @param s       String a mostrar en pantalla
     * @param posicio línea en donde mostrar la palabra
     */
    private void mostraParaula(String s, int posicio) {
        s = s.toUpperCase();
        for (int i = 0; i < s.length(); i++) {
            tvConjunt[posicio - 1][i].setText(String.valueOf(s.charAt(i)));
        }
    }

    /**
     * Muestra la primera letra de una palabra en la casilla (posición) correspondiente
     *
     * @param s       String que contiene la primera letra a mostrar
     * @param posicio línea en donde mostrar la primera letra
     */
    private void mostraPrimeraLletra(String s, int posicio) {
        for (int i = 0; i < s.length(); i++) {
            tvConjunt[posicio - 1][0].setText(String.valueOf(s.charAt(0)).toUpperCase(Locale.getDefault()));
        }
    }

    /**
     * Muestra un mensaje en pantalla notificando al usuario. Hacemos uso de la función Toast.
     *
     * @param s     String que contiene el mensaje a notificar
     * @param llarg booleano que determina si es un mensaje largo (true) o corto (false)
     */
    private void mostraMissatge(String s, boolean llarg) {
        Context context = getApplicationContext();
        int temps = Toast.LENGTH_SHORT;
        if (llarg) {
            temps = Toast.LENGTH_LONG;
        }
        Toast toast = Toast.makeText(context, s, temps);
        toast.show();
    }

    /**
     * Funcionalidad del botón reiniciar. Reiniciar todas las variables para la nueva partida.
     * La mayor parte del nuevo reinicio se hace en posarLletresCercle().
     */
    private void reiniciar() {
        if (tResultado.length() < 1) {
            //Los colores del círculo y textviews van rotando
            indexAleatori++;
            if (indexAleatori == colors.length) {
                indexAleatori = 0;
            }

            //reinicia variables, casillas y habilita botones
            contador = 0;
            sizeSolucions = 0;
            sizeParaulesOcultes = 0;
            sizeClavesAsociadas = 0;
            sizePalabrasNomostradas = 0;

            gestionarBonus('r');
            limpiezaCasillas();
            Enable_DisableBottons(true, constraintLayout.getId());
            posarLletresCercle();
        }
    }

    /**
     * Vacía las casillas si tenían carácteres y eliminan color actual para no sobreescribirlo
     */
    private void limpiezaCasillas() {
        for (int i = 0; i < tvConjunt.length; i++) {

            for (int j = 0; j < tvConjunt[i].length; j++) {
                tvConjunt[i][j].setText("");
                tvConjunt[i][j].setBackgroundColor(Color.TRANSPARENT);
            }
        }
    }

    /**
     * Hace gran parte del reinicio. Muestra la nueva palabra en el círculo, calcula las soluciones
     * de la nueva palabra (paraulesOcultes también). Se hace gran uso de mappings en esta parte:
     *
     * @solucions es un mapping que contiene las palabras que se pueden formar a partir
     * de las letras del círculo (estén en casillas o no). HashMap<String,Integer> porque no nos
     * importa el orden del mapping y el hashing es más rápido. La clave es la palabra solución, el
     * valor es la longitud de la palabra solución.
     * @solucionsTrobades es un mapping que contiene las palabras que el usuario ha adivinado
     * (sean solución o no). Es un TreeMap<String,Integer> porque en este caso sí importa el orden.
     * La clave es la palabra y el valor es la longitud.
     * @paraulesOcultes es un mapping que contiene las palabras a desvelar en las casillas. Es un
     * TreeMap<String,Integer> porque sí que importa el orden. La clave es la palabra y el valor es
     * la longitud.
     */

    private void posarLletresCercle() {
        paraulesOcultes = new TreeMap<>();
        solucionsTrobades = new TreeMap<>();
        solucions = new HashMap<>();
        sizesPorLongitud = new HashMap<>();
        PalabrasNomostradas = new TreeMap<>();

        //Borrar letras de los botones
        for (int i = 0; i < bConjunto.length; i++) {
            bConjunto[i].setText("");
        }

        // Seleccionar aleatoriamente el número de letras del círculo
        wordLength = rm.nextInt(4) + 4;
        Character[] lletresCercle = new Character[wordLength];

        cambiarColorCirculo(colors[indexAleatori]);

        // Recorrer el mapa longituds para añadir todas las palabras del mismo tamaño a la lista
        List<String> clavesAsociadas = new ArrayList<>();
        Iterator<Map.Entry<String, Integer>> iterator = longituds.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> entry = iterator.next();
            if (entry.getValue().equals(wordLength)) {
                clavesAsociadas.add(entry.getKey());
                sizeClavesAsociadas++;
            }
        }

        // Devolver una palabra aleatoria de entre toda la lista de palabras del mismo tamaño
        String paraulaCentre = clavesAsociadas.get(rm.nextInt(sizeClavesAsociadas));

        for (int i = 0; i < wordLength; i++) {
            lletresCercle[i] = paraulaCentre.toUpperCase().charAt(i);
        }

        // Crear el mapping de posibles soluciones a partir de las letras del círculo
        iterator = longituds.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> entry = iterator.next();
            if (esParaulaSolucio(paraulaCentre, entry.getKey()) && entry.getValue() <= wordLength) {
                solucions.put(entry.getKey(), entry.getValue());
                sizeSolucions++;
            }
        }

        tInstruction.setText("Has encertat " + contador + " de possibles " + sizeSolucions + ": ");

        //Mapping de ayuda
        Map<Integer, List<String>> solucionesPorLongitud = new HashMap<>();

        // Inicializar las listas en el mapa
        for (int i = MIN_WORDLENGTH; i <= MAX_WORDLENGTH; i++) {
            solucionesPorLongitud.put(i, new ArrayList<>());
            sizesPorLongitud.put(i, 0);//inicializar los tamaños
        }

        // Llenar las listas en el mapa según la longitud de las soluciones
        Iterator<Map.Entry<String, Integer>> it = solucions.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Integer> entry = it.next();
            int length = entry.getValue();
            if (length >= MIN_WORDLENGTH && length <= MAX_WORDLENGTH) {
                solucionesPorLongitud.get(length).add(entry.getKey());
                sizesPorLongitud.put(length, sizesPorLongitud.get(length) + 1);//incrementar el tamaño
            }
        }

        //añadir las palabras de 3 letras
        int numParaules3 = MAX_WORDLENGTH - wordLength + 1;
        int i = 0;
        for (int j = MIN_WORDLENGTH + 1; j < wordLength; j++) {//palabras intermedias: mas de 3 letras y menor que wordlength
            if (solucionesPorLongitud.get(j).isEmpty()) {
                numParaules3++;
            }

        }
        while (!solucionesPorLongitud.get(MIN_WORDLENGTH).isEmpty() && i < numParaules3) {
            String paraula = solucionesPorLongitud.get(MIN_WORDLENGTH).remove(rm.nextInt(sizesPorLongitud.get(MIN_WORDLENGTH)));
            //decrementar el tamaño de las soluciones de MIN_QORDLENGTH=3
            sizesPorLongitud.put(MIN_WORDLENGTH, sizesPorLongitud.get(MIN_WORDLENGTH) - 1);
            paraulesOcultes.put(paraula, i);
            sizeParaulesOcultes++;
            i++;
        }

        //palabras de mas de 3 letras
        for (int j = MIN_WORDLENGTH + 1; j <= wordLength; j++) {
            if (!solucionesPorLongitud.get(j).isEmpty()) {
                String paraula = solucionesPorLongitud.get(j).remove(rm.nextInt(sizesPorLongitud.get(j)));
                sizesPorLongitud.put(j, sizesPorLongitud.get(j) - 1);
                paraulesOcultes.put(paraula, i);
                sizeParaulesOcultes++;
                i++;
            }
        }

        //  Las palabras de 3 letras están ordenadas en el mapping pero no tienen el valor adecuado
        if (numParaules3 > 1) {
            Set<String> palabrasTresLetras = new TreeSet<>(); // Utilizamos un TreeSet para ordenar alfabéticamente

            // Obtener las palabras de longitud tres del mapping y añadirlas al conjunto
            Iterator<Map.Entry<String, Integer>> iteratorTS = paraulesOcultes.entrySet().iterator();
            while (iteratorTS.hasNext()) {
                Map.Entry<String, Integer> map = iteratorTS.next();
                if (map.getKey().length() == 3) {
                    palabrasTresLetras.add(map.getKey());
                    iteratorTS.remove(); // Eliminamos la palabra del mapping de forma segura
                }
            }

            // Insertar las palabras ordenadas de vuelta en el mapping con el orden correspondiente
            int index = 0;
            for (String palabra : palabrasTresLetras) {
                paraulesOcultes.put(palabra, index++);
            }
        }
        Iterator<Map.Entry<String, Integer>> iteratorTS = paraulesOcultes.entrySet().iterator();
        while (iteratorTS.hasNext()) {
            Map.Entry<String, Integer> map = iteratorTS.next();
            PalabrasNomostradas.put(map.getKey(), map.getValue());
            sizePalabrasNomostradas++;
        }

        tvConjunt = new TextView[sizeParaulesOcultes][];

        prepararParaules();
        textoSoluciones();

        //Para organizar mejor las letras dentro del circulo
        if (wordLength == 4) {
            bConjunto[3] = findViewById(R.id.button7);
        }

        //Poner nuevas letras al circulo
        for (int j = 0; j < lletresCercle.length; j++) {
            bConjunto[j].setText(String.valueOf(lletresCercle[j]));
        }

        //Restablecer el botón cambiado
        bConjunto[3] = findViewById(R.id.button4);

        setLletres(bConjunto);
    }

    /**
     * Lógica cambiar color circulo
     *
     * @param color color a cambiar
     */
    private void cambiarColorCirculo(int color) {
        // Obtén la referencia del ImageView donde se encuentra el shape
        ImageView imageView = findViewById(R.id.imageView);
        // Obtiene el Drawable del ImageView
        GradientDrawable drawable = (GradientDrawable) imageView.getDrawable();
        // Establece el color del shape
        drawable.setColor(color);
    }

    /**
     * Para poder deshabilitar todos los botones al acabar al partida.
     * Exceptuando Bonus y restart
     *
     * @param enable booleano que determina si se activa o se deshabilita
     * @param parent id Layout
     */
    private void Enable_DisableBottons(boolean enable, int parent) {
        ConstraintLayout cl = findViewById(parent);
        int count = cl.getChildCount();
        System.out.println("count: " + count);
        for (int i = 0; i < count; i++) {
            View child = cl.getChildAt(i);
            int childId = child.getId();
            if (childId == R.id.bonus || childId == R.id.restart) {
                cl.getChildAt(i).setEnabled(true);
            } else {
                cl.getChildAt(i).setEnabled(enable);
            }
        }

    }

    /**
     * Leer el diccionario y establecer las palabras del diccionario en los mappings
     * correspondientes:
     *
     * @throws IOException necesario para la lectura del fichero.
     * @paraulesValides es un mapping que contiene todas las palabras del diccionario que tienen
     * longitud 3 y 7. Es un TreeMap<String,String> porque sí importa el orden de las palabras.
     * La key es la palabra sin acento, el valor es la palabra con acento.
     * @longituds es un mapping que contiene todas las palabras entre longitud 3 y 7 pero ahora el
     * valor ya no es la misma palabra con acento, sino que es la longitud de la palabra. Es un
     * TreeMap<String,String> porque sí importa el orden de las palabras.
     */
    private void llegirParaules() throws IOException {
        //Lectura fichero
        InputStream is = getResources().openRawResource(R.raw.paraules);
        BufferedReader r = new BufferedReader(new InputStreamReader(is));
        paraulesValides = new TreeMap<>();
        longituds = new TreeMap<>();
        String linea;
        while ((linea = r.readLine()) != null) {
            String[] palabras = linea.split(";");
            String primeraPalabra = palabras[0].trim();
            String segundaPalabra = palabras[1].trim();
            //Si están entre esas longitudes se añaden a los mappings
            if (segundaPalabra.length() >= MIN_WORDLENGTH && segundaPalabra.length() <= MAX_WORDLENGTH) {
                paraulesValides.put(segundaPalabra, primeraPalabra);
                longituds.put(segundaPalabra, segundaPalabra.length());

            }
        }
    }
}