import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import java.util.*;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Graphics;
import javax.swing.*;
import java.awt.*;

public class JuegoContenido extends JPanel implements ActionListener{ 
  //pantalla
 static final int PANTALLA = 600;
 static final int CUADROS_SIZE = 25; //elementos del juego 
 static final int CUADROS_PARELELO = (int) PANTALLA / CUADROS_SIZE;  //cuantos cuadritos caben a lo largo y a lo ancho. ponemos int al inicio para que nos de un enterio si o si, debido a que en la division puede que no de un numero entero

  //serpiente
 static final int TOTAL_SERPIENTE = (PANTALLA * PANTALLA / CUADROS_SIZE ); // se determina el tamaño completo de la serpiente
   int[] serpienteX = new int[TOTAL_SERPIENTE];
   int[] serpienteY = new int [TOTAL_SERPIENTE];
   int cuerpo_serpiente = 3; //el tamaño con el que empieza la serpiente
   char direccion = 'd'; //a donde se dirige la serpiente. teclas: aswd

  //comida
 int comidaX; //la comida se encuentra en una posición X
 int comidaY;
  
  //timer
  static final int DELAY = 100; //la velocidad con la cual se mueve la serpiente
 Timer timer;
 boolean running = true; //nos ayudara a saber si el juego termino
  
  //otros
Random random = new Random(); //hace que la comida aparezaca aleatoreamente 

  JuegoContenido(){  //constructor
    this.setPreferredSize(new Dimension(PANTALLA,PANTALLA)); //tamaño de la pantalla, que sea cuadrada
    this.setBackground(Color.BLACK);
    this.setFocusable(true);
    this.addKeyListener(new Controles()); //va ayudar a mover la serpieden arriba, abajo, izquierda o derecha
    iniciarJuego();
  }

  public void iniciarJuego(){
    AgregarComida();
    timer = new Timer(DELAY, this);
    timer.start(); //se inicie y para que se redibuje nuestra ventana de juego
  }

  public void AgregarComida(){
    comidaX = random.nextInt(CUADROS_PARELELO) * CUADROS_SIZE; //es para indicar que la comida solo puede aparecer dentro de este rango.
    comidaY = random.nextInt(CUADROS_PARELELO) * CUADROS_SIZE;
  }

  public void MoverSerpiente(){
    for(int i = cuerpo_serpiente; i > 0; i--){ //este for es para que recorra el cuerpo de la serpiente pero no llege a su cabeza
      serpienteX[i] = serpienteX[i - 1]; //le damos nueva posicion al cuerpo de la serpiente
serpienteY[i] = serpienteY[i - 1];
}
    switch(direccion){
  case 'd':
    serpienteX[0] = serpienteX[0] + CUADROS_SIZE; //si quiero ir a la derecha 
    break;

  case 'a':
    serpienteX[0] = serpienteX[0] - CUADROS_SIZE; //si quiero ir a la izquierda 
    break;

  case 'w':
    serpienteY[0] = serpienteY[0] - CUADROS_SIZE;  //este es para cuando va arriba, es decir la cabeza es la que se mueve por eso se escoge la posicion 0
    break;
    
  case 's':
    serpienteY[0] = serpienteY[0] + CUADROS_SIZE; //este es para cuando baja
    break;
}
    }

public void ChecarComida(){
if(serpienteX[0]==comidaX && serpienteY[0]==comidaY){
  cuerpo_serpiente++; //la serpiente crece cada que come

    AgregarComida(); //llamamos al metodo agregar comida para que salga nueva comida
}
}

public void checarColisiones() {
  if (serpienteX[0] < 0) { // si se va todo a la izquierda se va terminar el juego
    running = false;
  }  
  if (serpienteY[0] < 0) {  // si se va todo hacia arriba se va terminar el juego
    running = false;
  }
  if (serpienteX[0] > PANTALLA - CUADROS_SIZE) { // si se pasa de la pantalla se termina el juego
    running = false;
  }  
  if (serpienteY[0] > PANTALLA - CUADROS_SIZE) { // si se pasa de la pantalla hacia abajo se termina el juego
    running = false;
  }  
}

@Override
public void actionPerformed(ActionEvent e){
if(running){ //si el juego corre se llaman los siguientes metodos
MoverSerpiente();
ChecarComida();
checarColisiones();
}
repaint(); //esto re dibuja el panel
}

@Override
public void paintComponent(Graphics g) {
super.paintComponent(g);
for(int i = 0; i < CUADROS_PARELELO; i++){
g.drawLine(0, CUADROS_SIZE * i, PANTALLA, CUADROS_SIZE * i); //esto es para dibujar una linea
g.drawLine(CUADROS_SIZE * i, 0, CUADROS_SIZE * i, PANTALLA);
}
  g.setColor(Color.red); //color de la comida
g.fillOval(comidaX, comidaY, CUADROS_SIZE, CUADROS_SIZE);   //forma de la comida y su tamaño, escogi un ovalo y el tamaño es la variable CUADROS_SIZE

g.setColor(Color.green); //color de la serpiente
for(int i = 0; i < cuerpo_serpiente; i++){   //para dibujar en cada momento el tamaño de la serpiente
  g.fillRect(serpienteX[i], serpienteY[i], CUADROS_SIZE, CUADROS_SIZE);  //posicion inicial de la serpiente, tamaño y forma
}
  }

public class Controles extends KeyAdapter {
@Override
public void keyPressed(KeyEvent e){  // lógica para cambiar la dirección de la serpiente según la tecla presionada
  
switch(e.getKeyChar()){  //le damos el movimiento
  case 'w':
    direccion='w'; //le decimos que vaya arriba
    break;

     case 's':
    direccion='s'; //le decimos que vaya abajo
    break;

     case 'd':
    direccion='d'; //le decimos que vaya a la derecha
    break;

     case 'a':
    direccion='a'; //le decimos que vaya a la izquierda
    break;
}
}
}
}