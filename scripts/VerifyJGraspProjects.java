// GPLv3; see LICENSE.TXT and NON-ENDORSEMENT.TXT for Bock contributions.
/** Local teacher check: open, compile, and run one packaged project in jGRASP.
 * Compile with jgrasp.jar on the classpath and launch this class alongside it.
 * Args: isolated settings directory, project file, source file, screenshot, JDK bin.
 * Uses Swing menu actions in the real editor. No global workspace settings change.
 * Captures evidence; inspect the project tree and Run I/O result before declaring a pass.
 */
import java.awt.*;
import java.io.*;
import javax.swing.*;
import javax.swing.text.JTextComponent;
public class VerifyJGraspProjects {
 static JMenu menu(String label) {
  for(Window w:Window.getWindows()) if(w instanceof JFrame f && f.isVisible() && f.getJMenuBar()!=null)
   for(int i=0;i<f.getJMenuBar().getMenuCount();i++) { JMenu m=f.getJMenuBar().getMenu(i); if(m!=null&&label.equals(m.getText()))return m; }
  throw new IllegalStateException("No menu "+label);
 }
 static JMenuItem item(JMenu m,String label) {
  for(Component c:m.getMenuComponents())if(c instanceof JMenuItem i && (label.equals(i.getText().trim()) || (label.equals("Project") && i.getText().startsWith("Project "))))return i;
  for(Component c:m.getMenuComponents())if(c instanceof JMenuItem i)System.out.println("AVAILABLE "+m.getText()+" / "+i.getText());
  throw new IllegalStateException("No item "+label);
 }
 static <T> T find(Component c,Class<T> type) {
  if(type.isInstance(c))return type.cast(c);
  if(c instanceof Container p)for(Component ch:p.getComponents()){T r=find(ch,type);if(r!=null)return r;}
  return null;
 }
 static void choose(File file) throws Exception {
  Thread.sleep(1200);
  SwingUtilities.invokeAndWait(()->{
   for(Window w:Window.getWindows())if(w.isVisible()) {
    JFileChooser c=find(w,JFileChooser.class);
    if(c!=null){c.setSelectedFile(file);c.approveSelection();return;}
   }
   throw new IllegalStateException("No file chooser");
  });
 }
 static void dump(Component c) {
  if(c instanceof JFrame f)System.out.println("FRAME "+f.getTitle()+" visible="+f.isVisible());
  if(c instanceof JDialog d)System.out.println("DIALOG "+d.getTitle()+" visible="+d.isVisible());
  if(c instanceof JLabel l)System.out.println("LABEL "+l.getText());
  if(c instanceof JList l)for(int i=0;i<l.getModel().getSize();i++)System.out.println("LIST "+l.getModel().getElementAt(i));
  if(c instanceof JButton b)System.out.println("BUTTON "+b.getText());
  if(c instanceof JTextComponent t && !t.getText().isBlank())System.out.println("TEXT "+t.getText().substring(0,Math.min(2500,t.getText().length())));
  if(c instanceof JTree t)for(int i=0;i<t.getRowCount();i++)System.out.println("TREE "+t.getPathForRow(i));
  if(c instanceof JMenu m)for(Component ch:m.getMenuComponents()){if(ch instanceof JMenuItem i)System.out.println("MENU "+m.getText()+" / "+i.getText()+" enabled="+i.isEnabled());}
  else if(c instanceof Container p)for(Component ch:p.getComponents())dump(ch);
 }
 public static void main(String[] args)throws Exception{
  if(args.length!=5)throw new IllegalArgumentException("Expected settings, project, source, screenshot, JDK bin");
  new Thread(()->{try{Class.forName("Grasp").getMethod("main",String[].class).invoke(null,(Object)new String[]{"-i","-pj","-d",args[0],"-nosplash","-no_font_check","-j",args[4]});}catch(Exception e){e.printStackTrace();}}).start();
  Thread.sleep(8000);
  SwingUtilities.invokeAndWait(()->{for(Window w:Window.getWindows())if(w instanceof JDialog d && d.getTitle().startsWith("Welcome"))d.dispose();});
  Thread.sleep(1500);
  SwingUtilities.invokeLater(()->item(menu("Project"),"Open").doClick());
  choose(new File(args[1]));
  Thread.sleep(2000);
  SwingUtilities.invokeLater(()->item(menu("File"),"Open").doClick());
  choose(new File(args[2]));
  Thread.sleep(2000);
  SwingUtilities.invokeLater(()->item(menu("Build"),"Compile").doClick());
  Thread.sleep(4000);
  SwingUtilities.invokeLater(()->item(menu("Build"),"Run").doClick());
  Thread.sleep(12000);
  SwingUtilities.invokeAndWait(()->{
   for(Window w:Window.getWindows())if(w instanceof JFrame f && f.isVisible()){
    f.setSize(1250, 900);
    java.awt.image.BufferedImage image=new java.awt.image.BufferedImage(f.getWidth(),f.getHeight(),java.awt.image.BufferedImage.TYPE_INT_RGB);
    java.awt.Graphics2D g=image.createGraphics();f.printAll(g);g.dispose();
    try{javax.imageio.ImageIO.write(image,"png",new File(args[3]));}catch(Exception e){throw new RuntimeException(e);}
   }
   for(Window w:Window.getWindows())dump(w);
  });
  SwingUtilities.invokeAndWait(()->{JMenuItem end=item(menu("Build"),"End");if(end.isEnabled())end.doClick();});
  Thread.sleep(1000);
  System.exit(0);
 }
}
