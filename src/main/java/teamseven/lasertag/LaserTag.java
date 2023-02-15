/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package teamseven.lasertag;

/**
 *
 * @author dmr019
 */
public class LaserTag {
    public static Database db = new Database();
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
            db.openDatabase();           
            db.readRecords();
            db.createRecord(9, "Paul", "Smith", "brug");
            db.deleteRecord(9, "Paul", "Smith", "brug");
            db.inTable(100);
            db.closeDatabase();
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SplashScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SplashScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SplashScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SplashScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        SplashScreen splashScreen = new SplashScreen();
        EntryTerminal entryTerminal = new EntryTerminal();
        
        /* Create and display the form */
        // make splashScreen visible
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                splashScreen.setVisible(true);
            }
        });
        
        // display splash screen for 3 seconds
        try
        {
            Thread.sleep(3000);
        }
        catch (InterruptedException e)
        {
        }
        
        splashScreen.dispose();
        
        // make entryTerminal visible
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                entryTerminal.setVisible(true);
            }
        });
        
    }
    
}
