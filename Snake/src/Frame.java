import javax.swing.JFrame; //package for Jframe

public class Frame extends JFrame{ //creates frame that inherits concept of Jframe by extend

    Frame(){

        this.add(new Panel()); //sets new panel
        this.setTitle("Snake"); //sets title of panel to snake
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //specify close operation
        this.setResizable(false); //prevents user from resizing
        this.pack(); //sets all contents to at or above their prefered size
        this.setVisible(true); //controls if things are on the screen
        this.setLocationRelativeTo(null); //sets position relative to something Null centers the frame

    }
}