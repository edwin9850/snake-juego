import javax.swing.JFrame;

public class JuegoVentana extends JFrame{
  JuegoVentana(){
    this.setTitle("snake");    //titulo del juego
    this.add(new JuegoContenido()); //agregarle el contenido 
     this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //se cierra la ventana 
    this.setResizable(false);  //PARA que los jugadores no muevan la ventana
    this.pack();
    this.setLocationRelativeTo(null); //para que la ventana se centre, se vea lindo
    this.setVisible(true);
  }
}

